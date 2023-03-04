package com.example.vkgif.presentation.fragments.search_fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vkgif.domain.models.Data
import com.example.vkgif.domain.models.GifImage
import com.example.vkgif.domain.repository.GiphyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel
@Inject
constructor(private val giphyRepository: GiphyRepository): ViewModel() {
    private val response = MutableLiveData<GifImage>()

    val searchResponse: LiveData<GifImage>
        get() = response

    init {
        getGifs("programming", 0)
    }

    fun updateGifs(search: String, offset: Int) {
        getGifs(search = search, offset = offset)
    }

    private fun getGifs(search: String, offset: Int) = viewModelScope.launch {
        giphyRepository.getGifBySearch(search = search, offset = offset).let { resp ->
            if (resp.isSuccessful) {
                response.postValue(resp.body())
                Log.i("getGifs", resp.message())
            } else {
                Log.e("getGifs", resp.message())
            }
        }
    }
}