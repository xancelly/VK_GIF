package com.example.vkgif.presentation.fragments.search_fragment

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.vkgif.R
import com.example.vkgif.databinding.FragmentSearchBinding
import com.example.vkgif.domain.models.Data
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(), SearchAdapter.OnItemClickListener {

    private var recyclerViewState: Parcelable? = null
    private var searchBinding: FragmentSearchBinding? = null
    private val binding get() = searchBinding!!
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var searchAdapter: SearchAdapter

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
        }

        observeData()

        val searchEditText = view.findViewById<EditText>(R.id.searchEditText)
        val searchButton = view.findViewById<Button>(R.id.searchButton)
        searchButton.setOnClickListener {
            getGifBySearch(searchEditText.text.toString(), 0)
        }
    }

    private fun getGifBySearch(search: String, offset: Int) {
        viewModel.getGifsBySearchResult(search, offset)
    }

    private fun observeData() {
        viewModel.search.observe(viewLifecycleOwner) { result ->
            searchAdapter.gifList = result.data
            recyclerViewState?.let {
                binding.gifsRecyclerView.layoutManager?.onRestoreInstanceState(it)
            }
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