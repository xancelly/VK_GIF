package com.example.vkgif.presentation.fragments.search_fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vkgif.domain.models.GifImage
import com.example.vkgif.domain.repository.GiphyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel
@Inject
constructor(private val giphyRepository: GiphyRepository): ViewModel() {
    //Поле, которое хранит последний запрос на поиск
    private var lastSearchQuery: String? = null
    //Поле, которое используется для хранения ответа от сервера Giphy
    private val searchResponse = MutableLiveData<GifImage>()

    val search: LiveData<GifImage>
        get() = searchResponse

    /*Функция, которая загружает список GIF из репозитория и обновляет значения в LiveData.
    * Если запрос является дубликатом последнего, функция загружает дополнительные данные и
    * объединяет их с текущими данными. Если запрос не является дубликатом последнего,
    * функция выполняет новый запрос и обновляет значения LiveData. */
    private fun getGifsBySearch(search: String, offset: Int) = viewModelScope.launch {
        if (lastSearchQuery == search) {
            val currentGifs = searchResponse.value
            currentGifs?.let {
                val newData = giphyRepository.getGifBySearch(search = search, offset = offset).body()?.data
                newData?.let {
                    val updatedData = currentGifs?.data?.plus(it) ?: it
                    searchResponse.postValue(currentGifs.copy(data = updatedData))
                }
            }
        } else {
            val resp = giphyRepository.getGifBySearch(search = search, offset = offset)
            if (resp.isSuccessful) {
                searchResponse.postValue(resp.body())
                lastSearchQuery = search
                Log.i("getGifs", resp.message())
            } else {
                Log.e("getGifs", resp.message())
            }
        }
    }

    fun getGifsBySearchResult(search: String, offset: Int) = getGifsBySearch(search = search, offset = offset)
}