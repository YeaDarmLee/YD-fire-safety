package com.floortracking.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.Dp
import androidx.fragment.app.Fragment
import com.floortracking.R
import com.floortracking.ui.theme.FloorTrackingTheme

class AppbarFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView: View = inflater.inflate(R.layout.fragment_appbar, container, false)

        rootView.findViewById<ComposeView>(R.id.toolbar_compose_view).apply {
            setContent {
                FloorTrackingTheme {
                    ToolbarWithTitle(name = "지붕뚫고 하이킥")
                }
            }
        }
        return rootView
    }
}

@Composable
fun ToolbarWithTitle(name: String) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = name) },
            )
        }
    ) {

    }
}