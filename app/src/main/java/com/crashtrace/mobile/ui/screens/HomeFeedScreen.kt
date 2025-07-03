package com.crashtrace.mobile.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import com.crashtrace.mobile.ui.components.AppBarMain

@Composable
fun HomeFeedScreen() {
    Column {
        AppBarMain(title = "Home", backButton = false)
        Text("Home Feed")
    }
}
