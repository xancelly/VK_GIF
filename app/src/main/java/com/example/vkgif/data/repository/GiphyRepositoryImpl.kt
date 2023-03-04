package com.example.vkgif.data.repository

import com.example.vkgif.data.api.GiphyApi
import com.example.vkgif.domain.models.GifImage
import com.example.vkgif.domain.repository.GiphyRepository
import retrofit2.Response
import javax.inject.Inject

class GiphyRepositoryImpl
@Inject
constructor(private val giphyApi: GiphyApi): GiphyRepository {
    override suspend fun getGifBySearch(search: String, offset: Int): Response<GifImage> = giphyApi.getGifBySearch(search = search, offset = offset)
}