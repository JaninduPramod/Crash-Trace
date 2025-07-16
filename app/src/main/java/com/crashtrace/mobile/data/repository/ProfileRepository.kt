package com.crashtrace.mobile.data.repository

import com.crashtrace.mobile.data.api.RetrofitInstance
import com.crashtrace.mobile.data.entity.ApiResponse
import com.crashtrace.mobile.data.entity.UserProfile

class ProfileRepository {
    suspend fun userProfile(token: String): ApiResponse<UserProfile>? {


        return try {
            val response = RetrofitInstance.api.getUserProfile("Bearer $token")
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