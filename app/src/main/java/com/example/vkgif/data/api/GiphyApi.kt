package com.example.vkgif.data.api

import com.example.vkgif.domain.models.GifImage
import com.example.vkgif.util.Constants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GiphyApi {
    @GET("search?api_key=${API_KEY}&limit=25")
    suspend fun getGifBySearch(@Query("q") search: String, @Query("offset") offset: Int) : Response<GifImage>
}