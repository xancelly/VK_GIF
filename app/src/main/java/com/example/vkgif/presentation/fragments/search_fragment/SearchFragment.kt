package com.example.vkgif.presentation.fragments.search_fragment

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.viewModels
import com.example.vkgif.R
import com.example.vkgif.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

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
        observeData()

        val searchEditText = view.findViewById<EditText>(R.id.searchEditText)
        val searchButton = view.findViewById<Button>(R.id.searchButton)
        searchButton.setOnClickListener {
            getGifBySearch(searchEditText.text.toString(), 0)
        }
    }

    private fun getGifBySearch(search: String, offset: Int) {
        viewModel.updateGifs(search, offset)
    }

    private fun observeData() {
        searchAdapter = SearchAdapter()
        binding.gifsRecyclerView.apply {
            adapter = searchAdapter
        }
        viewModel.searchResponse.observe(viewLifecycleOwner) { result ->
            searchAdapter.gifList = result.data
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        searchBinding = null
    }
}