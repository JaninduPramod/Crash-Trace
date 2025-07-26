package com.crashtrace.mobile.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.crashtrace.mobile.R
import com.crashtrace.mobile.network.SupabaseClient
import com.crashtrace.mobile.ui.components.AppBarMain
import com.crashtrace.mobile.viewmodel.ReportViewModel
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchReportScreen(navController: NavHostController) {

    val reportViewModel: ReportViewModel = koinViewModel()
    val vehicleNumber by reportViewModel.vehicleNumber.collectAsState()
    val date by reportViewModel.date.collectAsState()
    val description by reportViewModel.description.collectAsState()
    val address by reportViewModel.address.collectAsState()
    val reporter by reportViewModel.reporter.collectAsState()
    val damageRate by reportViewModel.damageRate.collectAsState()
    val title by reportViewModel.title.collectAsState()
    val trustRate by reportViewModel.trustRate.collectAsState()

    val location by reportViewModel.location.collectAsState()
    val lat by reportViewModel.lat.collectAsState()
    val lng by reportViewModel.lng.collectAsState()

    var searchedVehicleNumber by remember { mutableStateOf("") }
    var loadProfile by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        reportViewModel.resetFields()
    }

    if (loadProfile) {
        navController.navigate("profile")
        loadProfile = false
    }

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
            AppBarMain(title = "REPORTER VIEW", BackButton = false,
                onProfileClick = { isProfile ->
                    if (isProfile) loadProfile = true
                }
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                // Search Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                        .shadow(8.dp, RoundedCornerShape(16.dp), clip = false),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "SEARCH REPORT",
                            fontWeight = FontWeight.Bold,
                            fontSize = 28.sp,
                            color = Color.Black,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        Text(
                            text = "Search Report Using Vehicle Number",
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        OutlinedTextField(
                            value = vehicleNumber,
                            onValueChange = { reportViewModel.setVehicleNumber(it)},
                            placeholder = {
                                Text(
                                    text = "Enter Vehicle Number",
                                    color = Color.Gray,
                                    fontSize = 14.sp
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    color = Color(0xFFF0F0F0),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(horizontal = 12.dp, vertical = 2.dp),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Transparent,
                                unfocusedBorderColor = Color.Transparent,
                                focusedContainerColor = Color(0xFFF0F0F0),
                                unfocusedContainerColor = Color(0xFFF0F0F0)
                            )
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                searchedVehicleNumber = vehicleNumber
                                reportViewModel.searchReport()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = "SEARCH",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                    }
                }

                // Accident Report Card
                if (date.isNotBlank()) {
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
                            if (searchedVehicleNumber.isNotBlank()) {
                                val imageUrl = SupabaseClient.getImageUrl("$searchedVehicleNumber.jpg")
                                AsyncImage(
                                    model = imageUrl,
                                    contentDescription = "Main News",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp)
                                        .clip(RoundedCornerShape(16.dp))
                                        .background(Color.LightGray),
                                    contentScale = ContentScale.Crop
                                )
                                Spacer(modifier = Modifier.height(30.dp))
                            }

                            Text(
                                text = date,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFFFF4D4D),
                                fontSize = 12.sp,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )

                            // Title + Trust Rate (left) + Icon (right)
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 0.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = title,
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
                                        // Convert trustRate (0.0-1.0 Double) to a percentage (0-100 Int) for comparison
                                        val trustRatePercentage = (trustRate * 100).toInt()

                                        // CORRECTED: Icon and color logic to match AdminNewsViewScreen
                                        val (iconRes, iconColor) = when {
                                            trustRatePercentage <= 50 -> R.drawable.close_circle to Color(0xFFAF4C4C) // Red for low trust
                                            trustRatePercentage in 51..74 -> R.drawable.minus_cirlce to Color(0xFFFF9800) // Orange for medium trust
                                            else -> R.drawable.tick_circle to Color(0xFF4CAF50) // Green for high trust
                                        }

                                        Icon(
                                            painter = painterResource(id = iconRes),
                                            contentDescription = "Status",
                                            tint = iconColor,
                                            modifier = Modifier
                                                .padding(end = 10.dp)
                                                .size(36.dp)
                                        )

                                        Column(modifier = Modifier.weight(1f)) {
                                            val trustRateForBar = (trustRate * 100).toInt()
                                            Text(
                                                text = "Trust Rate " + trustRateForBar.toString() + "%",
                                                fontWeight = FontWeight.Medium,
                                                fontSize = 14.sp,
                                                color = Color.Gray
                                            )

                                            // CORRECTED: Background color logic to match AdminNewsViewScreen
                                            val backgroundColor = when {
                                                trustRateForBar <= 50 -> Color(0xFFFCD2D2) // light red for low trust
                                                trustRateForBar in 51..74 -> Color(0xFFFFE0B2) // light orange for medium trust
                                                else -> Color(0xFFD2FCD3) // light green for high trust
                                            }

                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth(0.5f)
                                                    .height(10.dp)
                                                    .clip(RoundedCornerShape(4.dp))
                                                    .background(backgroundColor)
                                            ) {
                                                // CORRECTED: Foreground color logic to match AdminNewsViewScreen
                                                val foregroundColor = when {
                                                    trustRateForBar <= 50 -> Color(0xFFF50000) // Red for low trust
                                                    trustRateForBar in 51..74 -> Color(0xFFFF9800) // Orange for medium trust
                                                    else -> Color(0xFF00F508) // Green for high trust
                                                }

                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxWidth(trustRateForBar / 100f)
                                                        .fillMaxHeight()
                                                        .clip(RoundedCornerShape(6.dp))
                                                        .background(foregroundColor)
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            Text(
                                text = description,
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
                                text = reporter,
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
                                text = damageRate.toString() + "%",
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
                                text = address,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color.Red
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            val accidentPosition = LatLng(lat, lng)
                            val cameraPositionState = rememberCameraPositionState {
                                position = CameraPosition.fromLatLngZoom(accidentPosition, 15f)
                            }

                            GoogleMap(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(330.dp)
                                    .clip(RoundedCornerShape(8.dp)),
                                cameraPositionState = cameraPositionState
                            ) {
                                Marker(
                                    state = MarkerState(position = accidentPosition),
                                    title = address,
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
fun SearchReportScreenPreview() {
    SearchReportScreen(navController = rememberNavController())
}