package com.crashtrace.mobile.data.repository

import com.crashtrace.mobile.data.api.RetrofitInstance
import com.crashtrace.mobile.data.entity.RegisterRequest

// Repository class for sign-up functionality
class SignUpRepository {
    suspend fun saveSignUpData(name: String, nic: String, email: String, password: String) {
        val request = RegisterRequest(name, nic, email, password)
        try {
            val response = RetrofitInstance.api.registerUser(request)
            if (response.isSuccessful) {
                println("Success: ${response.body()}")
            } else {
                println("Error: ${response.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            println("API call failed: ${e.message}")
        }
    }
}
