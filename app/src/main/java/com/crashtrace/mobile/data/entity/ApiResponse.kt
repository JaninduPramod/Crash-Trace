package com.crashtrace.mobile.data.entity

data class ApiResponse<T>(
    val data: T?,          // Generic type for flexible data handling
    val message: String,   // Message from the backend
    val success: Boolean   // Indicates success or failure
)
