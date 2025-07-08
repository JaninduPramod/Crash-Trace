package com.crashtrace.mobile.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.crashtrace.mobile.R
import com.crashtrace.mobile.ui.components.AppBarMain
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@Composable
fun AdminNewsViewScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
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
            AppBarMain(title = "REPOTER VIEW", BackButton = false)

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                        .padding(top = 0.dp)
                        .shadow(8.dp, RoundedCornerShape(16.dp), clip = false),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.accident),
                            contentDescription = "Main News",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .clip(RoundedCornerShape(16.dp)),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(modifier = Modifier.height(30.dp))

                        Text(
                            text = "2025/04/28",
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFFFF4D4D),
                            fontSize = 12.sp,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 0.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Preview Card Title",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 24.sp,
                                    color = Color.Black
                                )

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.close_circle),
                                        contentDescription = "status",
                                        tint = Color.Red,
                                        modifier = Modifier
                                            .padding(end = 12.dp)
                                            .size(28.dp)
                                    )

                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = "Trust Rate",
                                            fontWeight = FontWeight.Medium,
                                            fontSize = 14.sp,
                                            color = Color.Gray
                                        )
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth(0.5f)
                                                .height(10.dp)
                                                .clip(RoundedCornerShape(4.dp))
                                                .background(Color(0xFFFFCDD2))
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth(0.38f)
                                                    .fillMaxHeight()
                                                    .clip(RoundedCornerShape(4.dp))
                                                    .background(Color(0xFFFF4155))
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        Text(
                            text = "Another common cause of auto damage: having a parked vehicle hit by another car. Whether you're leaving your car in a parking lot or on the road, take steps to help avoid parked car collisions and claims. Here are some suggestions:\n\nhaving a parked vehicle hit by another car.  hit by another car to help avoid parked car",
                            color = Color.Black.copy(alpha = 0.5f),
                            fontSize = 12.sp,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text = "Reporter",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color.Black,
                            modifier = Modifier.padding(top = 0.dp, bottom = 2.dp)
                        )
                        Text(
                            text = "Sia Kroven",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.Black.copy(alpha = 0.4f)
                        )

                        Text(
                            text = "Damage Rate",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color.Black,
                            modifier = Modifier.padding(top = 10.dp, bottom = 2.dp)
                        )
                        Text(
                            text = "80%",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.Black.copy(alpha = 0.4f)
                        )

                        Text(
                            text = "Other Details",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color.Black,
                            modifier = Modifier.padding(top = 10.dp, bottom = 2.dp)
                        )
                        Text(
                            text = "Mirihana",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.Black.copy(alpha = 0.4f)
                        )

                        Text(
                            text = "Location",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color.Black,
                            modifier = Modifier.padding(top = 10.dp, bottom = 2.dp)
                        )
                        Text(
                            text = "Mirihana",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.Red
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        val mirihana = LatLng(6.8750, 79.9020)
                        val cameraPositionState = rememberCameraPositionState {
                            position = CameraPosition.fromLatLngZoom(mirihana, 15f)
                        }

                        GoogleMap(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(330.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            cameraPositionState = cameraPositionState
                        ) {
                            Marker(
                                state = MarkerState(position = mirihana),
                                title = "Mirihana",
                                snippet = "Accident Location"
                            )
                        }



                        Text(
                            text = "if you want to make any changes",
                            color = Color.Gray,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(bottom = 10.dp ,top = 20.dp)
                        )
                        // EDIT & SAVE Row
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFF333333), RoundedCornerShape(15.dp))
                                .padding(3.dp)
                        ) {


                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Button(
                                    modifier = Modifier.width(168.dp),
                                    onClick = { /* TODO: Edit action */ },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEDEDED)),
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Text("EDIT", color = Color.Black)
                                }

                                Button(
                                    modifier = Modifier.width(168.dp),
                                    onClick = { /* TODO: Save action */ },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Text("SAVE", color = Color.White)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "if you want to publish or delete this NEWS",
                            color = Color.Gray,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(bottom = 5.dp)
                        )

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFF333333), RoundedCornerShape(15.dp))
                                .padding(2.dp)
                        ) {


                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Button(
                                    modifier = Modifier.width(168.dp),
                                    onClick = { /* TODO: Delete action */ },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEDEDED)),
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Text("DELETE", color = Color.Black)
                                }

                                Button(
                                    modifier = Modifier.width(168.dp),
                                    onClick = { /* TODO: Publish action */ },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF98FB98)),
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Text("PUBLISH", color = Color.Black)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AdminNewsViewScreenPreview() {
    AdminNewsViewScreen()
}
