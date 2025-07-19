package com.crashtrace.mobile.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.crashtrace.mobile.R
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex
import com.crashtrace.mobile.viewmodel.ProfileViewModel
import com.crashtrace.mobile.viewmodel.SignUpViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun AppBarSub(
    title: String,
    showOverlay: Boolean = false,
    backButton: Boolean = false,
    onBackClick: (() -> Unit)? = null
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(170.dp)
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp),
                clip = false
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFF2D2D))
        )
        if (showOverlay) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0x99000000))
                    .zIndex(999f)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .align(Alignment.BottomCenter)
                .background(
                    brush = Brush.verticalGradient(
                        listOf(Color.Transparent, Color.Black.copy(alpha = 0.3f))
                    ),
                    shape = RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp)
                )
        )

        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 20.dp, end = 20.dp, bottom = 40.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                color = Color.White,
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
        }

        if (backButton) {
            Icon(
                painter = painterResource(id = R.drawable.back_button_icon),
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 20.dp, bottom = 50.dp)
                    .size(32.dp)
                    .clickable { onBackClick?.invoke() }
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .align(Alignment.BottomCenter)
                .zIndex(1f)
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                    ambientColor = Color.Black,
                    spotColor = Color.Black,
                    clip = false
                )
                .background(
                    color = Color(0xFFF3F6F8),
                    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                )
        )
    }
}


// ✅ Mock data
data class UserProfile(
    val userName: String,
    val userNick: String,
    val userEmail: String,
    val userNIC: String,
    val userAddress: String,
    val userMobile: String
)

fun getMockUserProfile(): UserProfile {
    return UserProfile(
        userName = "Sia Kroven",
        userNick = "Sia097",
        userEmail = "Siakrovenedvid@gmail.com",
        userNIC = "991952695V",
        userAddress = "31,B\nLULU LANE,\nMOTER PARCK\nKANDY",
        userMobile = "0762233448"
    )
}

@Composable
fun ProfileScreen(navController: NavHostController) {

    val user = getMockUserProfile()
    var showLogoutDialog by remember { mutableStateOf(false) }

    val profileViewModel: ProfileViewModel = koinViewModel()
    val name by profileViewModel.name.collectAsState()
    val nic by profileViewModel.nic.collectAsState()
    val email by profileViewModel.email.collectAsState()
    val role by profileViewModel.role.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3F6F8))
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // ✅ AppBar with working back button
            AppBarSub(
                title = "Profile",
                backButton = true,
                showOverlay = showLogoutDialog,
                onBackClick = { navController.popBackStack() }
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF3F6F8))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 0.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp, 0.dp, 10.dp, 0.dp),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Hi ${name}\nWelcome !",
                                fontWeight = FontWeight.Bold,
                                fontSize = 28.sp,
                                color = Color(0xFF222222),
                                modifier = Modifier.padding(20.dp, 0.dp, 0.dp, 20.dp)
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp, 10.dp, 0.dp, 16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.sia_croven),
                                    contentDescription = "Profile",
                                    modifier = Modifier
                                        .size(66.dp)
                                        .clip(CircleShape)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = name,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp,
                                        color = Color(0xFF222222)
                                    )
                                    Text(
                                        text = email,
                                        color = Color(0xFF888888),
                                        fontSize = 14.sp
                                    )
                                }
                            }

                            ProfileInfoItem(
                                icon = R.drawable.user_icon,
                                label = "Name:",
                                value = name,
                                onClick = null,
                                backgroundColor = Color(0xFFEAEAEA)
                            )
                            ProfileInfoItem(
                                icon = R.drawable.email_icon,
                                label = "Email:",
                                value = email,
                                onClick = null,
                                backgroundColor = Color(0xFFEAEAEA)
                            )
                            ProfileInfoItem(
                                icon = R.drawable.nic_nub_icon,
                                label = "NIC no:",
                                value = nic,
                                onClick = null,
                                backgroundColor = Color(0xFFEAEAEA)
                            )
                            ProfileInfoItem(
                                icon = R.drawable.location_icon,
                                label = "Address:",
                                value = user.userAddress,
                                onClick = null,
                                backgroundColor = Color(0xFFEAEAEA)
                            )
                            ProfileInfoItem(
                                icon = R.drawable.phone_icon,
                                label = "Mobile no:",
                                value = user.userMobile,
                                onClick = null,
                                backgroundColor = Color(0xFFEAEAEA)
                            )

                            Spacer(modifier = Modifier.height(75.dp))


                        HorizontalDivider(
                            modifier = Modifier.fillMaxWidth(),
                            thickness = 1.dp,
                            color = Color.Gray
                        )
                            HorizontalDivider(
                                modifier = Modifier.fillMaxWidth(),
                                thickness = 1.dp,
                                color = Color.Gray
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp, 0.dp, 10.dp, 0.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "if you want to log out?",
                                    color = Color(0xFF676767),
                                    fontSize = 14.sp,
                                    modifier = Modifier.weight(1f)
                                )
                                Button(
                                    onClick = { showLogoutDialog = true },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF2D2D)),
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier
                                        .height(44.dp)
                                        .width(120.dp)
                                ) {
                                    Text(
                                        text = "Log out",
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp
                                    )
                                }
                            }
                        }
                    }
                }

                if (showLogoutDialog) {
                    com.crashtrace.mobile.ui.components.LogoutAlertBox(
                        navController = navController,
                        onDismiss = { showLogoutDialog = false }
                    )
                }
            }
        }
    }
}

// ✅ Reusable profile info item
@Composable
fun ProfileInfoItem(
    icon: Int,
    label: String,
    value: String,
    onClick: (() -> Unit)?,
    backgroundColor: Color = Color(0xFFF3F6F8)
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(0.dp, 10.dp, 10.dp, 0.dp),
        color = backgroundColor,
        shadowElevation = 0.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp)
                .let { base -> if (onClick != null) base.clickable { onClick() } else base },
            verticalAlignment = Alignment.Top
        ) {
            val iconSize = if (icon == R.drawable.nic_nub_icon) 26.dp else 22.dp
            Image(
                painter = painterResource(id = icon),
                contentDescription = label,
                modifier = Modifier.size(iconSize),
                colorFilter = ColorFilter.tint(Color(0xFF222222))
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = label,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = Color(0xFF222222)
                )
                Text(
                    text = value,
                    color = Color(0xFF888888),
                    fontSize = 14.sp
                )
            }
            if (onClick != null) {
                Icon(
                    painter = painterResource(id = R.drawable.email_icon),
                    contentDescription = "Edit",
                    tint = Color(0xFF888888),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(navController = rememberNavController())
}
