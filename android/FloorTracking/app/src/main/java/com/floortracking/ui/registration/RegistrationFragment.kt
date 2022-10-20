package com.floortracking.ui.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import com.floortracking.R
import com.floortracking.ui.components.OneButtonPopup
import com.floortracking.ui.floor.FloorFragment
import com.floortracking.ui.theme.FloorTrackingTheme
import com.floortracking.util.AppPreferences
import com.floortracking.util.MathUtils
import com.floortracking.viewmodel.FloorViewModel
import com.floortracking.viewmodel.MainViewModel
import com.floortracking.viewmodel.RegistrationViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegistrationFragment : Fragment() {

    @Inject
    lateinit var appPreferences: AppPreferences
    private val mainViewModel: MainViewModel by activityViewModels()

    private val viewModel: RegistrationViewModel by viewModels()
    private val showDialog = mutableStateOf(false)
    private val titleText = mutableStateOf("테스트합니다")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return ComposeView(inflater.context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            setContent {
                CompositionLocalProvider(

                ) {
                    FloorTrackingTheme {
                        RegistrationUI(titleName = getString(R.string.scene_fire_register),
                            labelText = getString(R.string.scene_fire_name),
                            placeHolderText = getString(R.string.scene_fire_name),
                            textFieldValue = mainViewModel.sceneFireName,
                            groundFloorValue = mainViewModel.groundFloor,
                            groundHeightValue = mainViewModel.groundHeight,
                            middleFloorValue = mainViewModel.middleFloor,
                            middleHeightValue = mainViewModel.middleHeight,
                            underGroundFloorValue = mainViewModel.underGroundFloor,
                            underGroundHeightValue = mainViewModel.underGroundHeight,
                            settingAlignAction = {
                             //   showDialog.value = true
                                saveSettings()
                            })
                    }
                }
                if (showDialog.value) {
                    OneButtonPopup(mainViewModel.sceneFireName.value?:"", onDismiss = {showDialog.value = false})
                }
            }
        }
    }

    private fun saveSettings() {
        appPreferences.alignAltitude = MathUtils.calAltitude(mainViewModel.seaLevel.value.toFloat(), mainViewModel.pressure.value)
        appPreferences.sceneFireName = mainViewModel.sceneFireName.value ?: ""
        appPreferences.groundFloor =  MathUtils.parseInt(mainViewModel.groundFloor.value)
        appPreferences.groundHeight =  MathUtils.parseFloat(mainViewModel.groundHeight.value)
        appPreferences.middleFloor =  MathUtils.parseInt(mainViewModel.middleFloor.value)
        appPreferences.middleHeight =  MathUtils.parseFloat(mainViewModel.middleHeight.value)
        appPreferences.underGroundFloor =  MathUtils.parseInt(mainViewModel.underGroundFloor.value)
        appPreferences.underGroundHeight =  MathUtils.parseFloat(mainViewModel.underGroundHeight.value)
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
}
/*
@Composable
fun showDialog() {
    OneButtonPopup {

    }
}*/