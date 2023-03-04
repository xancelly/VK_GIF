package com.example.vkgif.domain.repository

import com.example.vkgif.domain.models.GifImage
import retrofit2.Response

interface GiphyRepository {
    suspend fun getGifBySearch(search: String, offset: Int): Response<GifImage>
    suspend fun getGifInfoById(id: Int): Response<GifImage>
}