package com.directionfinding.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DirectionMapViewModel @Inject constructor(): ViewModel() {
    val distance = mutableStateOf(0)
}