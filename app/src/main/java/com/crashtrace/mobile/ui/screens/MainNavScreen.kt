package com.crashtrace.mobile.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.crashtrace.mobile.ui.components.NavBar


@Composable
fun MainNavScreen(navController: NavController, selectedIndex: Int = 0) {
    var currentIndex by remember { mutableStateOf(selectedIndex) }

    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.weight(1f)) {
            when (currentIndex) {

                0 -> HomeFeedScreen(navController as NavHostController)

                1 -> NewsFeedScreen(navController as NavHostController)
                2 -> SearchReportScreen(navController as NavHostController)
                3 -> NewsDraftScreen(navController as NavHostController)
            }
        }
        NavBar(selectedIndex = currentIndex, onItemSelected = { currentIndex = it })
    }
}
