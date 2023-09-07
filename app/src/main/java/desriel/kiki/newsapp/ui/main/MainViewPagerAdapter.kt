package desriel.kiki.newsapp.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import desriel.kiki.newsapp.ui.main.home.HomeFragment
import desriel.kiki.newsapp.ui.main.popular.PopularFragment
import desriel.kiki.newsapp.ui.main.topic.TopicFragment

class MainViewPagerAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> HomeFragment()
            1 -> PopularFragment()
            2 -> TopicFragment()
            else -> HomeFragment()
        }
    }
}