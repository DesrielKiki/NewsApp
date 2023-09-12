package desriel.kiki.newsapp.ui.main.popular

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import desriel.kiki.newsapp.databinding.ItemPopularBinding
import desriel.kiki.newsapp.data.model.NewsData
import desriel.kiki.newsapp.ui.detail.DetailActivity
import desriel.kiki.newsapp.util.getTimeAgo

class PopularAdapter :
    androidx.recyclerview.widget.ListAdapter<NewsData, PopularAdapter.ViewHolder>(
        PopularDiffCallback()
    ) {
    inner class ViewHolder(private val binding: ItemPopularBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(newsData: NewsData){
                binding.tvPopTitle.text = newsData.title
                val dateText = newsData.createdAt?.getTimeAgo()
                val subTitle = "${newsData.newsCategory?.title} - $dateText"
                binding.tvInfo.text = subTitle
                Glide.with(binding.root)
                    .load("https://tamasya.technice.id/${newsData.thumb}")
                    .into(binding.ivNewsThumbnail)
                binding.icCamera.visibility = View.INVISIBLE
            }
    }

    private class PopularDiffCallback : DiffUtil.ItemCallback<NewsData>() {
        override fun areItemsTheSame(oldItem: NewsData, newItem: NewsData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NewsData, newItem: NewsData): Boolean {
            return oldItem.id == newItem.id
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPopularBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val newsData = getItem(position)
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("news id", newsData.id)
            context.startActivity(intent)
        }
        holder.bind(newsData)
    }


}