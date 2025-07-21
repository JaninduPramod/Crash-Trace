package com.crashtrace.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.crashtrace.mobile.navigation.AppNavGraph


import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri

import android.os.Environment
import android.widget.Toast

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.compose.rememberAsyncImagePainter
import java.io.File




class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        setContent {

            val navController = rememberNavController()
            AppNavGraph(navController)
        }
    }
}
@Composable
fun CameraScreen() {
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (!success) {
            imageUri = null
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            launchCamera(context) { uri ->
                imageUri = uri
                cameraLauncher.launch(uri)
            }
        } else {
            Toast.makeText(context, "Camera permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = {
            when (PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) -> {
                    launchCamera(context) { uri ->
                        imageUri = uri
                        cameraLauncher.launch(uri)
                    }
                }
                else -> {
                    permissionLauncher.launch(Manifest.permission.CAMERA)
                }
            }
        }) {
            Text("Open Camera")
        }

        Spacer(modifier = Modifier.height(20.dp))

        imageUri?.let {
            Image(
                painter = rememberAsyncImagePainter(it),
                contentDescription = null,
                modifier = Modifier
                    .size(300.dp)
                    .clickable {},
                contentScale = ContentScale.Crop
            )
        }
    }
}

fun launchCamera(context: Context, onImageReady: (Uri) -> Unit) {
    val photoFile = File(
        context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
        "IMG_${System.currentTimeMillis()}.jpg"
    )
    val uri = FileProvider.getUriForFile(
        context,
        "${context.packageName}.provider",
        photoFile
    )
    onImageReady(uri)
}