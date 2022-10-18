package com.floortracking.di

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.floortracking.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetWorkModule {

    @Singleton
    @Provides
    fun provideWeatherApiService(): ApiService {
        val client = OkHttpClient.Builder().run {
            addNetworkInterceptor(StethoInterceptor())
            build()
        }
        return Retrofit.Builder()
            .client(client)
            .baseUrl("https://api.openweathermap.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}