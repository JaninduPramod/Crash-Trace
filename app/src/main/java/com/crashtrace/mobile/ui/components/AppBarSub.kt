package com.crashtrace.mobile.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex



@Composable
fun AppBarSub(title: String, showOverlay: Boolean = false) {
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
                    .background(Color(0xCC000000))
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
        // Title text
        Text(
            text = title,
            color = Color.White,
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 20.dp, bottom = 40.dp)
        )
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
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AppBarSubPreview() {
    AppBarSub(title = "Preview Title")
}
