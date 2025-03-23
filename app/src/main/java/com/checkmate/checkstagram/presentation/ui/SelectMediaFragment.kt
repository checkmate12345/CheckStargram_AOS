package com.checkmate.checkstagram.presentation.ui

import PickMediaAdapter
import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.checkmate.checkstagram.R
import com.checkmate.checkstagram.databinding.FragmentSelectMediaBinding
import com.checkmate.checkstagram.presentation.model.SelectedMediaInfo
import com.checkmate.checkstagram.presentation.viewmodel.SelectMediaViewModel
import com.checkmate.checkstagram.util.repeatOnStarted
import com.greener.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class SelectMediaFragment: BaseFragment<FragmentSelectMediaBinding>(
    FragmentSelectMediaBinding::inflate
) {
    private val viewModel: SelectMediaViewModel by viewModels()
    private val pickMediaAdapter = PickMediaAdapter{ selectedMedia ->
        viewModel.toggleSelection(selectedMedia)
    }
    private var player: ExoPlayer? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        setupToolbar()
        initRV()

        // 1️⃣ 권한 확인 후 미디어 로드
        if (hasStoragePermission()) {
            Log.d("jomi", "Storage permission granted, loading media")
            viewModel.loadMedia(requireContext()) // ✅ 권한이 있으면 미디어 로드
        } else {
            Log.d("jomi", "Requesting storage permission")
            requestStoragePermission() // ❌ 권한 없으면 요청
        }
    }

    override fun initCollector() {
        repeatOnStarted(viewLifecycleOwner) {
            viewModel.mediaList.collectLatest { list ->
                pickMediaAdapter.submitList(list)
            }
        }

        repeatOnStarted(viewLifecycleOwner) {
            viewModel.preview.collectLatest { media ->
                media ?: return@collectLatest

                binding.pvSpSelectedVideo.player = player
                binding.pvSpSelectedVideo.visibility = View.VISIBLE
                binding.ivSpSelectedImage.visibility = View.GONE

                // 1️⃣ 기존 플레이어 해제
                player?.release()
                player = null

                if (media.mediaType == "video") {
                    player = ExoPlayer.Builder(requireContext()).build().apply {
                        setMediaItem(MediaItem.fromUri(Uri.parse(media.mediaUrl)))
                        prepare()
                        playWhenReady = true
                        repeatMode = Player.REPEAT_MODE_ONE
                        volume = 0f
                    }

                } else {
                    binding.pvSpSelectedVideo.player = null
                    binding.pvSpSelectedVideo.visibility = View.GONE
                    binding.ivSpSelectedImage.visibility = View.VISIBLE

                    Glide.with(requireContext())
                        .load(media.mediaUrl)
                        .into(binding.ivSpSelectedImage)
                }
            }
        }
    }

    override fun onDestroyView() {
        binding.pvSpSelectedVideo.player = null
        player?.release()
        player = null
        super.onDestroyView()
    }

    private fun setupToolbar() {
//        binding.tbSp.setOnMenuItemClickListener { menuItem ->
//            when (menuItem.itemId) {
//                R.id.action_next -> {
//                    val selectedMediaList = viewModel.selectedMediaList.value
//                    val selectedMediaParcelableList = selectedMediaList.map { SelectedMediaInfo(it) }.toTypedArray()
//                    if (selectedMediaList.isEmpty().not()) {
//                        val action = SelectMediaFragmentDirections.actionSelectMediaFragmentToCreatePostFragment(selectedMediaParcelableList)
//                        findNavController().navigate(action)
//                    }
//                    true
//                }
//                else -> false
//            }
//        }
    }

    private fun initRV() {
        binding.rvCpPhotoPicker.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = pickMediaAdapter
        }
    }

    private fun hasStoragePermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED
        } else {
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        }
    }

    // 3️⃣ 권한 요청
    private fun requestStoragePermission() {
        requestPermissionsLauncher.launch(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                arrayOf(
                    Manifest.permission.READ_MEDIA_IMAGES,
                    Manifest.permission.READ_MEDIA_VIDEO
                )
            } else {
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        )
    }

    // 4️⃣ 권한 요청 결과 처리
    private val requestPermissionsLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions.values.all { it }) {
                Log.d("jomi", "All permissions granted, loading media")
                viewModel.loadMedia(requireContext()) // ✅ 권한 승인 후 미디어 로드
            } else {
                Log.d("jomi", "Permission denied")
            }
        }
}
