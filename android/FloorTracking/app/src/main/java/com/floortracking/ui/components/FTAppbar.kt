package com.floortracking.ui.components

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun FTAppBar(name: String) {
    TopAppBar(
        elevation = 4.dp,
        title = { Text(text = name) },
    )
}