package com.directionfinding.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import com.directionfinding.R

@Composable
fun DFCircle(sizeRate: Float = 1f, color: Color = Color.Black, image: Painter? = null) {
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(color = color, shape = CircleShape)
            .layout() { measurable, constraints ->
                // Measure the composable
                val placeable = measurable.measure(constraints)

                //get the current max dimension to assign width=height
                val currentHeight = placeable.height
                val currentWidth = placeable.width
                val newDiameter = maxOf((currentHeight * sizeRate).toInt(), (currentWidth* sizeRate).toInt())

                //assign the dimension and the center position
                layout(newDiameter, newDiameter) {
                    // Where the composable gets placed
                    placeable.placeRelative(
                        (newDiameter - currentWidth) / 2,
                        (newDiameter - currentHeight) / 2
                    )
                }
            }) {
        image?.run {
            Image(this, "")
        }

    }
}