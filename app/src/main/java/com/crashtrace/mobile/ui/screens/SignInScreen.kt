package com.crashtrace.mobile.ui.screens


import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.crashtrace.mobile.R
import com.crashtrace.mobile.ui.components.AppBarSub
import com.crashtrace.mobile.viewmodel.LoginViewModel
import com.crashtrace.mobile.viewmodel.ProfileViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun SigningInScreen(navController: NavHostController) {
    val loginViewModel: LoginViewModel = koinViewModel()
    val profileViewModel: ProfileViewModel = koinViewModel()

    val email by loginViewModel.email.collectAsState()
    val password by loginViewModel.password.collectAsState()


    var passwordVisible by remember { mutableStateOf(false) }
    var rememberMe by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current


    fun handleLogin() {
        coroutineScope.launch {
            loginViewModel.executeUserLogin().collect { response ->
                if (response?.success == true) {
                    if(response.data?.role == "admin")
                    {
                        println("Navigate to admin")
                        navController.navigate("adminGallery")
                    }

                    else
                    {
                        println("Navigate to home")
                        navController.navigate("home")
                    }
                    Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()
                    profileViewModel.executeUserProfile()

                } else {
                    Toast.makeText(context, response?.message ?: "Login failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3F6F8))
    ) {
        AppBarSub(title = "Sign In!")
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .background(Color(0xFFF3F6F8))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 0.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Sign in to\nyour account",
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
                        text = "Don't have an account?",
                        color = Color(0xFF888888),
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "Sign up",
                        color = Color(0xFFFF2D2D),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        modifier = Modifier.clickable { navController.navigate("signup") }
                    )
                }
                Spacer(modifier = Modifier.height(50.dp))

                // Debugging
                println("Email: $email, Password: $password")

                // Email Field
                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        loginViewModel.setEmail(it)
                    },
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
                Spacer(modifier = Modifier.height(12.dp))
                // Password Field
                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        loginViewModel.setPassword(it)
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.lock),
                            contentDescription = "Password",
                            modifier = Modifier.size(22.dp),
                            tint = Color(0xFF272727)
                        )
                    },
                    placeholder = {
                        Text(
                            text = "Enter your password",
                            color = Color(0xFFCACACA),
                            fontSize = 15.sp,
                            maxLines = 1
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
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
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,
                        errorContainerColor = Color.White
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                // Remember Me and Forgot Password Row
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 5.dp, end = 0.dp, bottom = 30.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = Color.Transparent
                    ) {
                        Checkbox(
                            checked = rememberMe,
                            onCheckedChange = { rememberMe = it },
                            colors = CheckboxDefaults.colors(
                                checkedColor = Color(0xFFFF2D2D),
                                uncheckedColor = Color(0xFFCACACA),
                                checkmarkColor = Color.White,
                                disabledCheckedColor = Color.LightGray,
                                disabledUncheckedColor = Color.LightGray,
                                disabledIndeterminateColor = Color.LightGray
                            ),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Text(
                        text = "Remember me",
                        color = Color(0xFF888888),
                        fontSize = 14.sp,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "Forgot password",
                        color = Color(0xFF008CFF),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .clickable { navController.navigate("reset") }
                            .padding(end = 4.dp)
                    )
                }
                Spacer(modifier = Modifier.height(230.dp))
                // Sign Up Button
                Button(
                    onClick = {
                        handleLogin()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF2D2D)),
                    shape = RoundedCornerShape(15.dp)
                ) {
                    Text(
                        text = "Sign In",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                // Divider with text
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    HorizontalDivider(modifier = Modifier.weight(1f), color = Color(0xFFE0E0E0))
                    Text(
                        text = "  or sign up with  ",
                        color = Color(0xFF888888),
                        fontSize = 14.sp
                    )
                    HorizontalDivider(modifier = Modifier.weight(1f), color = Color(0xFFE0E0E0))
                }
                Spacer(modifier = Modifier.height(20.dp))
                // Google Sign Up Button
                OutlinedButton(
                    onClick = { loginViewModel.getJwtToken() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.White,
                        contentColor = Color(0xFF222222)
                    ),
                    border = BorderStroke(1.dp, Color(0xFFCACACA))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.google_default),
                        contentDescription = "Google",
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Sign up with Google",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }

    
}


@Preview(showBackground = true)
@Composable
fun SigningInfoPreview() {
    SigningInScreen(navController = androidx.navigation.compose.rememberNavController())
}
