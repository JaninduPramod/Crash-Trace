package com.crashtrace.mobile.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.crashtrace.mobile.ui.screens.OnBoardScreen1
import com.crashtrace.mobile.ui.screens.SignUpScreen
import com.crashtrace.mobile.ui.screens.SigningInScreen

import com.crashtrace.mobile.ui.screens.SplashScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") { SigningInScreen(navController) }
        composable("first") {
            OnBoardScreen1(
                navController = navController,
                onSkip = { /* TODO: handle skip navigation */ },
                onNext = { /* TODO: handle next navigation */ }
            )
        }
//        composable("second") { SignUpScreen(navController) }


    }
}