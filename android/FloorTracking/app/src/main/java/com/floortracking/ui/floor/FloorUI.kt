package com.floortracking.ui.floor

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.floortracking.R
import com.floortracking.ui.components.CommonButton
import com.floortracking.ui.components.CommonText
import com.floortracking.ui.components.FTAppBarBack
import com.floortracking.ui.components.NumberOutLineTextField

@Composable
fun FloorUI(titleName: String, floorText: String, hpaText: String, meterText: String, settingAlignAction:  () -> Unit) {
    Scaffold() {
        Column {
            FTAppBarBack(name = titleName)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CommonText(text = stringResource(id = R.string.my_floor_position), fontSize = 30.sp)
                CommonText(text = floorText, modifier = Modifier.padding(top = 20.dp), color = Color.Red, fontSize = 28.sp)

                Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 60.dp),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    CommonText(text = stringResource(id = R.string.sea_level_pressure), fontSize = 24.sp, fontWeight = FontWeight(FontWeight.Bold.weight))
                    CommonText(text = hpaText, modifier = Modifier.padding(start = 2.dp), color = Color.Red, fontSize = 20.sp, fontWeight = FontWeight(FontWeight.Bold.weight))
                    CommonText(text = stringResource(id = R.string.hectopascal), modifier = Modifier.padding(start = 2.dp), color = Color.Black, fontSize = 20.sp)
                }
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    CommonText(text = stringResource(id = R.string.reference_elevation), fontSize = 24.sp)
                    CommonText(text = meterText, modifier = Modifier.padding(start = 2.dp), color = Color.Red, fontSize = 20.sp, fontWeight = FontWeight(FontWeight.Bold.weight))
                    CommonText(text = stringResource(id = R.string.meter), modifier = Modifier.padding(start = 2.dp), color = Color.Black, fontSize = 20.sp)
                }

            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 30.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,
            ) {
                CommonButton(text = stringResource(id = R.string.refresh), onClickAction = settingAlignAction)
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