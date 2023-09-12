package desriel.kiki.newsapp.ui.drawer.bookmark

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import desriel.kiki.newsapp.data.model.BookmarkData
import desriel.kiki.newsapp.databinding.ItemBookmarkBinding
import desriel.kiki.newsapp.ui.detail.DetailActivity
import desriel.kiki.newsapp.ui.detail.DetailViewModel
import desriel.kiki.newsapp.util.getTimeAgo

class BookmarkAdapter(
    private val detailViewModel: DetailViewModel
) : ListAdapter<BookmarkData, BookmarkAdapter.ViewHolder>(NewsDiffCallback()) {
    inner class ViewHolder(val binding: ItemBookmarkBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(bookmarkData: BookmarkData) {
            binding.tvNewsTitle.text = bookmarkData.newsTitle
            binding.tvDescription.text = bookmarkData.content
            val dateText = bookmarkData.createdAt?.getTimeAgo()
            val infoText = "${bookmarkData.newsCategory} - $dateText"
            binding.tvNewsInfo.text = infoText
            Glide.with(binding.root)
                .load("https://tamasya.technice.id/${bookmarkData.thumb}")
                .into(binding.ivNewsThumbnail)
            /*
                binding.tvNewsCreatedDate.text = newsData.createdAt
*/
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemBookmarkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bookmarkData = getItem(position)
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("news id", bookmarkData.newsId)
            context.startActivity(intent)
        }
        holder.binding.btnBookmark.setOnClickListener {
            showDeleteConfirmationDialog(holder.itemView.context) { confirmed ->
                if (confirmed) {
                    // Menghapus bookmark
                    detailViewModel.deleteBookmark(bookmarkData.newsId)
                    // Hapus item dari adapter
                    val positionToRemove = holder.adapterPosition
                    if (positionToRemove != RecyclerView.NO_POSITION) {
                        currentList.toMutableList().removeAt(positionToRemove)
                        notifyItemRemoved(positionToRemove)
                    }
                }
            }
        }
        holder.bind(bookmarkData)
    }

    private class NewsDiffCallback : DiffUtil.ItemCallback<BookmarkData>() {
        override fun areItemsTheSame(oldItem: BookmarkData, newItem: BookmarkData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: BookmarkData, newItem: BookmarkData): Boolean {
            return oldItem.id == newItem.id
        }

    }

    private fun showDeleteConfirmationDialog(
        context: Context,
        onDeleteConfirmed: (Boolean) -> Unit
    ) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("DELETE BOOKMARK")
        builder.setMessage("Do you want to delete this bookmark ?")
        builder.setPositiveButton("Delete") { _, _ ->
            onDeleteConfirmed(true)
        }
        builder.setNegativeButton("Cancel") { _, _ ->
            onDeleteConfirmed(false)
        }
        builder.create().show()
    }

}
