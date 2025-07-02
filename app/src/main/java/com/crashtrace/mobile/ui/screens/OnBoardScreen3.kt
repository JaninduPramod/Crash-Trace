package com.crashtrace.mobile.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.Font
import com.crashtrace.mobile.R

// Define Nanito Sans font family
@Composable
fun OnBoardScreen3(navController: NavController, onSkip: () -> Unit, onNext: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F6F6))
    ) {
        // Center onboard2 image in the Box with margin top 50dp
        Image(
            painter = painterResource(id = R.drawable.onboard3),
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
                onClick = { /* TODO: handle back action */ },
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
                onClick = onSkip,
            ) {
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
                    text = "Check\nTrust Score \nBefore You BUY",
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    color = Color(0xFF222222),
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Know a vehicle saftety score before making a purchase",
                    fontSize = 14.sp,
                    color = Color(0xFF888888),
                    textAlign = TextAlign.Start
                )
            }
            Spacer(modifier = Modifier.height(26.dp))
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
                        .background(Color(0xFFCCCCCC), shape = CircleShape)

                )
                Spacer(modifier = Modifier.width(6.dp))
                Box(
                    modifier = Modifier
                        .size(width = 40.dp, height = 7.dp)

                        .background(Color(0xFF222222), shape = RoundedCornerShape(4.dp))
                )
            }
        }
        // Place the Button at the bottom of the Box
        Button(
            onClick = onNext,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(start = 24.dp, end = 24.dp, bottom = 32.dp)
                .fillMaxWidth()
                .height(52.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF2D2D)),
            shape = RoundedCornerShape(15.dp)
        ) {
            Text(
                text = "Get Started",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            Icon(
                painter = painterResource(id = R.drawable.arrow_right),
                contentDescription = "Skip",
                tint = Color.White,
                modifier = Modifier
                    .size(30.dp)
                    .padding(5.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnBoardScreen3Preview() {
    OnBoardScreen3(
        navController = rememberNavController(),
        onSkip = {},
        onNext = {}
    )
}
