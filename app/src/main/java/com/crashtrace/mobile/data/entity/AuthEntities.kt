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