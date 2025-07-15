package com.crashtrace.mobile.data.entity

data class RegisterData(
    val name: String,
    val nic: String,
    val email: String,
    val password: String
);

data class UserData(
    val name: String,
    val nic: String,
    val email: String,
    val password: String
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val token: String,
    val role: String
)

data class EmailVerificationRequest(
    val email: String
)

data class EmailVerificationResponse(
    val message: String
)

data class OtpVerificationRequest(
    val email: String,
    val otp: String
)

data class ResetPasswordRequest(
    val email: String,
    val password: String,
    val confirmPassword: String
)
