package com.directionfinding.ui.directionmap

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import com.directionfinding.R
import com.directionfinding.components.DFCircle
import com.directionfinding.util.MathUtils

@Composable
fun DirectionmapUI(distance: Int = 1000) {
    DFCircle(color = Color(0xFF236AB8))
    DFCircle(sizeRate = 0.60f, color = Color(0xFF3693CC))
    DFCircle(sizeRate = 0.20f, color = Color(0xFFA5C1E1), image = painterResource(id = R.drawable.img_emoji_people1))
    drawRescuer(distance)

}

@Composable
fun drawRescuer(distance: Int) {
    val image = painterResource(id = R.drawable.img_emoji_people_red)
    image.run {
        Image(this, "", modifier = Modifier.layout() {measurable, constraints ->
            // Measure the composable
            val placeable = measurable.measure(constraints)
            Log.d("placeable", "${placeable.width}, ${placeable.height}")
            //get the current max dimension to assign width=height
            val currentHeight = placeable.height
            val currentWidth = placeable.width
            val point = MathUtils.calRescuerPosition(currentWidth, currentHeight, distance)

            //assign the dimension and the center position
            layout(currentWidth, currentHeight) {
                // Where the composable gets placed
                placeable.place(point.x, point.y)
            }

        }, contentScale = ContentScale.Inside)
    }
}