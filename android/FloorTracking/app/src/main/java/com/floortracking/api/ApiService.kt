package com.floortracking.api

import com.floortracking.api.model.WeatherInfo
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    private val openWeatherKey: String
        get() = "936ec179727ae4e27dce1488a3970815"

    @GET("data/2.5/weather")
    suspend fun requestCurrentWeather(
        @Query("lat") lat : Float,
        @Query("lon") lon : Float,
        @Query("appid") appid : String = openWeatherKey
    ): WeatherInfo
}