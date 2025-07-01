package com.crashtrace.mobile.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.crashtrace.mobile.ui.screens.FirstScreen
import com.crashtrace.mobile.ui.screens.SecondScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "first") {
        composable("first") { FirstScreen(navController) }
        composable("second") { SecondScreen(navController) }

    }
}