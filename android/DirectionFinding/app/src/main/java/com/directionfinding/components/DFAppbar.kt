package com.directionfinding.ui.components

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun DFAppBar(name: String) {
    TopAppBar(
        elevation = 4.dp,
        title = { Text(text = name) },
    )
}

@Composable
fun DFAppBarBack(name: String) {
    TopAppBar(
        elevation = 4.dp,
        title = { Text(text = name) },
        navigationIcon = {
            IconButton(onClick = {/* Do Something*/ }) {
                Icon(Icons.Filled.ArrowBack, null)
            }
        }
    )
}