package com.floortracking.ui.floor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.ComposeView
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.floortracking.R
import com.floortracking.api.ApiResponse
import com.floortracking.ui.base.BaseFragment
import com.floortracking.ui.theme.FloorTrackingTheme
import com.floortracking.util.AppPreferences
import com.floortracking.util.MathUtils
import com.floortracking.viewmodel.FloorViewModel
import com.floortracking.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.pow
import kotlin.math.roundToInt

@AndroidEntryPoint
class FloorFragment: BaseFragment() {

    @Inject
    lateinit var appPreferences: AppPreferences

    private val mainViewModel: MainViewModel by activityViewModels()

    private val viewModel: FloorViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initData()
        altitudeLaunch()
        return ComposeView(inflater.context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            setContent {
                CompositionLocalProvider(
                   // LocalBackPressedDispatcher provides requireActivity().onBackPressedDispatcher
                ) {
                    FloorTrackingTheme {
                        FloorUI(titleName = getString(R.string.floor_info_modify),
                            floorText = "${viewModel.currentFloor.value}F",
                            hpaText = "${mainViewModel.seaLevel.value}",
                            meterText = String.format("%.2f", viewModel.altitude.value),
                            onBackClick = {
                                requireActivity().onBackPressed()
                            },
                            settingAlignAction = {
                            //    calAltitude()
                                //showDialog.value = true
                                //titleText.value = "호잇 둘리는 초능력 내친구"

                            })
                    }
                }
            }
        }
    }
    private fun initData() {
        viewModel.floorHeight.value = appPreferences.groundHeight
        viewModel.alignAltitude.value = appPreferences.alignAltitude

        viewModel.groundFloor.value = appPreferences.groundFloor
        viewModel.groundHeight.value = appPreferences.groundHeight
        viewModel.middleFloor.value = appPreferences.middleFloor
        viewModel.middleHeight.value = appPreferences.middleHeight
        viewModel.underGroundFloor.value = appPreferences.underGroundFloor
        viewModel.underGroundHeight.value = appPreferences.underGroundHeight

    }
    private fun altitudeLaunch() {
        lifecycleScope.launch {
            while (true) {
                delay(1000)
                val altitude = MathUtils.calAltitude(mainViewModel.seaLevel.value.toFloat(), mainViewModel.pressure.value)
                viewModel.altitude.value =  altitude
                Log.d("altitude", "${viewModel.altitude.value}, ${viewModel.altitude.value.toInt()}")
            //    viewModel.floorHeight.value?.run {
                    val diffHeight = altitude - (viewModel.alignAltitude.value?: 0f)

                    if (diffHeight < 0) {
                        viewModel.underGroundHeight.value?.run {
                            val floor = (diffHeight / this).roundToInt()
                            if (floor < 0) {
                                viewModel.currentFloor.value =  floor
                            } else {
                                viewModel.currentFloor.value = 1 + floor
                            }
                        }

                    } else {

                        val middleTotalHeight = (viewModel.middleHeight.value?:0f) * (viewModel.middleFloor.value?:0)
                        if (diffHeight > middleTotalHeight) {
                            viewModel.groundHeight.value?.run {
                                viewModel.currentFloor.value = (viewModel.middleFloor.value?:0) + (((diffHeight - middleTotalHeight) / this).roundToInt())
                            }
                        } else {
                            viewModel.middleHeight.value?.run {
                                viewModel.currentFloor.value = 1 + (diffHeight / this).roundToInt()
                            }
                        }

                    }
            //        viewModel.currentFloor.value = 1 + (diffHeight / this).toInt()
            //    }
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
    }
}