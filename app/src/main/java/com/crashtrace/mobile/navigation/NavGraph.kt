package com.crashtrace.mobile.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.crashtrace.mobile.ui.screens.OnBoardScreen1
import com.crashtrace.mobile.ui.screens.OnBoardScreen2
import com.crashtrace.mobile.ui.screens.OnBoardScreen3
import com.crashtrace.mobile.ui.screens.OtpCodeScreen
import com.crashtrace.mobile.ui.screens.ProfileScreen
import com.crashtrace.mobile.ui.screens.ResetScreen
import com.crashtrace.mobile.ui.screens.SignUpScreen
import com.crashtrace.mobile.ui.screens.SigningInScreen
import com.crashtrace.mobile.ui.screens.VerificationScreen
import com.crashtrace.mobile.ui.screens.MainNavScreen
import com.crashtrace.mobile.ui.screens.NewsGalleryScreen
import com.crashtrace.mobile.ui.screens.NewsInfoScreen


@Composable
fun AppNavGraph(navController: NavHostController) {

    NavHost(navController = navController, startDestination = "signin") {
//        composable("splash") {SplashScreen(navController) }

        composable("signup") { SignUpScreen(navController) }
        composable("signin") { SigningInScreen(navController) }
        composable("reset") { VerificationScreen(navController) }
        composable("otpVerify") { OtpCodeScreen(navController) }
        composable("newPassword") { ResetScreen(navController) }
        composable("profile") { ProfileScreen(navController) }
        composable("home") { MainNavScreen(navController) }
        composable("card") { NewsInfoScreen(navController) }
        composable("gallery") { NewsGalleryScreen(navController) }


        composable("first") {
            OnBoardScreen1(
                navController = navController,
                onSkip = { /* TODO: handle skip navigation */ },
                onNext = { /* TODO: handle next navigation */ }
            )
        }
        composable("second") {
            OnBoardScreen2(
                navController = navController,
                onSkip = { /* TODO: handle skip navigation */ },
                onNext = { /* TODO: handle next navigation */ }
            )
        }

        composable("third") {
            OnBoardScreen3(
                navController = navController,
                onSkip = { /* TODO: handle skip navigation */ },
                onNext = { /* TODO: handle next navigation */ }
            )
        }

    }
}
