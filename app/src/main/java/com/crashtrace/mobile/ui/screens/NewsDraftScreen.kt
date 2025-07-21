package com.crashtrace.mobile.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.crashtrace.mobile.R
import com.crashtrace.mobile.launchCamera
import com.crashtrace.mobile.ui.components.AppBarMain
import com.crashtrace.mobile.viewmodel.ReportViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.compose.koinViewModel
import java.io.File
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
                                .padding(horizontal = 16.dp)
                                .offset(y = 20.dp)
                                .zIndex(2f),
                            placeholder = { Text("Search location...") },
                            singleLine = true,
                            shape = RoundedCornerShape(15.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.LightGray,
                                unfocusedBorderColor = Color.LightGray,
                                focusedContainerColor = Color.White.copy(alpha = 0.8f),
                                unfocusedContainerColor = Color.White.copy(alpha = 0.8f)
                            )
                        )

                        GoogleMap(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(5.dp)
                                .clip(RoundedCornerShape(15.dp)),
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
                                onDismissRequest()
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

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    var location_code by remember { mutableStateOf("") }
    var loadProfile by remember { mutableStateOf(false) }
    var showLocationDialog by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }

    LaunchedEffect(location_code) {
        reportViewModel.setLocation(location_code)
    }


    if (loadProfile) {
        navController.navigate("profile")
        loadProfile = false
    }
    fun handleSubmit() {
        coroutineScope.launch {
            reportViewModel.submitReport().collect { response ->
                if (response?.success == true) {
                    Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()
                    navController.navigate("home")
                } else {
                    Toast.makeText(context, response?.message ?: "Submit Report Failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val photoFile = remember {
        File(
            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            "IMG_${System.currentTimeMillis()}.jpg"
        )
    }
    val photoUri = FileProvider.getUriForFile(
        context,
        "${context.packageName}.provider",
        photoFile
    )

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            imageUri = photoUri
        }
    }


    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            cameraLauncher.launch(photoUri)
        } else {
            Toast.makeText(context, "Camera permission denied", Toast.LENGTH_SHORT).show()
        }
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
                        Spacer(modifier = Modifier.height(10.dp))
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .height(130.dp)
                                .clip(RoundedCornerShape(15.dp))
                                .background(Color.Gray.copy(alpha = 0.1f))
                                .clickable {
                                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                                        == PackageManager.PERMISSION_GRANTED
                                    ) {
                                        cameraLauncher.launch(photoUri)
                                    } else {
                                        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                                    }
                                }
                        ) {
                            if (imageUri != null) {
                                Image(
                                    painter = rememberAsyncImagePainter(model = imageUri),
                                    contentDescription = "Captured Image",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Default.CameraAlt,
                                    contentDescription = "Camera Icon",
                                    tint = Color.Gray,
                                    modifier = Modifier
                                        .size(40.dp)
                                        .align(Alignment.Center)
                                )}}
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            "Confirm News information",
                            fontSize = 14.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

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
                                    shape = RoundedCornerShape(15.dp)
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
                            onClick = { showDatePicker = true }
                        )
                    }
                }

                Button(
                    onClick = { handleSubmit() },
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
            modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
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
                            .padding(0.dp)
                            .scale(1f)
                            .offset(y = (20).dp),
                        shape = RoundedCornerShape(15.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    )  {
                        AndroidView(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 30.dp)
                                .weight(1f)
                                .offset(y = (-75).dp),
                            factory = { context ->
                                android.widget.DatePicker(android.view.ContextThemeWrapper(context, R.style.Theme_Crash_Trace)).apply {
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
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun DraftNewsPreview() {
//    NewsDraftScreen(navController = rememberNavController())
//}
//
//@Composable
//fun NewsDraftScreen(navController: NavController) {
//    val context = LocalContext.current
//    var imageUri by remember { mutableStateOf<Uri?>(null) }
//
//    // Generate unique file & URI
//    val photoFile = remember {
//        File(
//            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
//            "IMG_${System.currentTimeMillis()}.jpg"
//        )
//    }
//    val photoUri = FileProvider.getUriForFile(
//        context,
//        "${context.packageName}.provider",
//        photoFile
//    )
//
//    // Launcher to take picture
//    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
//        if (success) {
//            imageUri = photoUri
//        }
//    }
//
//    // Launcher to request permission
//    val cameraPermissionLauncher = rememberLauncherForActivityResult(
//        ActivityResultContracts.RequestPermission()
//    ) { isGranted ->
//        if (isGranted) {
//            cameraLauncher.launch(photoUri)
//        } else {
//            Toast.makeText(context, "Camera permission denied", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    Box(
//        Modifier
//            .fillMaxWidth()
//            .height(130.dp)
//            .clip(RoundedCornerShape(15.dp))
//            .background(Color.Gray.copy(alpha = 0.1f))
//            .clickable {
//                if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
//                    == PackageManager.PERMISSION_GRANTED
//                ) {
//                    cameraLauncher.launch(photoUri)
//                } else {
//                    cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
//                }
//            }
//    ) {
//        if (imageUri != null) {
//            Image(
//                painter = rememberAsyncImagePainter(model = imageUri),
//                contentDescription = "Captured Image",
//                modifier = Modifier.fillMaxSize(),
//                contentScale = ContentScale.Crop
//            )
//        } else {
//            Icon(
//                imageVector = Icons.Default.CameraAlt,
//                contentDescription = "Camera Icon",
//                tint = Color.Gray,
//                modifier = Modifier
//                    .size(40.dp)
//                    .align(Alignment.Center)
//            )
//        }
//    }
//}



