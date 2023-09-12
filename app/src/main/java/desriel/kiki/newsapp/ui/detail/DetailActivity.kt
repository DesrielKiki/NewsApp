package desriel.kiki.newsapp.ui.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import desriel.kiki.newsapp.R
import desriel.kiki.newsapp.data.model.BookmarkData
import desriel.kiki.newsapp.data.model.DetailResponse
import desriel.kiki.newsapp.databinding.ActivityDetailBinding
import desriel.kiki.newsapp.ui.MainViewModel
import desriel.kiki.newsapp.ui.main.activity.MainActivity
import desriel.kiki.newsapp.util.getTimeAgo

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding


    private val detailViewModel: DetailViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()
    private var newsId: String? = ""
    private var newsTitle: String? = null
    private var createdAt: String? = null
    private var newsCategory: String? = null
    private var content: String? = null
    private var thumb: String? = null
    private lateinit var toolBar: androidx.appcompat.widget.Toolbar

    private var isBookmarked: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        toolBar = binding.toolbar

        val receivedData = intent.getStringExtra("news id")
        newsId = receivedData
        Log.d("detail activity", "received data = $receivedData")
        checkBookmarkStatus()

        dataProcessing()
        toolBarFunction()
        setContentView(view)
    }


    private fun toolBarFunction() {

        toolBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {

                /**
                 * bookmark function
                 */

                R.id.menu_bookmark -> {
                    checkBookmarkStatus()
                    val bookmarkedNews = BookmarkData(
                        0L,
                        newsId,
                        newsTitle,
                        createdAt,
                        newsCategory,
                        content,
                        thumb,
                        true
                    )
                    if (!isBookmarked) {
                        menuItem.setIcon(R.drawable.ic_bookmarkfilled)
                        detailViewModel.insertBookmark(bookmarkedNews)

                        val rootView = findViewById<View>(android.R.id.content)
                        Snackbar.make(rootView, "content bookmarked successfully", Snackbar.LENGTH_SHORT)
                            .show()

                    } else {
                        menuItem.setIcon(R.drawable.ic_bookmark)
                        detailViewModel.deleteBookmark(newsId)
                        val rootView = findViewById<View>(android.R.id.content)
                        Snackbar.make(rootView, "content deleted from bookmark", Snackbar.LENGTH_SHORT)
                            .show()


                    }
                    true
                }

                /**
                 * share function
                 */

                R.id.menu_share -> {
                    val sharingIntent = Intent(Intent.ACTION_SEND)
                    sharingIntent.type = "text/plain"
                    val shareBody =
                        "Check out this news!! \n https://tamasya.technice.id/api/mobile/news/$newsId"
                    sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
                    startActivity(Intent.createChooser(sharingIntent, "Share via"))

                    true
                }

                else -> false // Handle other menu items (if any)
            }
        }
        /**
         * navigate back function
         */
        toolBar.setNavigationOnClickListener {
            val intent = Intent(this@DetailActivity, MainActivity::class.java)
            this.startActivity(intent)
            Log.d("detail activity", " back to main activity triggered")

        }
    }

    private fun dataProcessing() {
        Log.d("detail fragment", "before processing error = ${mainViewModel.isError.value}")
        mainViewModel.getDetailData(newsId)

        mainViewModel.isLoading.observe(this) {
            if (it) binding.shimmerContainer.visibility = View.VISIBLE
            if (it) binding.shimmerContainer.startShimmer()

        }
        mainViewModel.isError.observe(this) {
            if (it) {
                binding.tvStatus.text = mainViewModel.errorMessage
                Log.d("detail fragment", "after processing error = ${mainViewModel.isError.value}")

            }
        }
        mainViewModel.detailData.observe(this) { detailData ->
            binding.tvStatus.visibility = View.INVISIBLE
            binding.shimmerContainer.visibility = View.GONE
            binding.shimmerContainer.stopShimmer()
            binding.ivNewsThumbnail.visibility = View.VISIBLE
            binding.Separator.visibility = View.VISIBLE
            setDetailContent(detailData)

        }
    }

    private fun setDetailContent(detailData: DetailResponse) {
        detailData.data.let { detailData ->
            binding.tvNewsCategory.text = "${detailData?.newsCategory?.title}"
            binding.tvNewsTitle.text = "${detailData?.title}"
            val createdTime = detailData?.createdAt?.getTimeAgo()
            binding.tvNewsCreatedDate.text = "$createdTime"
            binding.tvDescription.text = "${detailData?.content}"

            this.let {
                Glide.with(it)
                    .load("https://tamasya.technice.id/${detailData?.thumb}")
                    .into(binding.ivNewsThumbnail)
            }

            newsTitle = detailData?.title
            createdAt = detailData?.createdAt
            newsCategory = detailData?.newsCategory?.title
            content = detailData?.content
            thumb = detailData?.thumb


        }
    }
    private fun checkBookmarkStatus(){
        val bookmarkData = detailViewModel.getBookmarkByNewsId(newsId)
        bookmarkData.observe(this) {
            isBookmarked = it != null
            updateUIBasedOnBookmarkStatus()
        }
        Log.d("detail activity", "bookmark status = $isBookmarked")
    }

    private fun updateUIBasedOnBookmarkStatus() {
        val menu = toolBar.menu
        val bookmarkMenuItem = menu.findItem(R.id.menu_bookmark)
        Log.d("detail activity", "bookmark status = $isBookmarked")
        if (isBookmarked) {
            bookmarkMenuItem.setIcon(R.drawable.ic_bookmarkfilled)
        } else {
            bookmarkMenuItem.setIcon(R.drawable.ic_bookmark)
        }
    }
}