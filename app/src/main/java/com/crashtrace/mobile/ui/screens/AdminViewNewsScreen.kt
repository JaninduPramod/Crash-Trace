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
import com.crashtrace.mobile.R
import com.crashtrace.mobile.ui.components.AppBarMain
import com.crashtrace.mobile.ui.components.DeletNewsAlertBox
import com.crashtrace.mobile.ui.components.RejectNewsAlertBox
import com.crashtrace.mobile.ui.components.SaveNewsAlertBox
import com.crashtrace.mobile.viewmodel.AdminGalleryViewModel
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import org.koin.androidx.compose.koinViewModel

@Composable
fun AdminNewsViewScreen(navController: NavHostController, cardId: String) {

    val viewModel: AdminGalleryViewModel = koinViewModel()
    val item = viewModel.adminNewsList.collectAsState().value.find { it.cardId == cardId }

    if (item == null) {
        Text("News not found!", modifier = Modifier.padding(16.dp))
        return
    }

    var isEditing by remember { mutableStateOf(false) }

    var title by remember { mutableStateOf(item.title) }
    var description by remember { mutableStateOf(item.description) }
    var damageRate by remember { mutableStateOf(item.damageRate.toString()) }
    var reporterId by remember { mutableStateOf(item.reporterId) }
    var vehicleNo by remember { mutableStateOf(item.vehicleNo) }
    var location by remember { mutableStateOf(item.location) }

    var showSaveDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showPublishDialog by remember { mutableStateOf(false) }
    var showRejectDialog by remember { mutableStateOf(false) }

    var loadProfile by remember { mutableStateOf(false) }
    if (loadProfile) {
        navController.navigate("profile")
        loadProfile = false
    }
    var backToFeed by remember { mutableStateOf(false) }
    if (backToFeed) {
        AdminNewsGalleryScreen(navController = navController)
        return
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
            AppBarMain(
                title = "REPOTER VIEW",
                BackButton = true,
                onBackClick = { navController.popBackStack() }, // <-- Add this line
                onProfileClick = { isProfile -> if (isProfile) loadProfile = true }
            )


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(10.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                if (isEditing) {

                    Text(
                        text = "EDIT NEWS",
                        color = Color(0xFF343434),
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Card(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(0.dp), // outer spacing
                        elevation = CardDefaults.cardElevation(4.dp), // shadow
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color.White)
                                .padding(8.dp)
                        ) {
                        OutlinedTextField(
                            value = title,
                            onValueChange = { title = it },
                            label = { Text("Title") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp),
                            shape = RoundedCornerShape(12.dp),
                                    colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color.LightGray,
                            unfocusedBorderColor = Color.LightGray
                        )

                        )

                        OutlinedTextField(
                            value = description,
                            onValueChange = { description = it },
                            label = { Text("Description") },
                            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                                .padding(horizontal = 10.dp),
                                    shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.LightGray,
                                unfocusedBorderColor = Color.LightGray
                            )
                        )

                        OutlinedTextField(
                            value = damageRate,
                            onValueChange = { damageRate = it },
                            label = { Text("Damage Rate") },
                            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                                .padding(horizontal = 10.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.LightGray,
                                unfocusedBorderColor = Color.LightGray
                            )
                        )

                        OutlinedTextField(
                            value = reporterId,
                            onValueChange = { reporterId = it },
                            label = { Text("Reporter ID") },
                            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                                .padding(horizontal = 10.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.LightGray,
                                unfocusedBorderColor = Color.LightGray
                            )
                        )

                        OutlinedTextField(
                            value = vehicleNo,
                            onValueChange = { vehicleNo = it },
                            label = { Text("Vehicle No") },
                            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                                .padding(horizontal = 10.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.LightGray,
                                unfocusedBorderColor = Color.LightGray
                            )
                        )

                        OutlinedTextField(
                            value = location,
                            onValueChange = { location = it },
                            label = { Text("Location") },
                            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                                .padding(horizontal = 10.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.LightGray,
                                unfocusedBorderColor = Color.LightGray
                            )
                        )

                    Spacer(modifier = Modifier.height(40.dp))


                    Row(
                        modifier = Modifier.fillMaxWidth().height(70.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            modifier = Modifier.fillMaxWidth().height(52.dp).padding(horizontal = 10.dp),
                            onClick = { showSaveDialog = true },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF0000)),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("SAVE", color = Color.White)
                        }

                        Button(
                            modifier = Modifier.width(168.dp),
                            onClick = {

                                title = item.title
                                description = item.description
                                damageRate = item.damageRate.toString()
                                reporterId = item.reporterId
                                vehicleNo = item.vehicleNo
                                location = item.location
                                isEditing = false
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("CANCEL", color = Color.White)
                        }
                    }}}
                } else {

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 0.dp)
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
                                text = item.date,
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
                                        text = item.title,
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
                                        val (iconRes, iconColor) = when {
                                            item.damageRate <= 50 -> R.drawable.tick_circle to Color(
                                                0xFF4CAF50
                                            )

                                            item.damageRate in 51..74 -> R.drawable.minus_cirlce to Color(
                                                0xFFFF9800
                                            )

                                            else -> R.drawable.close_circle to Color.Red
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
                                            Text(
                                                text = "Trust Rate",
                                                fontWeight = FontWeight.Medium,
                                                fontSize = 14.sp,
                                                color = Color.Gray
                                            )
                                            val backgroundColor = when {
                                                item.damageRate <= 50 -> Color(0xFFD2FCD3) // light green
                                                item.damageRate in 51..74 -> Color(0xFFFFE0B2) // light orange
                                                else -> Color(0xFFFFCDD2) // light red
                                            }

                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth(0.5f)
                                                    .height(10.dp)
                                                    .clip(RoundedCornerShape(4.dp))
                                                    .background(backgroundColor)
                                            ) {
                                                val foregroundColor = when {
                                                    item.damageRate <= 50 -> Color(0xFF00F508) // Light Green
                                                    item.damageRate in 51..74 -> Color(0xFFFF9800) // Orange
                                                    else -> Color(0xFFFF4155) // Red
                                                }

                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxWidth(0.38f)
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
                                text = item.description,
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
                                text = item.reporterId,
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
                                text = item.damageRate.toString(),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color.Black.copy(alpha = 0.4f)
                            )

                            Text(
                                text = "Vehicle Details",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = Color.Black,
                                modifier = Modifier.padding(top = 10.dp, bottom = 2.dp)
                            )
                            Text(
                                text = item.vehicleNo,
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
                                text = item.location,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color.Red
                            )
                            Spacer(modifier = Modifier.height(20.dp))

                            val cameraLatLng = remember(item.locationUrl) {
                                extractLatLngFromUrl(item.locationUrl) ?: LatLng(6.9271, 79.8612)
                            }

                            val cameraPositionState = rememberCameraPositionState {
                                position = CameraPosition.fromLatLngZoom(cameraLatLng, 15f)
                            }

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
                                    title = item.location,
                                    snippet = "Reported Location",
                                    icon = BitmapDescriptorFactory.fromResource(R.drawable.car_accident)
                                )
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            Text(
                                text = "If you want to make any changes",
                                color = Color.Gray,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(bottom = 10.dp)
                            )

                            // EDIT Button
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(0xFF333333), RoundedCornerShape(15.dp))
                                    .padding(3.dp),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Button(
                                    modifier = Modifier.width(175.dp)
                                        .padding(horizontal = 4.dp),
                                    onClick = { isEditing = true },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Text("EDIT", color = Color.White)
                                }
                                Button(
                                    modifier = Modifier.width(175.dp)
                                        .padding(horizontal = 4.dp),
                                    onClick = { showDeleteDialog = true },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Text("DELETE", color = Color.White)
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = "If you want to publish or delete this NEWS",
                                color = Color.Gray,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(bottom = 5.dp)
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(0xFF333333), RoundedCornerShape(15.dp)),

                                horizontalArrangement = Arrangement.Center
                            ) {
                                Button(
                                    modifier = Modifier
                                        .width(175.dp)
                                        .padding(horizontal = 4.dp),
                                    onClick = { showRejectDialog = true },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(
                                        0xFFFFD45A
                                    )
                                    ),

                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Text("REJECT", color = Color.DarkGray)
                                }
                                Button(
                                    modifier = Modifier
                                        .width(175.dp)
                                        .padding(horizontal = 4.dp),
                                    onClick = { showPublishDialog = true },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF90EE90)),
                                    shape = RoundedCornerShape(10.dp),

                                ) {
                                    Text("PUBLISH", color = Color.DarkGray)
                                }

                            }


                        }
                    }
                }
            }
        }

        // Save Confirmation Dialog
        if (showSaveDialog) {
            SaveNewsAlertBox(
                onConfirm = {
                    showSaveDialog = false
                    isEditing = false
                    viewModel.updateItem(
                        cardId = cardId,
                        title = title,
                        description = description,
                        damageRate = damageRate.toIntOrNull() ?: 0,
                        reporterId = reporterId,
                        vehicleNo = vehicleNo,
                        location = location
                    )
                },
                onDismiss = { showSaveDialog = false }
            )
        }


        if (showDeleteDialog) {
            DeletNewsAlertBox(
                onConfirm = {
                    showDeleteDialog = false
                    viewModel.deleteItem(cardId)
                    navController.popBackStack() // Navigate back after delete
                },
                onDismiss = { showDeleteDialog = false }
            )
        }

        if (showPublishDialog) {
            DeletNewsAlertBox(
                onConfirm = {
                    showPublishDialog = false
                    viewModel.deleteItem(cardId)
                    navController.popBackStack() // Navigate back after publish
                },
                onDismiss = { showPublishDialog = false }
            )
        }


        if (showRejectDialog) {
            RejectNewsAlertBox(
                onConfirm = {
                    showRejectDialog = false
                    viewModel.deleteItem(cardId)
                    navController.popBackStack() // Navigate back after reject
                },
                onDismiss = { showRejectDialog = false }
            )
        }

    }

}