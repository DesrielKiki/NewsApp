package desriel.kiki.newsapp.ui.main.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuItemCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import desriel.kiki.newsapp.R
import desriel.kiki.newsapp.databinding.ActivityMainBinding
import desriel.kiki.newsapp.ui.search.SearchDialog
import desriel.kiki.newsapp.ui.search.SearchableActivity
import desriel.kiki.newsapp.ui.main.MainViewPagerAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toolBar: androidx.appcompat.widget.Toolbar
    private val searchDialog = SearchDialog()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        initViewPager()
        initToolbarFunction()
        setContentView(view)


    }

    private fun initToolbarFunction() {
        toolBar = binding.mainToolbar

        toolBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_search -> {
                    val intent = Intent(this, SearchableActivity::class.java)
                    this.startActivity(intent)
                }
            }
            true
        }
    }

    private fun initViewPager() {
        val viewPager: ViewPager2 = binding.viewPager
        val tabLayout: TabLayout = binding.tabLayout

        viewPager.adapter = MainViewPagerAdapter(this)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Latest"
                1 -> tab.text = "Popular"
                2 -> tab.text = "Topic"
            }
        }.attach()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val searchItem = menu?.findItem(R.id.menu_search)
        val searchView = searchItem?.actionView as? SearchView

        if (searchView != null) {
            Log.d("MainActivity", "searchView is already installed")

            // SearchView sudah diinisialisasi dengan benar
            // Lakukan operasi yang Anda butuhkan dengan searchView di sini
        } else {
            // SearchView belum diinisialisasi atau objek actionView adalah null
            // Anda dapat mencetak pesan log atau melakukan tindakan lain sesuai kebutuhan Anda
            Log.e("MainActivity", "SearchView is null or not properly initialized")
        }
        return super.onCreateOptionsMenu(menu)
    }

    private fun initSearchView(menuItem: MenuItem?) {
        val searchView = MenuItemCompat.getActionView(menuItem) as SearchView


        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))

        // Mendengarkan perubahan teks pencarian
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Handle saat pengguna menekan tombol cari
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Handle perubahan teks pencarian dan tampilkan saran pencarian
                // Di sini Anda dapat memperbarui daftar saran pencarian
                return true
            }
        })

    }


}
