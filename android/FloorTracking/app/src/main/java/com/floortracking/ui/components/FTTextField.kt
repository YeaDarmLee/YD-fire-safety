package com.floortracking.ui.components

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import com.floortracking.util.Utils

@Composable
fun FloorInputTextField(textFieldValue: MutableLiveData<String>, labelText: String, placeHolderText: String, modifier: Modifier = Modifier) {
    var text by remember { mutableStateOf(TextFieldValue(textFieldValue.value?:"")) }
    BasicTextField(
        value = text,
        onValueChange = { newText ->
            Log.d("newText", "$newText")
            Utils.isValidFloorValue(newText.text, text.text)?.run {
                text = TextFieldValue(this)
                text.text.also { textFieldValue.value = it }
                return@BasicTextField
            }
            text = newText
            textFieldValue.value = text.text
        },
        textStyle = TextStyle.Default.copy(textAlign = TextAlign.Start),
        singleLine = true,
        keyboardActions = KeyboardActions(onDone = {
            Log.d("keyboardAction", "onDone")
        }),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        modifier = modifier
            .border(1.dp, Color.LightGray, RoundedCornerShape(0))
            .padding(vertical = 10.dp)//background(color = Color.Black, shape = RectangleShape)
    )
}

@Composable
fun FloorHeightInputTextField(textFieldValue: MutableLiveData<String>, labelText: String, placeHolderText: String, modifier: Modifier = Modifier) {
    var text by remember { mutableStateOf(TextFieldValue(textFieldValue.value?:"")) }
    BasicTextField(
        value = text,
        onValueChange = { newText ->
            Log.d("newText", "$newText")
            Utils.isValidValue(newText.text, text.text)?.run {
                text = TextFieldValue(this)
                text.text.also { textFieldValue.value = it }
                return@BasicTextField
            }
            text = newText
            textFieldValue.value = text.text
        },
        textStyle = TextStyle.Default.copy(textAlign = TextAlign.Start),
        singleLine = true,
        keyboardActions = KeyboardActions(onDone = {
            Log.d("keyboardAction", "onDone")
        }),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        modifier = modifier
            .border(1.dp, Color.LightGray, RoundedCornerShape(0))
            .padding(vertical = 10.dp)//background(color = Color.Black, shape = RectangleShape)
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

@Composable
fun SimpleTextField(textFieldValue: MutableLiveData<String>, labelText: String, placeHolderText: String) {
    var text by remember { mutableStateOf(TextFieldValue(textFieldValue.value?:"")) }
    TextField(
        value = text,
        onValueChange = { newText ->
            text = newText
            textFieldValue.value = text.text
        },
        label = { Text(text = labelText) },
        placeholder = { Text(text = placeHolderText) },
        singleLine = true,
        keyboardActions = KeyboardActions(onDone = {
            Log.d("keyboardAction", "onDone")
        }),
    )
}