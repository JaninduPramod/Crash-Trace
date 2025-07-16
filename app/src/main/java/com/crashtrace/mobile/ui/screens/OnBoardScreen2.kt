package com.crashtrace.mobile.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.crashtrace.mobile.R

@Composable
fun OnBoardScreen2(navController: NavController, onSkip: () -> Unit, onNext: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F6F6))
    ) {
        // Center onboard2 image in the Box with margin top 50dp
        Image(
            painter = painterResource(id = R.drawable.onboard2),
            contentDescription = "App Logo",
            modifier = Modifier
                .size(420.dp)
                .align(Alignment.Center)
                .padding(bottom = 120.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.istockphoto_back),
            contentDescription = "Background",
            modifier = Modifier.size(500.dp),
            contentScale = androidx.compose.ui.layout.ContentScale.Crop
        )

        // Top Row: Back Arrow and Skip Button on the same line
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 80.dp, start = 25.dp, end = 10.dp)
                .align(Alignment.TopCenter),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .background(Color(0xFFFF2D2D), shape = RoundedCornerShape(15.dp))
                    .size(50.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_left),
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
            TextButton(
                onClick = { navController.navigate("signup") },
            ) {
                Text(
                    text = "Skip",
                    color = Color(0xFFFF2D2D),
                    fontSize = 16.sp
                )
                Icon(
                    painter = painterResource(id = R.drawable.chevron_right),
                    contentDescription = "Skip",
                    tint = Color(0xFFFF2D2D),
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 25.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(120.dp))
            // Map Pin Image (replace with your own drawable if available)
            Box(
                modifier = Modifier
                    .size(290.dp),

                contentAlignment = Alignment.Center
            ) {

            }
            Spacer(modifier = Modifier.height(50.dp))
            // Place texts in a Column aligned to start (left)
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Report\nAccident Easily",
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    color = Color(0xFF222222),
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Help others stay safe by reporting accidents with a quick photo.",
                    fontSize = 14.sp,
                    color = Color(0xFF888888),
                    textAlign = TextAlign.Start
                )
            }
            Spacer(modifier = Modifier.height(45.dp))
            // Indicator Dots
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(width = 40.dp, height = 7.dp)
                        .background(Color(0xFFCCCCCC), shape = CircleShape)

                )
                Spacer(modifier = Modifier.width(6.dp))
                Box(
                    modifier = Modifier
                        .size(width = 40.dp, height = 7.dp)
                        .background(Color(0xFF222222), shape = RoundedCornerShape(4.dp))
                )
                Spacer(modifier = Modifier.width(6.dp))
                Box(
                    modifier = Modifier
                        .size(width = 40.dp, height = 7.dp)
                        .background(Color(0xFFCCCCCC), shape = CircleShape)
                )
            }
        }
        // Place the Button at the bottom of the Box
        Button(
            onClick = { navController.navigate("third") },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(start = 24.dp, end = 24.dp, bottom = 32.dp)
                .fillMaxWidth()
                .height(52.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF2D2D)),
            shape = RoundedCornerShape(15.dp)
        ) {
            Text(
                text = "Next",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnBoardScreen2Preview() {
    OnBoardScreen2(
        navController = rememberNavController(),
        onSkip = {},
        onNext = {}
    )
}
