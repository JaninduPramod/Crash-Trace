package com.crashtrace.mobile.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.crashtrace.mobile.R

@Composable
fun AppBarSub(
    title: String,
    showOverlay: Boolean = false,
    backButton: Boolean = false,
    navController: NavController? = null,
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
        // Red background
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFF2D2D))
        )
        // Overlay when alert is shown (dark area over app bar)
        if (showOverlay) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0x99000000)) // 80% opacity black
                    .zIndex(999f)
            )
        }
        // --- Inner shadow at the bottom of the red area ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .align(Alignment.BottomCenter)
                .background(
                    brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.3f)
                        )
                    ),
                    shape = RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp)
                )
        )
        // Title centered at the bottom
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

        // Back button at the right side corner with 10dp padding
        if (backButton) {
            Icon(
                painter = painterResource(id = R.drawable.back_button_icon),
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 20.dp, bottom = 50.dp)
                    .size(32.dp)
                    .clickable {
                        navController?.popBackStack() ?: onBackClick?.invoke()
                    }
            )
        }

        Spacer(modifier = Modifier.width(15.dp))
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

@Preview(showBackground = true)
@Composable
fun AppBarSubPreview() {
    AppBarSub(title = "Preview Title", backButton = true)
}
