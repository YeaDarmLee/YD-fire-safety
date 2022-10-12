package com.directionfinding.main

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
import com.directionfinding.R
import com.directionfinding.ui.components.CommonButton
import com.directionfinding.ui.components.CommonText
import com.directionfinding.ui.components.DFAppBar
import com.directionfinding.ui.components.NumberOutLineTextField


@Composable
fun MainUI(titleName: String, labelText: String, placeHolderText: String, settingAlignAction:  () -> Unit) {
    Scaffold() {
        Column {
            DFAppBar(name = titleName)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SimpleTextField(labelText = labelText, placeHolderText = placeHolderText)
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp).height(250.dp),
            ) {
                val floorTextmodifier = Modifier.width(50.dp)
                val textFieldmodifier = Modifier
                    .height(40.dp)
                    .width(60.dp)
                val textFieldLayoutWidth = 70
                Row(horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),) {

                    CommonText(text = "  ", modifier = floorTextmodifier)
                    CommonText(text = stringResource(id = R.string.floor_number), textFieldmodifier.width(textFieldLayoutWidth.dp))
                    CommonText(text = stringResource(id = R.string.floor_height), textFieldmodifier.width(textFieldLayoutWidth.dp))
                }
                Row(horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),) {
                    CommonText(text = stringResource(id = R.string.ground_floor), modifier = floorTextmodifier)
                    Row(modifier = Modifier.width(textFieldLayoutWidth.dp),
                        verticalAlignment = Alignment.CenterVertically,) {
                        NumberOutLineTextField(labelText = "a", placeHolderText = "1", modifier = textFieldmodifier)
                        CommonText(text = stringResource(id = R.string.floor), modifier = Modifier.padding(start = 2.dp))

                    }
                    Row(modifier = Modifier.width(textFieldLayoutWidth.dp),
                        verticalAlignment = Alignment.CenterVertically,) {
                        NumberOutLineTextField(labelText = "", placeHolderText = "", modifier = textFieldmodifier)
                        CommonText(text = stringResource(id = R.string.meter), modifier = Modifier.padding(start = 2.dp))
                    }
                }

                Row(horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),) {
                    CommonText(text = stringResource(id = R.string.middle_floor), modifier = floorTextmodifier)
                    Row(modifier = Modifier.width(textFieldLayoutWidth.dp),
                        verticalAlignment = Alignment.CenterVertically,) {
                        NumberOutLineTextField(labelText = "", placeHolderText = "", modifier = textFieldmodifier)
                        CommonText(text = stringResource(id = R.string.floor), modifier = Modifier.padding(start = 2.dp))

                    }
                    Row(modifier = Modifier.width(textFieldLayoutWidth.dp),
                        verticalAlignment = Alignment.CenterVertically,) {
                        NumberOutLineTextField(labelText = "", placeHolderText = "", modifier = textFieldmodifier)
                        CommonText(text = stringResource(id = R.string.meter), modifier = Modifier.padding(start = 2.dp))
                    }
                }

                Row(horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),) {
                    CommonText(text = stringResource(id = R.string.basement_floor), modifier = floorTextmodifier)
                    Row(modifier = Modifier.width(textFieldLayoutWidth.dp),
                        verticalAlignment = Alignment.CenterVertically,) {
                        NumberOutLineTextField(labelText = "", placeHolderText = "", modifier = textFieldmodifier)
                        CommonText(text = stringResource(id = R.string.floor), modifier = Modifier.padding(start = 2.dp))

                    }
                    Row(modifier = Modifier.width(textFieldLayoutWidth.dp),
                        verticalAlignment = Alignment.CenterVertically,) {
                        NumberOutLineTextField(labelText = "", placeHolderText = "", modifier = textFieldmodifier)
                        CommonText(text = stringResource(id = R.string.meter), modifier = Modifier.padding(start = 2.dp))
                    }
                }
            }

            Column(
                modifier = Modifier.fillMaxSize().padding(bottom = 30.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,
            ) {
                CommonButton(text = stringResource(id = R.string.setting_align_floor), onClickAction = settingAlignAction)
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