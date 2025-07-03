package com.crashtrace.mobile.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.crashtrace.mobile.ui.components.NavBar


@Composable
fun MainNavScreen() {
    var selectedIndex by remember { mutableStateOf(0) }

    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.weight(1f)) {
            when (selectedIndex) {

                0 -> NewsFeedScreen()
                1 -> NewsGalleryScreen()
                2 -> SearchReportScreen()
                3 -> NewsDraftScreen()
            }
        }
        NavBar(selectedIndex = selectedIndex, onItemSelected = { selectedIndex = it })
    }
}
