package com.crashtrace.mobile.data.repository

import com.crashtrace.mobile.data.api.RetrofitInstance
import com.crashtrace.mobile.data.entity.ApiResponse
import com.crashtrace.mobile.data.entity.RegisterData
import com.crashtrace.mobile.data.entity.UserData

// Repository class for sign-up functionality
class SignUpRepository {
    suspend fun saveSignUpData(name: String, nic: String, email: String, password: String): ApiResponse<UserData>? {
        val request = RegisterData(name, nic, email, password)
        return try {
            val response = RetrofitInstance.api.registerUser(request)
            if (response.isSuccessful) {
                val apiResponse = response.body()

                apiResponse // Return the ApiResponse object
            } else {
                val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                println("API call failed: $errorMessage")
                null // Return null for unsuccessful responses
            }
        } catch (e: Exception) {
            println("API call failed: ${e.message}")
            null // Return null in case of an exception
        }
    }
}
