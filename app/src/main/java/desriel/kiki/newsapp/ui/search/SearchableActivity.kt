package desriel.kiki.newsapp.ui.search

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import desriel.kiki.newsapp.databinding.ActivitySearchableBinding
import desriel.kiki.newsapp.model.NewsData
import desriel.kiki.newsapp.model.NewsResponse
import desriel.kiki.newsapp.ui.MainViewModel
import desriel.kiki.newsapp.ui.main.activity.MainActivity
import desriel.kiki.newsapp.ui.main.popular.PopularAdapter

class SearchableActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchableBinding
    private lateinit var searchAdapter: SearchAdapter
    private lateinit var popularAdapter: PopularAdapter

    private val filteredNewsList = ArrayList<NewsData>()
    private var popularShow = true
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchableBinding.inflate(layoutInflater)
        val view = binding.root
        /*
                if (Intent.ACTION_SEARCH == intent.action) {
                    intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                    }
                }
        */
        searchAdapter = SearchAdapter()
        popularAdapter = PopularAdapter()
        Log.d("searchable activity", " ${binding.rvPopular.visibility}")


        binding.rvPopular.visibility = View.VISIBLE
        viewModel.newsData.observe(this) {
            initPopularResult(it)
        }
        binding.searchView.requestFocus()
        navigateBackFunction()
        searchFunction()
        popularDataProcessing()
        setContentView(view)

    }


    private fun filterData(query: String?) {
        filteredNewsList.clear()

        val nonNullQuery = query.orEmpty() // Mengganti nilai null dengan string kosong

        if (nonNullQuery.isBlank()) {
            popularDataProcessing()
            Log.d("search activity", "text masih kosong")
        } else {
            // Jika terdapat teks pencarian, saring data berdasarkan teks
            viewModel.allNewsList.value?.let { newsList ->
                for (newsItem in newsList) {
                    val title =
                        newsItem.title.orEmpty() // Mengganti nilai null dengan string kosong
                    val category =
                        newsItem.newsCategory?.title.orEmpty() // Mengganti nilai null dengan string kosong
                    if (title.contains(nonNullQuery, true) || category.contains(
                            nonNullQuery,
                            true
                        )
                    ) {
                        filteredNewsList.add(newsItem)
                    }
                }
            }
            popularShow = false
        }

        // Perbarui adapter dengan data yang telah difilter
        initSearchResult()

        // Periksa apakah daftar berita kosong
        if (filteredNewsList.isEmpty()) {
            if (!popularShow) {
                binding.tvStatus.visibility = View.VISIBLE
                binding.rvSearchResult.visibility = View.GONE
                binding.rvPopular.visibility = View.GONE
                binding.suggestion.visibility = View.GONE
                binding.tvStatus.text = "No News Found"
            }
        } else {
            binding.rvPopular.visibility = View.GONE
            binding.suggestion.visibility = View.GONE
            binding.rvSearchResult.visibility = View.VISIBLE
        }
    }

    private fun popularDataProcessing() {
        viewModel.getNewsData()

        viewModel.isLoading.observe(this) { isLoading ->
            if (isLoading) {
                binding.tvStatus.visibility = View.VISIBLE
                binding.tvStatus.text = "Loading . . ."
            }
        }

        viewModel.isError.observe(this) { isError ->
            if (isError) {
                binding.tvStatus.visibility = View.VISIBLE
                binding.tvStatus.text = viewModel.errorMessage
            }
        }

        viewModel.newsData.observe(this) { newsResponse ->
            if (newsResponse.error != true) {
                binding.tvStatus.text = ""
                initPopularResult(newsResponse)
            }
        }
    }

    private fun initPopularResult(newsResponse: NewsResponse) {
        binding.rvPopular.visibility = View.VISIBLE
        binding.suggestion.visibility = View.VISIBLE
        binding.rvPopular.adapter = popularAdapter
        binding.rvPopular.layoutManager = LinearLayoutManager(this)
        popularAdapter.submitList(newsResponse.data)
        popularShow = true
        binding.rvSearchResult.visibility = View.GONE
        Log.d("searchable activity", " popular result initialized")
        Log.d("searchable activity", " ${binding.rvPopular.visibility}")
    }

    private fun initSearchResult() {
        binding.rvSearchResult.visibility = View.VISIBLE
        binding.rvSearchResult.adapter = searchAdapter
        binding.rvSearchResult.layoutManager = LinearLayoutManager(this)
        searchAdapter.submitList(filteredNewsList)
    }

    private fun navigateBackFunction() {
        binding.toolbar.setNavigationOnClickListener {
            val intent = Intent(this@SearchableActivity, MainActivity::class.java)
            this.startActivity(intent)
            Log.d("detail activity", " back to main activity triggered")

        }
    }

    private fun searchFunction() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d("search activity", "text submitted")

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("search activity", "text change")
                filterData(newText)
                return true
            }
        })

    }
}