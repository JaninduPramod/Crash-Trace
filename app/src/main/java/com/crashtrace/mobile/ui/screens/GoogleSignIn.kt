package com.crashtrace.mobile.ui.screens

import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.api.ApiException
import java.util.Properties
import java.io.FileInputStream

@Composable
fun GoogleSignInScreen() {
    val context = LocalContext.current
    val activity = context as Activity


    var userName by remember { mutableStateOf<String?>(null) }
    var userEmail by remember { mutableStateOf<String?>(null) }

    // Load Web Client ID from .env file
    val properties = Properties().apply {
        load(FileInputStream("../../../../.env"))
    }
    val webClientId = properties.getProperty("GOOGLE_WEB_CLIENT_ID")

    // Configure Google Sign-In
    val googleSignInClient = remember {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken(webClientId) // Loaded from .env file
            .build()
        GoogleSignIn.getClient(activity, gso)
    }

    // Google Sign-In launcher
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            userName = account.displayName
            userEmail = account.email
            println("SignIn details:"+account.id)
        } catch (e: ApiException) {
            userName = null
            userEmail = "Sign-in failed: ${e.statusCode}"
            println("GoogleSignIn"+ "Sign-in failed with status code: ${e.statusCode}"+ e)
            if (e.statusCode == 10) {
                println("GoogleSignIn"+"Possible misconfiguration: Check Web Client ID and SHA-1 in Firebase Console.")
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = {
            val signInIntent = googleSignInClient.signInIntent
            launcher.launch(signInIntent)
            Log.d("GoogleSignIn", "Google Sign-In intent launched")
        }) {
            Text("Sign in with Google")
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (userName != null && userEmail != null) {
            Text("Welcome, $userName!")
            Text("Email: $userEmail")
        }
    }
}
