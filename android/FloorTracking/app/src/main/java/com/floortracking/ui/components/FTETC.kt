package com.floortracking.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

@Composable
fun CommonSpacerHorizontal(width: Dp) {
    Spacer(modifier = Modifier.width(width))
}

@Composable
fun CommonSpacerVertical(height: Dp) {
    Spacer(modifier = Modifier.height(height))
}