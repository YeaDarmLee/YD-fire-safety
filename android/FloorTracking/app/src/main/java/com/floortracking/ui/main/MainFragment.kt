package com.floortracking.ui.main

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
import com.floortracking.ui.components.OneButtonPopup
import com.floortracking.ui.floor.FloorFragment
import com.floortracking.ui.registration.RegistrationFragment
import com.floortracking.ui.theme.FloorTrackingTheme
import com.floortracking.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val showDialog = mutableStateOf(false)
    private val titleText = mutableStateOf("테스트합니다")

    private val mainViewModel: MainViewModel by activityViewModels()

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
                        MainUI(
                            altitudeMeasurementAction = {
                                startFloorFragment()
                            },
                            settingAlignAction = {
                                requestSeaLevel()
                                startRegistrationFragment()
                            })
                    }
                }
                if (showDialog.value) {
                    OneButtonPopup("",onDismiss = {})
                }
            }
        }
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

    private fun requestSeaLevel() {
        mainViewModel.viewModelScope.launch(Dispatchers.IO) {
            mainViewModel.requestSeaLevel(44.34f, 10.99f).collect {
                Log.d("test" , "$it")
            }
        }
    }


}
/*
@Composable
fun showDialog() {
    OneButtonPopup {

    }
}*/