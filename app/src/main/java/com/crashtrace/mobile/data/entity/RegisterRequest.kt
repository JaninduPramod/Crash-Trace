package com.crashtrace.mobile.data.entity

data class RegisterRequest(
    val name: String,
    val nic: String,
    val email: String,
    val password: String
)
