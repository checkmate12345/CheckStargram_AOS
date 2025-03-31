import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.checkmate.checkstagram.databinding.ItemPickMediaBinding
import com.checkmate.checkstagram.domain.model.MediaInfo

class PickMediaAdapter(
    private val onItemSelected: (MediaInfo) -> Unit
) : ListAdapter<MediaInfo, PickMediaAdapter.ViewHolder>(diffUtil) {

    private val selectedItems = mutableListOf<MediaInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemPickMediaBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemPickMediaBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MediaInfo) {
            // 1️⃣ 이미지 vs 동영상 구분하여 썸네일 로드
            if (item.mediaType == "video") {
                Glide.with(binding.root)
                    .load(item.mediaUrl) // 또는 썸네일 추출 logic
                    .frame(1000000) // 1초 지점 썸네일
                    .into(binding.itemIvFeedImage)
            } else {
                Glide.with(binding.root)
                    .load(item.mediaUrl)
                    .into(binding.itemIvFeedImage)
            }

            // 2️⃣ 선택 인덱스 표시
            val index = selectedItems.indexOf(item)
            if (index != -1) {
                binding.itemTvFeedIndex.text = (index + 1).toString()
                binding.itemTvFeedIndex.isSelected = true
            } else {
                binding.itemTvFeedIndex.text = ""
                binding.itemTvFeedIndex.isSelected = false
            }

            // 3️⃣ 클릭 처리
            binding.itemIvFeedImage.setOnClickListener {
                if (selectedItems.contains(item)) {
                    selectedItems.remove(item)
                } else {
                    selectedItems.add(item)
                }
                notifyDataSetChanged()
                onItemSelected(item)
            }
        }
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<MediaInfo>() {
            override fun areItemsTheSame(oldItem: MediaInfo, newItem: MediaInfo): Boolean =
                oldItem.mediaUrl == newItem.mediaUrl

            override fun areContentsTheSame(oldItem: MediaInfo, newItem: MediaInfo): Boolean =
                oldItem == newItem
        }
    }
}
