package com.floortracking.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FTText() {
}

@Composable
fun CommonText(text: String, modifier: Modifier = Modifier, topPadding: Int = 0) {
    Text(text = text,
    modifier = modifier)
}