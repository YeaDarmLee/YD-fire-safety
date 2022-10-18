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
import com.floortracking.viewmodel.FloorViewModel
import com.floortracking.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.pow

@AndroidEntryPoint
class FloorFragment: BaseFragment(), SensorEventListener {

    private val mainViewModel: MainViewModel by activityViewModels()

    private val viewModel: FloorViewModel by viewModels()

    var test = 1000
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initPressure()
        requestSeaLevel()
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
                            hpaText = "${viewModel.seaLevel.value}",
                            meterText = "${viewModel.altitude.value}",
                            settingAlignAction = {
                                calAltitude()
                                //showDialog.value = true
                                //titleText.value = "호잇 둘리는 초능력 내친구"

                            })
                    }
                }
            }
        }
    }

    private fun initPressure() {
        val sensorManager = requireContext().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)?.run {
            sensorManager.registerListener(this@FloorFragment, this, SensorManager.SENSOR_DELAY_UI)
        }
    }
    private fun requestSeaLevel() {
        mainViewModel.viewModelScope.launch(Dispatchers.IO) {
            mainViewModel.requestSeaLevel(mainViewModel.location.value?.latitude?.toFloat() ?: 0.0f, mainViewModel.location.value?.longitude?.toFloat() ?: 0.0f).collect {
                if (it is ApiResponse.Success) {
                    viewModel.seaLevel.value = it.value
                    Log.d("test" , "${it.value}")
                }

            }
        }
    }

    private fun calAltitude() {
        val p0 = viewModel.seaLevel.value.toFloat()
        val p = viewModel.pressure.value

        if (p0 <= 0 || p <= 0) {
            return
        }
        viewModel.altitude.value =  (44300*(1 - (p / p0).toDouble().pow(1.0 / 5.255))).toInt()
        Log.d("prepre","${viewModel.altitude.value}")

    }

    private fun altitudeLaunch() {
        lifecycleScope.launch {
            while (true) {
                delay(1000)
                calAltitude()
            }
        }
    }
    override fun onSensorChanged(event: SensorEvent) {
        Log.d("pressure" ,"${event.values[0]}")
        viewModel.pressure.value = event.values[0]

    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    override fun onDetach() {
        super.onDetach()
    }
}