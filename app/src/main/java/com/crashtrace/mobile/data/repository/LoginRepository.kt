package com.crashtrace.mobile.data.repository

import com.crashtrace.mobile.data.api.RetrofitInstance
import com.crashtrace.mobile.data.entity.ApiResponse
import com.crashtrace.mobile.data.entity.LoginResponse
import com.crashtrace.mobile.data.entity.LoginRequest

class LoginRepository {
    suspend fun userLogin(email: String, password: String): ApiResponse<LoginResponse>? {
        val request = LoginRequest(email, password)

        return try {
            val response = RetrofitInstance.api.loginUser(request)
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
