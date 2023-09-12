package desriel.kiki.newsapp.ui.main.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.ui.AppBarConfiguration
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import desriel.kiki.newsapp.R
import desriel.kiki.newsapp.databinding.ActivityMainBinding
import desriel.kiki.newsapp.ui.drawer.bookmark.BookmarkActivity
import desriel.kiki.newsapp.ui.main.MainViewPagerAdapter
import desriel.kiki.newsapp.ui.search.SearchableActivity
import desriel.kiki.newsapp.ui.drawer.settings.SettingsActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toolBar: androidx.appcompat.widget.Toolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        initViewPager()
        initToolbarFunction()
        navigationSetup()
        setContentView(view)
    }

    private fun navigationSetup(){
        val navView = binding.navProfile

        val bookmarkMenu = navView.menu.findItem(R.id.drawer_bookmark)
        bookmarkMenu.setOnMenuItemClickListener {
            val intent = Intent(this, BookmarkActivity::class.java)
            startActivity(intent)
            true
        }
        val settingMenu = navView.menu.findItem(R.id.drawer_setting)
        settingMenu.setOnMenuItemClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
            true
        }
        val logoutMenu = navView.menu.findItem(R.id.drawer_logout)
        logoutMenu.setOnMenuItemClickListener {
            finish()
             true
        }

    }

    private fun initToolbarFunction() {
        toolBar = binding.mainToolbar

        toolBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_search -> {
                    val intent = Intent(this, SearchableActivity::class.java)
                    this.startActivity(intent)
                }
                R.id.menu_profile ->{
                    binding.drawerProfile.openDrawer(GravityCompat.END)

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
}
