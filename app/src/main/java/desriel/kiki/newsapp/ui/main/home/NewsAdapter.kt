package desriel.kiki.newsapp.ui.main.home

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import desriel.kiki.newsapp.databinding.NewsItemBinding
import desriel.kiki.newsapp.model.NewsData
import desriel.kiki.newsapp.ui.detail.DetailActivity
import desriel.kiki.newsapp.ui.main.MainActivity

class NewsAdapter : ListAdapter<NewsData, NewsAdapter.ViewHolder>(NewsDiffCallback()) {
    inner class ViewHolder(private val binding: NewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(newsData: NewsData) {
            binding.tvNewsTitle.text = newsData.title
            val subTitle = "${newsData.newsCategory?.title} - ${newsData.createdAt}"
            binding.tvNewsCategory.text = subTitle
            Log.d("news adapter", "news id = ${newsData.id}")
            Glide.with(binding.root)
                .load("https://tamasya.technice.id/${newsData.thumb}")
                .into(binding.ivNewsThumbnail)
            binding.icCamera.visibility = View.INVISIBLE
            /*
                binding.tvNewsCreatedDate.text = newsData.createdAt
*/
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

    private class NewsDiffCallback : DiffUtil.ItemCallback<NewsData>() {
        override fun areItemsTheSame(oldItem: NewsData, newItem: NewsData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NewsData, newItem: NewsData): Boolean {
            return oldItem.id == newItem.id
        }

    }
}