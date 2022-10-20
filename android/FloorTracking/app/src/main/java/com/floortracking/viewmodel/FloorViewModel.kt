package com.floortracking.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FloorViewModel @Inject constructor(): ViewModel() {
    val floorHeight = MutableLiveData(0f)
    val alignAltitude = MutableLiveData(0f)

    val currentFloor = mutableStateOf(1)
    val altitude = mutableStateOf(0f)

    val groundFloor = MutableLiveData(0)
    val groundHeight = MutableLiveData(0f)

    val middleFloor = MutableLiveData(0)
    val middleHeight = MutableLiveData(0f)

    val underGroundFloor = MutableLiveData(0)
    val underGroundHeight = MutableLiveData(0f)

}