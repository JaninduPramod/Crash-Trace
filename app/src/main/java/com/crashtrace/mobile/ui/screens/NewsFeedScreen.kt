package com.crashtrace.mobile.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import com.crashtrace.mobile.ui.components.AppBarMain

@Composable
fun NewsFeedScreen() {
    Column {
        AppBarMain(title = "News", backButton = false)
        Text("News Feed")
    }
}
