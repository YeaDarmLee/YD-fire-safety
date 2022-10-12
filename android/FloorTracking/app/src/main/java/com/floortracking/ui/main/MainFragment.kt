package com.floortracking.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.floortracking.R
import com.floortracking.ui.components.OneButtonPopup
import com.floortracking.ui.floor.FloorFragment
import com.floortracking.ui.registration.RegistrationFragment
import com.floortracking.ui.theme.FloorTrackingTheme

class MainFragment : Fragment() {

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
                        MainUI(
                            altitudeMeasurementAction = {
                                startFloorFragment()
                            },
                            settingAlignAction = {
                                //showDialog.value = true
                                //titleText.value = "호잇 둘리는 초능력 내친구"
                                startRegistrationFragment()
                            })
                    }
                }
                if (showDialog.value) {
                    OneButtonPopup(onDismiss = {})
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
}
/*
@Composable
fun showDialog() {
    OneButtonPopup {

    }
}*/