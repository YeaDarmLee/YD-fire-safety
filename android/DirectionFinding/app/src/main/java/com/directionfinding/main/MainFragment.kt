package com.directionfinding.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.directionfinding.R
import com.directionfinding.main.MainUI
import com.directionfinding.ui.components.OneButtonPopup
import com.directionfinding.ui.theme.DirectionFindingTheme


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
                    DirectionFindingTheme() {

                    }
                    DirectionFindingTheme {
                        MainUI(titleName = getString(R.string.scene_fire_register),
                            labelText = getString(R.string.scene_fire_name),
                            placeHolderText = getString(R.string.scene_fire_name),
                            settingAlignAction = {
                                //showDialog.value = true
                                //titleText.value = "호잇 둘리는 초능력 내친구"
                             //   startFloorFragment()

                            })
                    }
                }
                if (showDialog.value) {
                    OneButtonPopup(onDismiss = {})
                }
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