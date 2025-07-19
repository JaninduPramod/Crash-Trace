package com.crashtrace.mobile.ui.screens

import android.location.Geocoder
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.crashtrace.mobile.R
import com.crashtrace.mobile.ui.components.AppBarMain
import com.crashtrace.mobile.viewmodel.ReportViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.androidx.compose.koinViewModel
import java.util.*

@Composable
fun LocationPickerDialog(
    onDismissRequest: () -> Unit,
    onLocationSelected: (LatLng) -> Unit
) {
    val context = LocalContext.current

    var selectedLatLng by remember { mutableStateOf<LatLng?>(null) }
    var searchText by remember { mutableStateOf("") }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(6.9271, 79.8612), 12f)
    }

    LaunchedEffect(searchText) {
        val trimmed = searchText.trim()
        if (trimmed.length > 3) {
            kotlinx.coroutines.delay(500)
            val latLng = safeGeocodeLocation(context, trimmed)
            if (latLng != null) {
                selectedLatLng = latLng
                cameraPositionState.move(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
            }
        }
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.4f))
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { onDismissRequest() }
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(600.dp)
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
                color = Color.White,
                tonalElevation = 8.dp
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp, bottom = 4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            Modifier
                                .size(width = 40.dp, height = 4.dp)
                                .background(Color.LightGray, RoundedCornerShape(2.dp))
                        )
                    }

//                    Text(
//                        "Pick Location",
//                        fontWeight = FontWeight.Bold,
//                        fontSize = 16.sp,
//                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
//                    )




                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 12.dp)
                    ) {

                        OutlinedTextField(
                            value = searchText,
                            onValueChange = { searchText = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 0.dp)
                                .offset(y = 20.dp)  // Move the field 20 dp down
                                .zIndex(2f),  // Set the z-index to 2 (higher value brings it to the front)
                            placeholder = { Text("Search location...") },
                            singleLine = true,
                            shape = RoundedCornerShape(15.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.LightGray,
                                unfocusedBorderColor = Color.LightGray,
                                focusedContainerColor = Color.White.copy(alpha = 0.8f),  // White background when focused
                                unfocusedContainerColor = Color.White.copy(alpha = 0.8f)  // White background when unfocused
                            )
                        )

                        GoogleMap(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(5.dp)
                                .clip(RoundedCornerShape(15.dp))
                                ,
                            cameraPositionState = cameraPositionState,
                            onMapClick = { latLng -> selectedLatLng = latLng }
                        ) {
                            selectedLatLng?.let {
                                Marker(
                                    state = MarkerState(position = it),
                                    title = "Selected Location",
                                    icon = BitmapDescriptorFactory.fromResource(R.drawable.car_accident)
                                )
                            }
                        }
                    }

                    Button(
                        onClick = {
                            selectedLatLng?.let {
                                onLocationSelected(it)
                                onDismissRequest() // Close the map dialog
                            }
                        },
                        enabled = selectedLatLng != null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .height(52.dp),
                        shape = RoundedCornerShape(15.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                    ) {
                        Text("Confirm Location", color = Color.White)
                    }
                }
            }
        }
    }
}

suspend fun safeGeocodeLocation(context: android.content.Context, query: String): LatLng? {
    return withContext(Dispatchers.IO) {
        try {
            val geocoder = Geocoder(context)
            val addresses = geocoder.getFromLocationName(query, 1)
            addresses?.firstOrNull()?.let {
                LatLng(it.latitude, it.longitude)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}

@Composable
fun NewsDraftScreen(navController: NavHostController) {

    val reportViewModel: ReportViewModel = koinViewModel()
    val vehicleNumber by reportViewModel.vehicleNumber.collectAsState()
    val description by reportViewModel.description.collectAsState()
    val date by reportViewModel.date.collectAsState()
    val address by reportViewModel.address.collectAsState()

    var location_code by remember { mutableStateOf("") }

    // Update the ViewModel's location state whenever location_code changes
    LaunchedEffect(location_code) {
        reportViewModel.setLocation(location_code)
    }

    var loadProfile by remember { mutableStateOf(false) }
    var showLocationDialog by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }

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

        Column(modifier = Modifier.fillMaxSize()) {
            AppBarMain(
                title = "DRAFT A NEWS",
                BackButton = false,
                onProfileClick = { isProfile -> if (isProfile) loadProfile = true }
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "NEWS DRAFT",
                    color = Color(0xFF343434),
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    modifier = Modifier.padding(top = 30.dp)
                )

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("add images", fontSize = 14.sp, color = Color.Gray)
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .height(130.dp)
                                .clip(RoundedCornerShape(15.dp))
                                .background(Color.Gray.copy(alpha = 0.1f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("+", color = Color.Gray, fontSize = 24.sp)
                                Text("add images", color = Color.Gray, fontSize = 12.sp)
                            }
                        }

                        Text("Confirm News information", fontSize = 14.sp, color = Color.Gray, modifier = Modifier.padding(bottom = 16.dp))
                        CustomInputField("Vehicle Number", vehicleNumber, height = 60.dp) { reportViewModel.setVehicleNumber(it) }



                        CustomInputField("Description", description, height = 90.dp) { reportViewModel.setDescription(it) }


                        CustomInputField("Address", address, height = 60.dp) { reportViewModel.setAddress(it) }

                        Text("Pick Location", fontSize = 12.sp, color = Color.Gray, modifier = Modifier.padding(bottom = 4.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(46.dp)
                                .clip(RoundedCornerShape(15.dp))
                                .background(Color(0xFFF8F8F8))
                                .border(
                                    width = 1.dp,
                                    color = Color.LightGray,
                                    shape = RoundedCornerShape(15.dp) // match the clip shape
                                )
                                .clickable { showLocationDialog = true }
                                .padding(horizontal = 16.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Text(
                                text = if (location_code.isNotEmpty()) location_code else "Tap to pick location",
                                color = if (location_code.isNotEmpty()) Color.Black else Color.Gray,
                                fontSize = 14.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        DateInputField(
                            date = date,
                            onClick = { showDatePicker = true },
                            modifier = Modifier


                        )

                    }
                }


                Button(
                    onClick = { reportViewModel.submitReport()},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                        .height(52.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF0000))
                ) {
                    Text("SUBMIT", fontSize = 16.sp, color = Color.White)
                }
            }
        }
    }

    if (showLocationDialog) {
        LocationPickerDialog(
            onDismissRequest = { showLocationDialog = false },
            onLocationSelected = { latLng ->
                location_code = "${latLng.latitude},${latLng.longitude}"
            }
        )
    }

    if (showDatePicker) {
        DatePickerBottomSheet(
            onDismissRequest = { showDatePicker = false },
            onDateSelected = {
                reportViewModel.setDate(it)
                showDatePicker = false
            }
        )
    }
}

@Composable
fun CustomInputField(label: String, value: String, height: Dp = 60.dp, onValueChange: (String) -> Unit) {
    Text(label, fontSize = 12.sp, color = Color.Gray, modifier = Modifier.padding(bottom = 4.dp))
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .padding(bottom = 16.dp)
            .clip(RoundedCornerShape(15.dp)),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Gray,
            unfocusedBorderColor = Color.LightGray,
            focusedContainerColor = Color.LightGray,
            unfocusedContainerColor = Color(0xFFF8F8F8)
        ),
        shape = RoundedCornerShape(15.dp)
    )
}

@Composable
fun DateInputField(
    date: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "Date",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier
                .padding(start = 4.dp, bottom = 4.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
                .clip(RoundedCornerShape(15.dp))
                .border(
                    width = 1.dp,
                    color = Color.LightGray,
                    shape = RoundedCornerShape(15.dp)
                )
                .background(Color(0xFFF8F8F8))
                .clickable { onClick() },
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (date.isNotEmpty()) date else "Select Date",
                    color = if (date.isNotEmpty()) Color.Black else Color.Gray,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Select Date",
                    tint = Color.Gray
                )
            }
        }
    }
}


@Composable
fun DatePickerBottomSheet(
    initialDate: Calendar = Calendar.getInstance(),
    onDismissRequest: () -> Unit,
    onDateSelected: (String) -> Unit
) {
    val calendar = remember { initialDate.clone() as Calendar }
    var selectedDate by remember { mutableStateOf(calendar.time) }

    Box(
        Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.4f))
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { onDismissRequest() }
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(510.dp)
                .offset(y = (20).dp)
        ) {

            Surface(
                modifier = Modifier
                    .fillMaxSize()

                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                color = Color.White,
                tonalElevation = 8.dp
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    // Drag indicator
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp, bottom = 4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            Modifier
                                .size(width = 40.dp, height = 4.dp)
                                .background(Color.LightGray, RoundedCornerShape(2.dp))
                        )
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp)  // No padding around the card
                            .scale(1f)  // Reducing the scale to 80% of original size
                            .offset(y = (20).dp),  // Moves the Card 40px up
                        shape = RoundedCornerShape(15.dp),  // Rounded corners with 15.dp radius
                        colors = CardDefaults.cardColors(containerColor = Color.Transparent),  // Transparent background
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)  // No shadow (zero elevation)
                    )  {
                        // Content inside the Card
                        AndroidView(
                            modifier = Modifier
                                .fillMaxWidth()  // Ensures the AndroidView fills the card width
                                .padding(horizontal = 30.dp)
                                .weight(1f)
                                .offset(y = (-75).dp),  // Moves the DatePicker 40px up
                            factory = { context ->
                                android.widget.DatePicker(android.view.ContextThemeWrapper(context, R.style.Theme_Crash_Trace)).apply {
                                    // Set the scale (1.2x width, 1.4x height)
                                    scaleX = 1.2f
                                    scaleY = 1.2f

                                    init(
                                        calendar.get(Calendar.YEAR),
                                        calendar.get(Calendar.MONTH),
                                        calendar.get(Calendar.DAY_OF_MONTH)
                                    ) { _, year, month, dayOfMonth ->
                                        calendar.set(year, month, dayOfMonth)
                                        selectedDate = calendar.time
                                    }
                                }
                            }
                        )
                        Button(
                            onClick = {
                                val dateStr = "${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.MONTH) + 1}/${calendar.get(Calendar.YEAR)}"
                                onDateSelected(dateStr)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                               .padding(16.dp,0.dp,16.dp,0.dp)
                                .height(52.dp)
                                .offset(y = (-50).dp),
                            shape = RoundedCornerShape(15.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                        ) {
                            Text("Confirm Date", color = Color.White)
                        }
                    }

                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DraftNewsPreview() {
    NewsDraftScreen(navController = rememberNavController())
}
