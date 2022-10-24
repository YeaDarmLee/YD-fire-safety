package com.floortracking.ui.main

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
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.lifecycle.viewModelScope
import com.floortracking.R
import com.floortracking.api.ApiResponse
import com.floortracking.ui.components.OneButtonPopup
import com.floortracking.ui.floor.FloorFragment
import com.floortracking.ui.registration.RegistrationFragment
import com.floortracking.ui.theme.FloorTrackingTheme
import com.floortracking.util.AppPreferences
import com.floortracking.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment(), SensorEventListener {

    @Inject
    lateinit var appPreferences: AppPreferences

    private val showDialog = mutableStateOf(false)

    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initSettings()
        requestSeaLevel()
        initPressure()
        return ComposeView(inflater.context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            setContent {
                CompositionLocalProvider(

                ) {
                    FloorTrackingTheme {
                        MainUI(
                            altitudeMeasurementAction = {
                                startFloorFragment()
                            },
                            settingAlignAction = {
                                startRegistrationFragment()
                            }, mainViewModel.altitudeEnabled.value)
                    }
                }
                if (showDialog.value) {
                    OneButtonPopup("",onDismiss = {})
                }
            }
        }
    }


    override fun onResume() {
        mainViewModel.altitudeEnabled.value = appPreferences.isValidData()
        super.onResume()
    }
    private fun startRegistrationFragment() {
        val fragment = RegistrationFragment()
        val fragmentManager = requireActivity().supportFragmentManager
        fragmentManager.commit {
            addToBackStack(null)
            setReorderingAllowed(true)
            replace(R.id.nav_host_fragment, fragment, "registrationInfo")
        }
    }
    private fun startFloorFragment() {
        val fragment = FloorFragment()
        val fragmentManager = requireActivity().supportFragmentManager
        fragmentManager.commit {
            addToBackStack(null)
            setReorderingAllowed(true)
            replace(R.id.nav_host_fragment, fragment, "floorInfo")
        }
    }
    private fun initSettings() {
        mainViewModel.sceneFireName.value = appPreferences.sceneFireName
        mainViewModel.groundFloor.value = appPreferences.groundFloor.toString()
        mainViewModel.groundHeight.value = appPreferences.groundHeight.toString()
        mainViewModel.middleFloor.value = appPreferences.middleFloor.toString()
        mainViewModel.middleHeight.value = appPreferences.middleHeight.toString()
        mainViewModel.underGroundFloor.value = appPreferences.underGroundFloor.toString()
        mainViewModel.underGroundHeight.value = appPreferences.underGroundHeight.toString()
    }
    private fun requestSeaLevel() {
        mainViewModel.viewModelScope.launch(Dispatchers.IO) {
            mainViewModel.requestSeaLevel(mainViewModel.location.value?.latitude?.toFloat()?:37.566f, mainViewModel.location.value?.longitude?.toFloat()?:126.97f).collect {
                if (it is ApiResponse.Success) {
                    mainViewModel.seaLevel.value = it.value
                    Log.d("test" , "${it.value}")
                }
            }
        }
    }

    private fun initPressure() {
        val sensorManager = requireContext().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)?.run {
            sensorManager.registerListener(this@MainFragment, this, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onSensorChanged(event: SensorEvent) {
        Log.d("pressure", "${event.values[0]}")
        mainViewModel.pressure.value = event.values[0]
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

}