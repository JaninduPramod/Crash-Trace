package com.crashtrace.mobile.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.foundation.verticalScroll

import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.crashtrace.mobile.R
import com.crashtrace.mobile.ui.components.AppBarMain
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.crashtrace.mobile.network.SupabaseClient
import com.crashtrace.mobile.viewmodel.NewsGalleryViewModel
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.maps.android.compose.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import org.koin.androidx.compose.koinViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton

@Composable
fun NewsInfoScreen(
    navController: NavHostController,
    cardId: String

) {

    var isFullScreen by remember { mutableStateOf(false) }
    var loadProfile by remember { mutableStateOf(false) }
    var backToFeed by remember { mutableStateOf(false) }


    if (loadProfile) {
        navController.navigate("profile")
        loadProfile = false
    }

    if (backToFeed) {
        MainNavScreen(navController = navController, selectedIndex = 1)
        return
    }
    val viewModel: NewsGalleryViewModel = koinViewModel()
    val newsList by viewModel.newsList.collectAsState()

    val selectedItem = newsList.find { it.cardId == cardId }

    if (selectedItem == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Loading or item not found...")
        }
        return
    }

    val imageUrl = SupabaseClient.getImageUrl("${selectedItem.vehiclenub}.jpg")

    val cameraLatLng = remember(selectedItem.locationUrl) {
        extractLatLngFromUrl(selectedItem.locationUrl) ?: LatLng(6.9271, 79.8612)
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(cameraLatLng, 15f)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.background_image),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(modifier = Modifier.fillMaxSize()) {
            AppBarMain(
                title = "",
                BackButton = true,
                onBackClick = { navController.popBackStack() },
                onProfileClick = { /* no-op */ }
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = "News Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .background(Color.LightGray)
                            .clickable { isFullScreen = true },
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
                        error = painterResource(id = R.drawable.ic_launcher_foreground)
                    )

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp)
                            .padding(top = 200.dp)
                            .shadow(elevation = 8.dp, shape = RoundedCornerShape(16.dp), clip = false),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = selectedItem.date,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFFFF2D2D),
                                fontSize = 12.sp,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )

                            Text(
                                text = selectedItem.title,
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp,
                                color = Color.Black
                            )

                            Text(
                                text = selectedItem.description,
                                color = Color.Black.copy(alpha = 0.5f),
                                fontSize = 12.sp,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            Text(
                                text = "Location",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = Color.Black,
                                modifier = Modifier.padding(top = 8.dp, bottom = 2.dp)
                            )

                            Text(
                                text = selectedItem.location,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Red.copy(alpha = 0.6f)
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            GoogleMap(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(330.dp)
                                    .padding(top = 8.dp)
                                    .clip(RoundedCornerShape(8.dp)),
                                cameraPositionState = cameraPositionState
                            ) {
                                Marker(
                                    state = MarkerState(position = cameraLatLng),
                                    title = selectedItem.location,
                                    snippet = "Accident Location",
                                    icon = BitmapDescriptorFactory.fromResource(R.drawable.car_accident)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))
            }
        }

        if (isFullScreen) {
            FullScreenImageView(imageUrl = imageUrl) {
                isFullScreen = false
            }
        }
    }
}

@Composable
fun FullScreenImageView(imageUrl: String, onClose: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.9f))
            .clickable(onClick = onClose),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = "Full screen image",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentScale = ContentScale.Fit,
            placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
            error = painterResource(id = R.drawable.ic_launcher_foreground)
        )
        IconButton(
            onClick = onClose,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close full screen",
                tint = Color.White
            )
        }
    }
}

fun extractLatLngFromUrl(locationUrl: String): LatLng? {
    val parts = locationUrl.split(",")
    if (parts.size == 2) {
        val lat = parts[0].trim().toDoubleOrNull()
        val lng = parts[1].trim().toDoubleOrNull()
        if (lat != null && lng != null) {
            return LatLng(lat, lng)
        }
    }
    return null
}
