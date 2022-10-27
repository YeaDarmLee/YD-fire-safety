package com.directionfinding.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.commit
import com.directionfinding.R
import com.directionfinding.base.BaseFragment
import com.directionfinding.ui.components.OneButtonPopup
import com.directionfinding.ui.directionmap.DirectionmapFragment
import com.directionfinding.ui.theme.DirectionFindingTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment() {

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
                    DirectionFindingTheme() {

                    }
                    DirectionFindingTheme {
                        MainUI(
                            altitudeMeasurementAction = {
                                startDirectionFindingFragment()
                            },
                            settingAlignAction = {
                           //     throw RuntimeException("Test Crash") // Force a crash
                       //         startRegistrationFragment()
                            })
                    }
                }
                if (showDialog.value) {
                    OneButtonPopup(onDismiss = {})
                }
            }
        }
    }
    private fun startDirectionFindingFragment() {
        val fragment = DirectionmapFragment()
        val fragmentManager = requireActivity().supportFragmentManager
        fragmentManager.commit {
            addToBackStack(null)
            setReorderingAllowed(true)
            replace(R.id.nav_host_fragment, fragment, "registrationInfo")
        }
    }
}
/*
@Composable
fun showDialog() {
    OneButtonPopup {

    }
}*/