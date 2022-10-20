package com.floortracking.ui.registration

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import com.floortracking.R
import com.floortracking.ui.components.*

@Composable
fun RegistrationUI(titleName: String, labelText: String, placeHolderText: String,
                   textFieldValue: MutableLiveData<String>,
                   groundFloorValue: MutableLiveData<String>,
                   groundHeightValue: MutableLiveData<String>,
                   middleFloorValue: MutableLiveData<String>,
                   middleHeightValue: MutableLiveData<String>,
                   underGroundFloorValue: MutableLiveData<String>,
                   underGroundHeightValue: MutableLiveData<String>,
                   settingAlignAction:  () -> Unit) {
    Scaffold() {
        Column {
            FTAppBar(name = titleName)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SimpleTextField(textFieldValue = textFieldValue, labelText = labelText, placeHolderText = placeHolderText)
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
                        FloorInputTextField(textFieldValue = groundFloorValue, labelText = "a", placeHolderText = "1", modifier = textFieldmodifier)
                        CommonText(text = stringResource(id = R.string.floor), modifier = Modifier.padding(start = 2.dp))

                    }
                    Row(modifier = Modifier.width(textFieldLayoutWidth.dp),
                        verticalAlignment = Alignment.CenterVertically,) {
                        FloorHeightInputTextField(textFieldValue = groundHeightValue, labelText = "", placeHolderText = "", modifier = textFieldmodifier)
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
                        FloorInputTextField(textFieldValue = middleFloorValue, labelText = "", placeHolderText = "", modifier = textFieldmodifier)
                        CommonText(text = stringResource(id = R.string.floor), modifier = Modifier.padding(start = 2.dp))

                    }
                    Row(modifier = Modifier.width(textFieldLayoutWidth.dp),
                        verticalAlignment = Alignment.CenterVertically,) {
                        FloorHeightInputTextField(textFieldValue = middleHeightValue, labelText = "", placeHolderText = "", modifier = textFieldmodifier)
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
                        FloorInputTextField(textFieldValue = underGroundFloorValue, labelText = "", placeHolderText = "", modifier = textFieldmodifier)
                        CommonText(text = stringResource(id = R.string.floor), modifier = Modifier.padding(start = 2.dp))

                    }
                    Row(modifier = Modifier.width(textFieldLayoutWidth.dp),
                        verticalAlignment = Alignment.CenterVertically,) {
                        FloorHeightInputTextField(textFieldValue = underGroundHeightValue, labelText = "", placeHolderText = "", modifier = textFieldmodifier)
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