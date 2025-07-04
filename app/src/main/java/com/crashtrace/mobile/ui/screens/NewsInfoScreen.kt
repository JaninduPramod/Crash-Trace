package com.crashtrace.mobile.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.crashtrace.mobile.R
import com.crashtrace.mobile.ui.components.AppBarMain
import com.crashtrace.mobile.ui.components.MyCustomCard
import com.crashtrace.mobile.ui.components.CardItem
import androidx.compose.ui.tooling.preview.Preview
import com.google.maps.android.compose.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng

@Composable
fun NewsInfoScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        // Background image
        Image(
            painter = painterResource(id = R.drawable.background_image),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // White top gradient
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.5f),
                            Color.White.copy(alpha = 0f)
                        )
                    )
                )
        )

        Column(modifier = Modifier.fillMaxSize()) {
            AppBarMain(title = "", BackButton = true)

            // Scrollable content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                // Box to allow image + overlaid card
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    // Image
                    Image(
                        painter = painterResource(id = R.drawable.accident),
                        contentDescription = "Main News",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp),
                        contentScale = ContentScale.Crop
                    )

                    // Overlaid card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp)
                            .padding(top = 200.dp) // overlap effect
                            .shadow(
                                elevation = 8.dp,
                                shape = RoundedCornerShape(16.dp),
                                clip = false
                            ),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "2025/04/28",
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFFFF2D2D),
                                fontSize = 12.sp,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                            Text(
                                text = "Preview Card Title",
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp,
                                color = Color.Black
                            )
                            Text(
                                text = "Another common cause of auto damage: having a parked vehicle hit by another car. Whether you're leaving your car in a parking lot or on the road, take steps to help avoid parked car collisions and claims. Here are some suggestions:\n\nhaving a parked vehicle hit by another car.  hit by another car to help avoid parked car",
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
                                text = "Mirihana",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Red.copy(alpha = 0.6f)
                            )

                            val mirihana = LatLng(6.8750, 79.9020)
                            val cameraPositionState = rememberCameraPositionState {
                                position = CameraPosition.fromLatLngZoom(mirihana, 15f)
                            }
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
                                    state = MarkerState(position = mirihana),
                                    title = "Mirihana",
                                    snippet = "Accident Location"
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewsInfoScreenPreview() {
    NewsInfoScreen()
}
