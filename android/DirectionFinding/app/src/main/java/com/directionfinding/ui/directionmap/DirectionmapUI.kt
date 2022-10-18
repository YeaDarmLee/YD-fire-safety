package com.directionfinding.ui.directionmap

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.directionfinding.R
import com.directionfinding.components.DFCircle

@Composable
fun DirectionmapUI() {
    DFCircle(color = Color(0xFF236AB8))
    DFCircle(sizeRate = 0.66f, color = Color(0xFF3693CC))
    DFCircle(sizeRate = 0.33f, color = Color(0xFFA5C1E1), image = painterResource(id = R.drawable.img_emoji_people1))

}