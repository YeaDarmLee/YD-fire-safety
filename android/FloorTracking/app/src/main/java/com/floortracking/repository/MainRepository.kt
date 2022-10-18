package com.floortracking.repository

import com.floortracking.api.ApiService
import com.floortracking.api.safeCall

class MainRepository(private val apiService: ApiService) {

    fun requestSeaLevel(lat: Float, lon: Float) = safeCall {
        apiService.requestCurrentWeather(lat, lon).sealevel()
    }
}