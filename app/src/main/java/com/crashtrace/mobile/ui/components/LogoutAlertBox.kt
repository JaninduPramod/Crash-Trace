package com.crashtrace.mobile.ui.components

import android.content.Context
import android.widget.Toast
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.crashtrace.mobile.R
import com.crashtrace.mobile.viewmodel.LoginViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun LogoutAlertBox(
    navController: NavHostController,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val loginViewModel: LoginViewModel = koinViewModel()

    fun clearJwtToken(context: Context) {
        // Example with Toast for visual feedback
        Toast.makeText(context, "JWT token cleared", Toast.LENGTH_SHORT).show()

        loginViewModel.removeJwtToken()
    }


    fun handleLogout() {
        // 🧹 Replace with your JWT/token clearing logic
        clearJwtToken(context)

        // ✅ Navigate to signin screen
        navController.navigate("signin") {
            popUpTo(0) { inclusive = true }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0x99000000)),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .width(360.dp)
                .wrapContentHeight()
                .background(Color.White, shape = RoundedCornerShape(24.dp))
                .padding(15.dp)
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(Color(0x1AFF2D2D))
                    .clickable { onDismiss() },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.close_circle),
                    contentDescription = "Close",
                    modifier = Modifier.size(35.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "LOGOUT",
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    color = Color(0xFF222222),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Are you Sure ?",
                    color = Color(0xFFFF2D2D),
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 15.dp)
                )
                Button(
                    onClick = { handleLogout() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF2D2D)),
                    shape = RoundedCornerShape(15.dp)
                ) {
                    Text(
                        text = "LOGOUT",
                        color = Color.White,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

// ✅ Replace this with your real implementation (e.g. SharedPreferences or DataStore)

