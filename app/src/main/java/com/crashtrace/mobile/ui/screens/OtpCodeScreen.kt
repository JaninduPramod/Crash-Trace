package com.crashtrace.mobile.ui.screens

import androidx.compose.foundation.BorderStroke
import com.crashtrace.mobile.viewmodel.PasswordResetViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.crashtrace.mobile.ui.components.AppBarSub
import androidx.compose.ui.tooling.preview.Preview
import org.koin.androidx.compose.koinViewModel


@Composable
fun OtpCodeScreen(navController: NavHostController) {
    val passwordResetViewModel: PasswordResetViewModel = koinViewModel()
    var otp1 by remember { mutableStateOf("") }
    var otp2 by remember { mutableStateOf("") }
    var otp3 by remember { mutableStateOf("") }
    var otp4 by remember { mutableStateOf("") }

    val otpSuccess by passwordResetViewModel.otpSuccess.collectAsState()

    
    // function to handle submission of OTP code
    fun onChangeEmailClick() {
        passwordResetViewModel.resetState()
        navController.navigate("reset")
    }

    fun handleOtpSubmission() {
        val otp = otp1 + otp2 + otp3 + otp4 // Combine the OTP inputs
        passwordResetViewModel.submitOtpCode(otp) // Call ViewModel function
    }

    LaunchedEffect(otpSuccess) {
        if (otpSuccess) {
            navController.navigate("newPassword")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3F6F8))
    ) {
        AppBarSub(title = "Verify Me")
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF3F6F8))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "OTP Verification",
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    color = Color(0xFF222222)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Do you Want to change Your Email ? ",
                        color = Color(0xFF888888),
                        fontSize = 15.sp
                    )
                    Text(
                        text = "Change EMAIL",
                        color = Color(0xFFFF2D2D),
                        fontSize = 15.sp,
                        modifier = Modifier.clickable  { onChangeEmailClick()}
                    )
                }

                Text(
                    text = "To verify your account, enter the 4 digit OTP code that we sent to your email",
                    color = Color(0xFF888888),
                    fontSize = 15.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Spacer(modifier = Modifier.height(50.dp))

                // OTP Input Boxes
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 50.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedTextField(
                        value = otp1,
                        onValueChange = { if (it.length <= 1) otp1 = it },
                        modifier = Modifier
                            .width(60.dp)
                            .height(50.dp)
                            .padding(horizontal = 4.dp),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true,
                        textStyle = androidx.compose.ui.text.TextStyle(
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color(0xFFCACACA),
                            focusedBorderColor = Color(0xFFCACACA),
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        )
                    )
                    OutlinedTextField(
                        value = otp2,
                        onValueChange = { if (it.length <= 1) otp2 = it },
                        modifier = Modifier
                            .width(60.dp)
                            .height(50.dp)
                            .padding(horizontal = 4.dp),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true,
                        textStyle = androidx.compose.ui.text.TextStyle(
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color(0xFFCACACA),
                            focusedBorderColor = Color(0xFFCACACA),
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        )
                    )
                    OutlinedTextField(
                        value = otp3,
                        onValueChange = { if (it.length <= 1) otp3 = it },
                        modifier = Modifier
                            .width(60.dp)
                            .height(50.dp)
                            .padding(horizontal = 4.dp),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true,
                        textStyle = androidx.compose.ui.text.TextStyle(
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color(0xFFCACACA),
                            focusedBorderColor = Color(0xFFCACACA),
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        )
                    )
                    OutlinedTextField(
                        value = otp4,
                        onValueChange = { if (it.length <= 1) otp4 = it },
                        modifier = Modifier
                            .width(60.dp)
                            .height(50.dp)
                            .padding(horizontal = 4.dp),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true,
                        textStyle = androidx.compose.ui.text.TextStyle(
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color(0xFFCACACA),
                            focusedBorderColor = Color(0xFFCACACA),
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        )
                    )
                }

                Spacer(modifier = Modifier.height(360.dp))

                // Submit Button
                Button(
                    onClick = {
                        handleOtpSubmission();
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF2D2D)),
                    shape = RoundedCornerShape(15.dp)
                ) {
                    Text(
                        text = "Submit",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Resend OTP Button
                OutlinedButton(
                    onClick = { passwordResetViewModel.executeResetSendOtp() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color(0xFFF5F5F5)
                    ),
                    border = BorderStroke(1.dp, Color(0xFF444444)),
                    shape = RoundedCornerShape(15.dp)
                ) {
                    Text(
                        text = "Resend OTP",
                        color = Color(0xFF444444),
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
fun OtpCodeScreenPreview() {
    OtpCodeScreen(navController = rememberNavController())
}
