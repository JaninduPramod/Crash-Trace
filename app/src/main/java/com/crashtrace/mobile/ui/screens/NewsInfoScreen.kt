// NewsInfoScreen.kt
package com.crashtrace.mobile.ui.screens

import android.app.Activity
import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.crashtrace.mobile.viewmodel.ReportViewModel
import kotlinx.coroutines.launch


@Composable
fun NewsInfoScreen(
    navController: NavHostController,
    cardId: String,
    origin: String = "unknown" // NEW: Receive the origin argument
) {

    // Initialize ViewModel before usage
    val viewModel: NewsGalleryViewModel = koinViewModel()
    val reportViewModel: ReportViewModel = koinViewModel()

    var isFullScreen by remember { mutableStateOf(false) }
    var loadProfile by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val activity = context as Activity
    // backToFeed is now handled by origin argument
    // var backToFeed by remember { mutableStateOf(false) }


    if (loadProfile) {
        navController.navigate("profile")
        loadProfile = false
    }


    fun handleVote() {
        coroutineScope.launch {

            reportViewModel.executeVoteReport().collect { response ->

                    Toast.makeText(context, "Successfully Voted", Toast.LENGTH_SHORT).show()
            }
        }
    }




    // Refresh news list when entering the detail screen
    LaunchedEffect(Unit) {
        viewModel.getNewsList()
    }
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

    // State for like and dislike buttons (true if pressed, false otherwise)
    var userLiked by remember { mutableStateOf(false) }
    var userDisliked by remember { mutableStateOf(false) }


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
                onBackClick = {
                    // MODIFIED: Navigate based on the 'origin' argument
                    when (origin) {
                        "from_gallery" -> navController.navigate("gallery")
                        "from_newsFeed" -> navController.navigate("mainScreen/1") // Assuming 1 is the index for NewsFeed
                        else -> navController.popBackStack() // Default behavior
                    }
                },
                onProfileClick = { navController.navigate("profile")}
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
                        Column(modifier = Modifier.padding(16.dp,16.dp,16.dp,0.dp)) {
                            Text(
                                text = selectedItem.date,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFFFF2D2D),
                                fontSize = 12.sp,

                                )

                            // --- Start of changes for like/dislike buttons ---
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = selectedItem.title,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 24.sp,
                                    color = Color.Black,
                                    modifier = Modifier.weight(1f)
                                )


                                // Like Button
                                IconButton(
                                    onClick = {
                                        userLiked = !userLiked // Toggle like state
                                        if (userLiked) { // If liked, ensure not disliked
                                            userDisliked = false
                                        }

                                        reportViewModel.setVoteType("up")
                                        reportViewModel.setReportDocumentId(cardId)
                                        handleVote()

                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ThumbUp,
                                        contentDescription = "Like",
                                        tint = if (userLiked) Color.Blue else Color.Gray, // Change color based on state
                                        modifier = Modifier.size(24.dp)
                                    )
                                }

                                // Dislike Button
                                IconButton(
                                    onClick = {
                                        userDisliked = !userDisliked // Toggle dislike state
                                        if (userDisliked) { // If disliked, ensure not liked
                                            userLiked = false
                                        }
                                        reportViewModel.setVoteType("down")
                                        reportViewModel.setReportDocumentId(cardId)
                                        handleVote()
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ThumbDown,
                                        contentDescription = "Dislike",
                                        tint = if (userDisliked) Color.Red else Color.Gray, // Change color based on state
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            }
                            // --- End of changes for like/dislike buttons ---

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
                            Spacer(modifier = Modifier.height(16.dp))
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