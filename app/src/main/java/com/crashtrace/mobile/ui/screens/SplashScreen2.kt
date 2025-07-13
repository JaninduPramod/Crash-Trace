package com.crashtrace.mobile.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.crashtrace.mobile.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    // Use rememberSaveable to show splash only once per process (not persistent)
    var splashShown by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (!splashShown) {
            delay(2000)
            splashShown = true
            navController.navigate("first") {
                popUpTo("splash") { inclusive = true }
            }
        }
    }
    if (!splashShown) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.red))
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.app_logo),
                    contentDescription = "App Logo",
                    modifier = Modifier.size(200.dp)
                )
            }
        }
    }
}
