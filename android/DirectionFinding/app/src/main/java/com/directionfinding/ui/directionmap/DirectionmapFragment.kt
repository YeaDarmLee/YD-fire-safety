package com.directionfinding.ui.directionmap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.ComposeView
import com.directionfinding.base.BaseFragment
import com.directionfinding.ui.theme.DirectionFindingTheme

class DirectionmapFragment  : BaseFragment(){

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
                        DirectionmapUI()
                    }
                }
            }
        }
    }
}