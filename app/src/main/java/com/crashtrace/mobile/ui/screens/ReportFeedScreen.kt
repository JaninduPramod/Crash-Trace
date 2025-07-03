package com.crashtrace.mobile.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import com.crashtrace.mobile.ui.components.AppBarMain

@Composable
fun ReportFeedScreen() {
    Column {
        AppBarMain(title = "Report", backButton = false)
        Text("Report Feed")
    }
}
