package com.floortracking.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.floortracking.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FloorViewModel @Inject constructor(): ViewModel() {

    val currentFloor = mutableStateOf(1)
    val seaLevel = mutableStateOf(0) //= MutableLiveData(0)
    val altitude = mutableStateOf(0)
    val pressure = mutableStateOf(0f)
}