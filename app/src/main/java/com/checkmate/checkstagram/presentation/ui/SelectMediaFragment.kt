package com.checkmate.checkstagram.presentation.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.checkmate.checkstagram.databinding.FragmentSelectMediaBinding
import com.checkmate.checkstagram.presentation.adapter.PickMediaAdapter
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
    private val pickMediaAdapter = PickMediaAdapter{ selectedUri ->
        viewModel.toggleSelection(selectedUri)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
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
                Log.d("jomi", "Media List Updated: ${list.size} items")
                pickMediaAdapter.submitList(list)
            }
        }

        repeatOnStarted(viewLifecycleOwner) {
            viewModel.preview.collectLatest { uri ->
                Glide.with(requireContext())
                    .load(uri)
                    .into(binding.ivSpSelectedImage)
            }
        }
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
