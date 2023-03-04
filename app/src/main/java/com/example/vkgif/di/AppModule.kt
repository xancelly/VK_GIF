package com.example.vkgif.di

import com.example.vkgif.data.api.GiphyApi
import com.example.vkgif.data.repository.GiphyRepositoryImpl
import com.example.vkgif.domain.repository.GiphyRepository
import com.example.vkgif.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideGiphyApi(): GiphyApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GiphyApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGiphyRepository(giphyApi: GiphyApi): GiphyRepository {
        return GiphyRepositoryImpl(giphyApi)
    }
}