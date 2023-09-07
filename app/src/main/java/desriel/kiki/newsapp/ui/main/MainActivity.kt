package desriel.kiki.newsapp.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import desriel.kiki.newsapp.databinding.ActivityMainBinding
import desriel.kiki.newsapp.ui.MainViewModel
import desriel.kiki.newsapp.ui.main.home.NewsAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        val viewPager : ViewPager2 = binding.viewPager
        val tabLayout : TabLayout = binding.tabLayout

        viewPager.adapter = MainViewPagerAdapter(this)
        TabLayoutMediator(tabLayout, viewPager){tab, position ->
            when(position){
                0 -> tab.text = "Latest"
                1 -> tab.text = "Popular"
                2 -> tab.text = "Topic"
            }
        }.attach()
        setContentView(view)


    }

/*    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_main)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }*/

}
