package com.floortracking.viewmodel

import android.location.Location
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.floortracking.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository): ViewModel() {
    val altitudeEnabled = mutableStateOf(true)
    val seaLevel = mutableStateOf(0)
    val pressure = mutableStateOf(0f)

    val location = MutableLiveData<Location>()

    val sceneFireName = MutableLiveData("")

    val groundFloor = MutableLiveData("")
    val groundHeight = MutableLiveData("")

    val middleFloor = MutableLiveData("")
    val middleHeight = MutableLiveData("")

    val underGroundFloor = MutableLiveData("")
    val underGroundHeight = MutableLiveData("")

    fun requestSeaLevel(lat: Float, lon: Float) = mainRepository.requestSeaLevel(lat, lon)
}