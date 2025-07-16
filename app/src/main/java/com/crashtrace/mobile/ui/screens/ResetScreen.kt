package com.crashtrace.mobile.ui.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.crashtrace.mobile.viewmodel.PasswordResetViewModel
import androidx.navigation.compose.rememberNavController
import com.crashtrace.mobile.ui.components.AppBarSub
import androidx.compose.ui.tooling.preview.Preview
import org.koin.androidx.compose.koinViewModel

@Composable
fun ResetScreen(navController: NavHostController) {

    val passwordResetViewModel: PasswordResetViewModel = koinViewModel()

    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") } // Added for password confirmation
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) } // Added for confirm password visibility
    var logoutAllDevices by remember { mutableStateOf(false) } // Changed from rememberMe

    val success by passwordResetViewModel.newPasswordSuccess.collectAsState()
    val message by passwordResetViewModel.message.collectAsState()

    
fun handleSaveButtonClick() {
    passwordResetViewModel.resetPassword(password, confirmPassword)
    
}

LaunchedEffect(success) {
        if (success) {
            navController.navigate("signin")
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3F6F8))
    ) {
        AppBarSub(title = "Reset" ,backButton = true)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF3F6F8))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Reset your Account Password", // Changed title
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    color = Color(0xFF222222)
                )
                Text(
                    text = "Please enter and confirm your new password", // Changed description
                    color = Color(0xFF888888),
                    fontSize = 15.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Spacer(modifier = Modifier.height(50.dp))

                // Enter Password Field
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = {
                        Text(
                            text = "Enter Password",
                            color = Color(0xFFCACACA),
                            fontSize = 15.sp
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val icon = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = icon,
                                contentDescription = "Toggle Password Visibility",
                                tint = Color(0xFFCACACA)
                            )
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color(0xFFCACACA),
                        focusedBorderColor = Color(0xFFCACACA),
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Reenter Password Field
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    placeholder = {
                        Text(
                            text = "Reenter Password",
                            color = Color(0xFFCACACA),
                            fontSize = 15.sp
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val icon = if (confirmPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                        IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                            Icon(
                                imageVector = icon,
                                contentDescription = "Toggle Password Visibility",
                                tint = Color(0xFFCACACA)
                            )
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color(0xFFCACACA),
                        focusedBorderColor = Color(0xFFCACACA),
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Log out all devices checkbox
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Checkbox(
                        checked = logoutAllDevices,
                        onCheckedChange = { logoutAllDevices = it },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Color(0xFFCACACA),
                            uncheckedColor = Color(0xFFCACACA),
                            checkmarkColor = Color.White
                        ),
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "Log out all devices",
                        color = Color(0xFF888888),
                        fontSize = 15.sp,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(310.dp))

                // Save Button
                Button(
                    onClick = {
                        handleSaveButtonClick()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF2D2D)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "SAVE",
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
fun ResetScreenPreview() {
    ResetScreen(navController = rememberNavController())
}
