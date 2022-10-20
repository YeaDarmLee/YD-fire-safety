package com.floortracking.ui.main

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.floortracking.R
import com.floortracking.ui.components.*

@Composable
fun MainUI(altitudeMeasurementAction:  () -> Unit, settingAlignAction:  () -> Unit, altitudeBtnEnabled: Boolean) {
    Scaffold() {
        Column {
            FTAppBar(name = stringResource(id = R.string.floor_info_modify))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val btnHeight = 100.dp
                CommonSpacerVertical(100.dp)
                CommonButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height = btnHeight)
                        .padding(horizontal = 20.dp),
                    text = stringResource(id = R.string.altitude_measurement),
                    onClickAction = altitudeMeasurementAction,
                    enabled = altitudeBtnEnabled
                )
                CommonSpacerVertical(10.dp)
                CommonButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(btnHeight)
                        .padding(horizontal = 20.dp),
                    text = stringResource(id = R.string.setting_align_floor),
                    onClickAction = settingAlignAction
                )
            }
        }
    }
}

@Composable
fun SimpleTextField(labelText: String, placeHolderText: String) {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    TextField(
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
    )
}

/*
*/

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}