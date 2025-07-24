package com.crashtrace.mobile.ui.screens

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.crashtrace.mobile.ui.components.AppBarSub
import com.crashtrace.mobile.viewmodel.SignUpViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignUpScreen(navController: NavHostController) {
    // Use Koin's koinViewModel function for dependency injection
    val signUpViewModel: SignUpViewModel = koinViewModel()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()


    val name by signUpViewModel.name.collectAsState()
    val nic by signUpViewModel.nic.collectAsState()
    val email by signUpViewModel.email.collectAsState()
    val password by signUpViewModel.password.collectAsState()


    var passwordVisible by remember { mutableStateOf(false) }
    var rememberMe by remember { mutableStateOf(false) }
    var loading by remember { mutableStateOf(false) } // Loading state



    val activity = context as Activity

    fun handleSignUp() {
        loading = true
        coroutineScope.launch {
            signUpViewModel.submitSignUpData().collect { response ->
                loading = false
                if (response?.success == true) {
                    signUpViewModel.resetFields()
                    Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()
                    navController.navigate("signin")

                } else {
                    signUpViewModel.resetFields()
                    Toast.makeText(context, response?.message ?: "Sign up failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }



    val webClientId = "679764917586-ounn6jabilj8qa3n8lup744e4qbu2pi9.apps.googleusercontent.com"

    // Configure Google Sign-In
    val googleSignInClient = remember {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(webClientId)
            .requestEmail()
            .build()
        GoogleSignIn.getClient(activity, gso)
    }


    // Google Sign-In launcher
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            // navigate to home screen or perform any action with the account

            signUpViewModel.setEmail(account.email ?: "")
            signUpViewModel.setName(account.displayName ?: "")
            signUpViewModel.setNIC(account.id ?: "")
            signUpViewModel.setPassword(account.idToken ?: "")
            signUpViewModel.submitSignUpData()

            signUpViewModel.resetFields()


            Toast.makeText(context, "Google SignUp Successfull", Toast.LENGTH_SHORT).show()
            navController.navigate("signin")



        } catch (e: ApiException) {

            println("GoogleSignIn"+ "Sign-in failed with status code: ${e.statusCode}"+ e)
            if (e.statusCode == 10) {
                println("GoogleSignIn"+"Possible misconfiguration: Check Web Client ID and SHA-1 in Firebase Console.")
            }
        }
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3F6F8)) // Changed background color here
    ) {
        AppBarSub(title = "Sign Up!")
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
                    text = "Create new\naccount",
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
                        text = "Already Have an account?",
                        color = Color(0xFF888888),
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "Sign in",
                        color = Color(0xFFFF2D2D),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        modifier = Modifier.clickable { navController.navigate("signin") }
                    )
                }
                Spacer(modifier = Modifier.height(50.dp))
                // Name Field
                OutlinedTextField(
                    value = name,
                    onValueChange = { signUpViewModel.setName(it) },
                    leadingIcon = {
                        Image(
                            painter = painterResource(id = com.crashtrace.mobile.R.drawable.user_icon),
                            contentDescription = "Name",
                            modifier = Modifier.size(26.dp),
                            colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color(0xFF272727))
                        )
                    },
                    placeholder = {

                        Text(
                            text = "Enter your Name",
                            color = Color(0xFFCACACA),
                            fontSize = 15.sp,
                            maxLines = 1,

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
                    )
                )
                Spacer(modifier = Modifier.height(12.dp))
                // NIC Field
                OutlinedTextField(
                    value = nic,
                    onValueChange = { signUpViewModel.setNIC(it) },
                    leadingIcon = {
                        Image(
                            painter = painterResource(id = com.crashtrace.mobile.R.drawable.nic_nub_icon),
                            contentDescription = "NIC",
                            modifier = Modifier.size(26.dp),
                            colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color(0xFF272727))
                        )
                    },
                    placeholder = {
                        Text(
                            text = "Enter your NIC",
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
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color(0xFFCACACA),
                        focusedBorderColor = Color(0xFFCACACA),
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,
                        errorContainerColor = Color.White
                    )
                )
                Spacer(modifier = Modifier.height(12.dp))
                // Email Field
                OutlinedTextField(
                    value = email,
                    onValueChange = { signUpViewModel.setEmail(it) },
                    leadingIcon = {
                        Image(
                            painter = painterResource(id = com.crashtrace.mobile.R.drawable.email_icon),
                            contentDescription = "Email",
                            modifier = Modifier.size(22.dp),
                            colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color(0xFF272727))
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
                                .height(28.dp), // Slightly larger placeholder area
                            contentAlignment = Alignment.BottomStart // Place text at the bottom
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
                    onValueChange = { signUpViewModel.setPassword(it) },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = com.crashtrace.mobile.R.drawable.lock),
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
                                tint = Color(0xFFCACACA) // Changed eye icon color
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
                // Remember Me
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp, 5.dp, 0.dp, 30.dp) // Move 10dp from left
                ) {
                    Surface(
                        shape = RoundedCornerShape(10.dp), // 10dp border radius
                        color = Color.Transparent // No background, just for shape
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
                            modifier = Modifier
                                .size(24.dp) // Optional: control checkbox size
                        )
                    }
                    Text(
                        text = "Remember me",
                        color = Color(0xFF888888),
                        fontSize = 14.sp,
                        modifier = Modifier.padding(start = 10.dp) // Padding between checkbox and text
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                // Sign Up Button
                Button(
                    onClick = {
                        handleSignUp()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF2D2D)),
                    shape = RoundedCornerShape(15.dp)
                ) {
                    Text(
                        text = "Sign Up",
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
                    Divider(modifier = Modifier.weight(1f), color = Color(0xFFE0E0E0))
                    Text(
                        text = "  or sign up with  ",
                        color = Color(0xFF888888),
                        fontSize = 14.sp
                    )
                    Divider(modifier = Modifier.weight(1f), color = Color(0xFFE0E0E0))
                }
                Spacer(modifier = Modifier.height(20.dp))
                // Google Sign Up Button
                OutlinedButton(
                    onClick = {
                        googleSignInClient.signOut().addOnCompleteListener {
                            val signInIntent = googleSignInClient.signInIntent
                            launcher.launch(signInIntent)
                        }
                    }, // Navigate to Google Sign In
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.outlinedButtonColors( // Material 3 ButtonDefaults
                        containerColor = Color.White, // CORRECTED: Use containerColor for M3
                        contentColor = Color(0xFF222222) // Optional: If you want to ensure text/icon color contrasts
                    ),
                    border = BorderStroke(1.dp, Color(0xFFCACACA))
                ) {
                    Image(
                        painter = painterResource(id = com.crashtrace.mobile.R.drawable.google_default),
                        contentDescription = "Google",
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Sign up with Google",
                        // Text color can also be set directly on Text composable if not using contentColor above
                        // color = Color(0xFF222222),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                Spacer(modifier = Modifier.height(40.dp))
                // Terms and Privacy
                Text(
                    buildAnnotatedString {
                        append("By clicking \"Sign Up\" you agree to Reconotes\n")
                        withStyle(style = SpanStyle(color = Color(0xFF008CFF), fontSize = 12.sp)) {
                            append("Term of Use")
                        }
                        append(" and ")
                        withStyle(style = SpanStyle(color = Color(0xFF008CFF), fontSize = 12.sp)) {
                            append("Privacy Policy")
                        }
                    },
                    color = Color(0xFF888888),
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            // Show loading indicator
            if (loading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color.LightGray)
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    // Provide a dummy NavHostController if needed, or use a lambda if your SignUpScreen doesn't require it for preview
    SignUpScreen(navController = androidx.navigation.compose.rememberNavController())
}
