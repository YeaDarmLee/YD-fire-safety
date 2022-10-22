package com.floortracking.ui.components

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun FTAppBar(name: String) {
    TopAppBar(
        elevation = 4.dp,
        title = { Text(text = name) },
    )
}

@Composable
fun FTAppBarBack(name: String, onClick: () -> Unit) {
    TopAppBar(
        elevation = 4.dp,
        title = { Text(text = name) },
        navigationIcon = {
            IconButton(onClick = onClick) {
                Icon(Icons.Filled.ArrowBack, null)
            }
        }
    )
}