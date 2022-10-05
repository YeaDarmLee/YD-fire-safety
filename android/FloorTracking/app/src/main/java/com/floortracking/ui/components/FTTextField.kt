package com.floortracking.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NumberOutLineTextField(labelText: String, placeHolderText: String, modifier: Modifier = Modifier) {
    var text by remember { mutableStateOf("") }
    BasicTextField(
        value = text,
        onValueChange = { newText ->
            text = newText
        },
        textStyle = TextStyle.Default.copy(textAlign = TextAlign.End),
        singleLine = true,
        keyboardActions = KeyboardActions(onDone = {
            Log.d("keyboardAction", "onDone")
        }),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = modifier.border(1.dp, Color.LightGray, RoundedCornerShape(0)).padding(vertical = 10.dp)//background(color = Color.Black, shape = RectangleShape)
    )
}

@Composable
fun OutLineTextField(labelText: String, placeHolderText: String, modifier: Modifier = Modifier) {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    OutlinedTextField(
        value = text,
        onValueChange = { newText ->
            text = newText
        },
        label = { Text(text = labelText) },
        placeholder = { Text(text = placeHolderText) },
        singleLine = true,
        keyboardActions = KeyboardActions(onDone = {
            Log.d("keyboardAction", "onDone")
        }),
        modifier = modifier
    )
}