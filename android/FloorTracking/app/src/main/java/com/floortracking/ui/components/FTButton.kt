package com.floortracking.ui.components

import android.service.autofill.OnClickAction
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun CommonButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClickAction: () -> Unit = {/* Do something! */ }
) {
    Button(modifier = modifier, onClick = onClickAction, enabled = enabled) {
        Text(text = text, color = Color.White,)
    }
}