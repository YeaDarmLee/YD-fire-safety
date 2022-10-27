package com.floortracking.ui.floor

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.floortracking.R
import com.floortracking.ui.base.BaseFragment
import com.floortracking.ui.theme.FloorTrackingTheme
import com.floortracking.util.AppPreferences
import com.floortracking.util.MathUtils
import com.floortracking.viewmodel.FloorViewModel
import com.floortracking.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt

@AndroidEntryPoint
class FloorFragment : BaseFragment() {

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

        viewModel.totalGroundFloor.value = appPreferences.groundFloor + appPreferences.middleFloor

    }

    private fun altitudeLaunch() {
        lifecycleScope.launch {
            while (true) {
                delay(1000)
                val altitude = MathUtils.calAltitude(
                    mainViewModel.seaLevel.value.toFloat(),
                    mainViewModel.pressure.value
                )
                viewModel.altitude.value = altitude
                Log.d(
                    "altitude",
                    "${viewModel.altitude.value}, ${viewModel.altitude.value.toInt()}"
                )
                val diffHeight = altitude - (viewModel.alignAltitude.value ?: 0f)

                val isUnderGround = ((diffHeight < 0 && ((viewModel.underGroundHeight.value
                    ?: 0f) > 0f) && ((viewModel.underGroundFloor.value ?: 0) > 0)))

                if (isUnderGround) {
                    viewModel.underGroundHeight.value?.run {
                        val floor = (diffHeight / this).roundToInt()
                        if (floor < 0) {
                            viewModel.currentFloor.value = floor
                        } else {
                            viewModel.currentFloor.value = 1 + floor
                        }
                    }

                } else {
                    if (isValidMiddle()) {
                        val middleTotalHeight =
                            (viewModel.middleHeight.value ?: 0f) * (viewModel.middleFloor.value
                                ?: 0)
                        if (diffHeight > middleTotalHeight) {
                            viewModel.groundHeight.value?.run {
                                viewModel.currentFloor.value = (viewModel.middleFloor.value
                                    ?: 0) + (((diffHeight - middleTotalHeight) / this).roundToInt())
                            }
                        } else {

                            if (middleTotalHeight > 0) {
                                viewModel.middleHeight.value?.run {
                                    viewModel.currentFloor.value =
                                        1 + (diffHeight / this).roundToInt()
                                }
                            } else {
                                viewModel.currentFloor.value = 1
                            }
                        }

                    } else {
                        viewModel.groundHeight.value?.run {
                            viewModel.currentFloor.value = 1 + ((diffHeight / this).roundToInt())
                        }
                    }
                }
                if (viewModel.currentFloor.value > (viewModel.totalGroundFloor.value ?: 0)) {
                    viewModel.currentFloor.value = viewModel.totalGroundFloor.value ?: 0
                }

            }
        }
    }

    private fun isValidMiddle(): Boolean {
        if ((viewModel.middleHeight.value ?: 0f) < 1f || (viewModel.middleFloor.value ?: 0) < 1) {
            return false
        }
        return true
    }
}