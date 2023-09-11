package desriel.kiki.newsapp.ui.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import desriel.kiki.newsapp.R
import desriel.kiki.newsapp.databinding.ActivityDetailBinding
import desriel.kiki.newsapp.model.DetailResponse
import desriel.kiki.newsapp.ui.MainViewModel
import desriel.kiki.newsapp.ui.main.activity.MainActivity
import desriel.kiki.newsapp.util.getTimeAgo

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding


    private val viewModel: MainViewModel by viewModels()
    private var newsId: String? = ""
    private lateinit var toolBar: androidx.appcompat.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        toolBar = binding.toolbar

        val receivedData = intent.getStringExtra("news id")
        newsId = receivedData
        Log.d("detail activity", "received data = $receivedData")
        dataProcessing()
        toolBarFunction()
        setContentView(view)
    }


    private fun toolBarFunction() {
        var isBookmarked = false

        toolBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {

                /**
                 * bookmark function
                 */

                R.id.menu_bookmark -> {
                    // Toggle the state and update the icon accordingly
                    isBookmarked = !isBookmarked // Invert the bookmark state

                    if (isBookmarked) {
                        menuItem.setIcon(R.drawable.ic_bookmarkfilled) // Bookmarked icon
                        // Perform bookmarking logic here if needed
                    } else {
                        menuItem.setIcon(R.drawable.ic_bookmark) // Unbookmarked icon
                        // Perform unbookmarking logic here if needed
                    }
                    true // Return true to indicate that the menu item click is handled
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
        Log.d("detail fragment", "before processing error = ${viewModel.isError.value}")
        viewModel.getDetailData(newsId)

        viewModel.isLoading.observe(this) {
            if (it) binding.shimmerContainer.visibility = View.VISIBLE
            if (it) binding.shimmerContainer.startShimmer()

        }
        viewModel.isError.observe(this) {
            if (it) {
                binding.tvStatus.text = viewModel.errorMessage
                Log.d("detail fragment", "after processing error = ${viewModel.isError.value}")

            }
        }
        viewModel.detailData.observe(this) { detailData ->
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
        }
    }
}