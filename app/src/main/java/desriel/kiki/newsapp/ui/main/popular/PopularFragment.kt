package desriel.kiki.newsapp.ui.main.popular

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import desriel.kiki.newsapp.R
import desriel.kiki.newsapp.databinding.FragmentPopularBinding
import desriel.kiki.newsapp.model.NewsResponse
import desriel.kiki.newsapp.ui.MainViewModel

class PopularFragment : Fragment() {

    private var _binding: FragmentPopularBinding? = null
    private val binding get() = _binding!!

    private val popularAdapter: PopularAdapter by lazy { PopularAdapter() }
    private val viewModel: MainViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPopularBinding.inflate(inflater, container, false)

        dataProcessing()
        return binding.root
    }

    private fun setupRecyclerView(newsResponse: NewsResponse) {
        binding.rvPopular.adapter = popularAdapter
        binding.rvPopular.layoutManager = LinearLayoutManager(context)
        popularAdapter.submitList(newsResponse.data)
    }
    private fun dataProcessing() {
        viewModel.getNewsData()

        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) binding.tvStatus.text = resources.getString(R.string.loading)
        }
        viewModel.isError.observe(viewLifecycleOwner) {
            if (it) {
                binding.tvStatus.text = viewModel.errorMessage
            }
        }
        viewModel.newsData.observe(viewLifecycleOwner) {
            binding.tvStatus.visibility = View.INVISIBLE
            setupRecyclerView(it)
        }

    }
}