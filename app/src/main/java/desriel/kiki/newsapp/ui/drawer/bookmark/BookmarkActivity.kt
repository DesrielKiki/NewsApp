package desriel.kiki.newsapp.ui.drawer.bookmark

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import desriel.kiki.newsapp.databinding.ActivityBookMarkBinding
import desriel.kiki.newsapp.ui.MainViewModel
import desriel.kiki.newsapp.ui.detail.DetailViewModel
import desriel.kiki.newsapp.ui.main.activity.MainActivity

class BookmarkActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookMarkBinding
    private val bookmarkViewModel: BookmarkViewModel by viewModels()
    private val detailViewModel: DetailViewModel by viewModels()
    private lateinit var bookmarkAdapter: BookmarkAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookMarkBinding.inflate(layoutInflater)
        val view = binding.root

        bookmarkAdapter = BookmarkAdapter(detailViewModel)
        binding.rvBookmark.adapter = bookmarkAdapter
        binding.rvBookmark.layoutManager = LinearLayoutManager(this)

        binding.bookmarkToolbar.setNavigationOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        bookmarkViewModel.getBookmarkData.observe(this) {
            if (it.isEmpty()){
                binding.tvStatus.visibility = View.VISIBLE
            }else {
                binding.tvStatus.visibility = View.GONE
            }
            bookmarkAdapter.submitList(it)
        }
        setContentView(view)
    }


}
