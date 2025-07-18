package com.crashtrace.mobile.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.maps.android.compose.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng

@Composable
fun NewsLocationScreen( navController: NavController) {
    var loadProfile by remember { mutableStateOf(false) }

    if (loadProfile) {
        navController.navigate("profile")
        loadProfile = false
    }


    Box(modifier = Modifier.fillMaxSize()) {
        // Background image
        Image(
            painter = painterResource(id = R.drawable.background_image),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

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

            AppBarMain(title = "FIND LOCATION", BackButton = false,
                onProfileClick = { isProfile ->
                    if (isProfile) loadProfile = true
                })



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

                    Text(
                        text = "NEWS Location",
                        color = Color(0xFF343434),
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp,
                        modifier = Modifier.padding(start = 16.dp, top = 30.dp, bottom = 0.dp)
                    )


                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp)
                            .padding(top = 80.dp)
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
                                    .height(600.dp)
                                    .padding(top = 8.dp)
                                    .clip(RoundedCornerShape(8.dp)),
                                cameraPositionState = cameraPositionState



                            ) {
                                Marker(
                                    state = MarkerState(position = mirihana),
                                    title = "Mirihana",
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
    }
}

@Preview(showBackground = true)
@Composable
fun NewsLocationPreview() {
    NewsLocationScreen(navController = rememberNavController())
}
