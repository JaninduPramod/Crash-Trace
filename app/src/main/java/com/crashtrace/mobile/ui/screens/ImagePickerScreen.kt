package com.example.imagepickerapp

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat

// NEW: Import AsyncImage and ImageRequest from Coil
import coil.compose.AsyncImage //
import coil.request.ImageRequest //


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImagePickerScreen() {
    // State to hold the URI of the selected image
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) } //
    // State to manage permission request status
    var hasPermission by remember { mutableStateOf(false) } //
    // Context for permission checking
    val context = LocalContext.current //

    // Determine the permission string based on Android version
    val permissionToRequest = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { //
        Manifest.permission.READ_MEDIA_IMAGES // For Android 13 (API 33) and above //
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE // For Android 12 (API 32) and below //
    }

    // Launcher for requesting permissions
    val permissionLauncher = rememberLauncherForActivityResult( //
        contract = ActivityResultContracts.RequestPermission(), //
        onResult = { isGranted -> //
            hasPermission = isGranted //
            if (!isGranted) { //
                // Handle case where permission is denied (e.g., show a message)
                println("Permission denied: $permissionToRequest") //
            }
        }
    )

    // Launcher for picking an image from the gallery
    val imagePickerLauncher = rememberLauncherForActivityResult( //
        contract = ActivityResultContracts.GetContent(), // Contract to get content (like images) //
        onResult = { uri: Uri? -> //
            // This lambda is called when an image is selected or the picker is cancelled
            selectedImageUri = uri //
        }
    )

    // Check permission status on composition
    LaunchedEffect(Unit) { //
        hasPermission = ContextCompat.checkSelfPermission( //
            context, //
            permissionToRequest //
        ) == PackageManager.PERMISSION_GRANTED //
    }

    Scaffold(
        topBar = {

        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                ,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Button(
                onClick = { //
                    if (hasPermission) { //
                        // If permission is granted, launch the image picker
                        imagePickerLauncher.launch("image/*") // MIME type for images //
                    } else {
                        // Request permission if not granted
                        permissionLauncher.launch(permissionToRequest) //
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text("Pick Image from Gallery") //
            }

            Spacer(modifier = Modifier.height(24.dp)) //

            // Display the selected image
            selectedImageUri?.let { uri -> //
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(), //
                        contentAlignment = Alignment.Center //
                    ) {
                        // --- IMPORTANT: Use an image loading library like Coil or Glide here ---
                        // Example with Coil:
                        AsyncImage( //
                            model = ImageRequest.Builder(context) //
                                .data(uri) //
                                .crossfade(true) //
                                .build(), //
                            contentDescription = "Selected Image", //
                            modifier = Modifier.fillMaxSize(), //
                            contentScale = ContentScale.Fit //
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp)) //
                Text(
                    text = "Selected URI: $uri", //
                    style = MaterialTheme.typography.bodySmall, //
                    color = Color.Gray, //
                    modifier = Modifier.padding(horizontal = 8.dp) //
                )
            } ?: run {
                // Text when no image is selected
                Text(
                    text = "No image selected yet.", //
                    style = MaterialTheme.typography.bodyLarge, //
                    color = MaterialTheme.colorScheme.onSurfaceVariant //
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewImagePickerScreen() {
    ImagePickerScreen() //
}