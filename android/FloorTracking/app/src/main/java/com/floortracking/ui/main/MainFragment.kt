package com.floortracking.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.floortracking.R
import com.floortracking.ui.components.FTAppBar
import com.floortracking.ui.theme.FloorTrackingTheme

class MainFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(inflater.context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            setContent {
                CompositionLocalProvider(

                ) {
                    FloorTrackingTheme {
                        MainUI(titleName = getString(R.string.scene_fire_register),
                        labelText = getString(R.string.scene_fire_name),
                        placeHolderText = getString(R.string.scene_fire_name))
                     //   Greeting(name = "호잇")
                     //   FTAppBar(name = getString(R.string.scene_fire_register))
                    }
                }
            }
        }

    }
}