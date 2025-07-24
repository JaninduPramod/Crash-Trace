package com.crashtrace.mobile.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.crashtrace.mobile.network.SupabaseClient
import com.crashtrace.mobile.ui.components.AppBarMain
import androidx.navigation.NavHostController

@Composable
fun ImageRetrievalScreen(navController: NavHostController) {
    var imageName by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf<String?>(null) }
    var loadProfile by remember { mutableStateOf(false) }

    if (loadProfile) {
        navController.navigate("profile")
        loadProfile = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppBarMain(
            title = "Image Retrieval",
            BackButton = true,
            onBackClick = { navController.popBackStack() },
            onProfileClick = { loadProfile = true }
        )
        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = imageName,
            onValueChange = { imageName = it },
            label = { Text("Enter Image Name (e.g., work.jpg)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (imageName.isNotBlank()) {
                    imageUrl = SupabaseClient.getImageUrl(imageName)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Retrieve Image")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .clip(RoundedCornerShape(12.dp))
                .border(1.dp, Color.Gray, RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            if (imageUrl != null) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "Retrieved Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            } else {
                Text("Image will be displayed here")
            }
        }
    }
}
