package desriel.kiki.newsapp.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import desriel.kiki.newsapp.databinding.FragmentHomeBinding
import desriel.kiki.newsapp.model.NewsResponse
import desriel.kiki.newsapp.ui.MainViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var newsAdapter: NewsAdapter
    private val viewModel: MainViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)


        newsAdapter = NewsAdapter()
        binding.rvNewsList.adapter = newsAdapter
        binding.rvNewsList.layoutManager = LinearLayoutManager(context)
        dataProcessing()

        return binding.root
    }
    private fun setData(newsResponse: NewsResponse) {
        newsAdapter.submitList(newsResponse.data)
    }

    private fun dataProcessing() {
        viewModel.getNewsData()

        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) binding.shimmerContainer.visibility = View.VISIBLE
            if (it) binding.shimmerContainer.startShimmer()
        }
        viewModel.isError.observe(viewLifecycleOwner) {
            if (it) {
                binding.rvNewsList.visibility = View.GONE
                binding.shimmerContainer.visibility = View.GONE
                binding.tvStatus.text = viewModel.errorMessage
            }
        }
        viewModel.newsData.observe(viewLifecycleOwner) {
            binding.tvStatus.visibility = View.INVISIBLE
            binding.shimmerContainer.stopShimmer()
            binding.shimmerContainer.visibility = View.GONE
            setData(it)
        }

    }
}