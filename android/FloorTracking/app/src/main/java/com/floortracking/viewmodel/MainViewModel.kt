package com.floortracking.viewmodel

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.floortracking.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository): ViewModel() {

    val location = MutableLiveData<Location>()

    fun requestSeaLevel(lat: Float, lon: Float) = mainRepository.requestSeaLevel(lat, lon)
}