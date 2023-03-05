package com.example.vkgif.presentation.fragments.search_fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.vkgif.databinding.FragmentSearchBinding
import com.example.vkgif.domain.models.Data
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(), SearchAdapter.OnItemClickListener {

    private var searchBinding: FragmentSearchBinding? = null
    private val binding get() = searchBinding!!
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var searchAdapter: SearchAdapter
    private var currentOffset = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        searchBinding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchAdapter = SearchAdapter(this)
        binding.gifsRecyclerView.apply {
            adapter = searchAdapter
            addOnScrollListener(object: RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!recyclerView.canScrollVertically(1)) {
                        currentOffset += 25
                        viewModel.getGifsBySearchResult(binding.searchEditText.text.toString(), currentOffset)
                    }
                }
            })
        }

        viewModel.search.observe(viewLifecycleOwner) { result ->
            searchAdapter.gifList = result.data
        }

        binding.searchButton.setOnClickListener {
            currentOffset = 0
            viewModel.getGifsBySearchResult(binding.searchEditText.text.toString(), currentOffset)
        }

        binding.nextPage.setOnClickListener {
            currentOffset += 25
            viewModel.getGifsBySearchResult(binding.searchEditText.text.toString(), currentOffset)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        searchBinding = null
    }

    override fun onItemClick(data: Data) {
        try {
            val direction = SearchFragmentDirections.actionSearchFragmentToDetailFragment(data)
            findNavController().navigate(direction)
        } catch (e: Exception) {
            Log.e("SearchFragment", "Error navigating to DetailFragment: ${e.message}" , e)
        }
    }
}