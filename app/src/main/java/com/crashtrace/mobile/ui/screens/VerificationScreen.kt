package com.crashtrace.mobile.ui.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.crashtrace.mobile.R
import com.crashtrace.mobile.ui.components.AppBarSub
import com.crashtrace.mobile.viewmodel.PasswordResetViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun VerificationScreen(navController: NavHostController) {
    val passwordResetViewModel: PasswordResetViewModel = koinViewModel()

    val email by passwordResetViewModel.email.collectAsState()
    val message by passwordResetViewModel.message.collectAsState()
    val success by passwordResetViewModel.success.collectAsState()

    LaunchedEffect(success) {
        if (success) {
            navController.navigate("otpVerify")
        } else if (message.isNotEmpty()) {
            // Show an error message
            println(message)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3F6F8))
    ) {
        AppBarSub(title = "Verification", backButton = true)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF3F6F8))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Verify\nyour account",
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    color = Color(0xFF222222),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "We need your Email to send the OTP code and Verify Your email and Account?",
                        color = Color(0xFF888888),
                        fontSize = 14.sp
                    )
                }
                Spacer(modifier = Modifier.height(35.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = {passwordResetViewModel.setEmail(it) },
                    leadingIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.email_icon),
                            contentDescription = "Email",
                            modifier = Modifier.size(22.dp),
                            colorFilter = ColorFilter.tint(Color(0xFF272727))
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color(0xFFCACACA),
                        focusedBorderColor = Color(0xFFCACACA),
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,
                        errorContainerColor = Color.White
                    ),
                    placeholder = {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(28.dp),
                            contentAlignment = Alignment.BottomStart
                        ) {
                            Text(
                                text = "Enter your email",
                                color = Color(0xFFCACACA),
                                fontSize = 15.sp,
                                maxLines = 1
                            )
                        }
                    }
                )
                Spacer(modifier = Modifier.height(440.dp))
                Button(
                    onClick = { passwordResetViewModel.executeResetSendOtp() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF2D2D)),
                    shape = RoundedCornerShape(15.dp)
                ) {
                    Text(
                        text = "Verify",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }


            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VerificationScreenPreview() {
    VerificationScreen(navController = rememberNavController())
}
