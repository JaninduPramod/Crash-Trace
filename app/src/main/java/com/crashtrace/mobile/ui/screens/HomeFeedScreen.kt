package com.crashtrace.mobile.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import com.crashtrace.mobile.ui.components.AppBarMain

@Composable
fun HomeFeedScreen(navController: NavHostController) {

    var loadProfile by remember { mutableStateOf(false) }

    if (loadProfile) {
        navController.navigate("profile")
        loadProfile = false
    }

    Column {
        AppBarMain(
            title = "Home",
            BackButton = false,
            onProfileClick = { isProfile ->
                if (isProfile) loadProfile = true
            }
        )
        Text("Home Feed")
    }
}
