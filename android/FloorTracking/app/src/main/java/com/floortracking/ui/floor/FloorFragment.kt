package com.floortracking.ui.floor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.ComposeView
import com.floortracking.R
import com.floortracking.ui.base.BaseFragment
import com.floortracking.ui.components.OneButtonPopup
import com.floortracking.ui.main.MainUI
import com.floortracking.ui.theme.FloorTrackingTheme

class FloorFragment: BaseFragment() {
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
                   // LocalBackPressedDispatcher provides requireActivity().onBackPressedDispatcher
                ) {
                    FloorTrackingTheme {
                        FloorUI(titleName = getString(R.string.floor_info_modify),
                            floorText = "1F",
                            hpaText = "970",
                            meterText = "0",
                            settingAlignAction = {
                                //showDialog.value = true
                                //titleText.value = "호잇 둘리는 초능력 내친구"

                            })
                    }
                }
            }
        }
    }
}