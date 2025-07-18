package com.crashtrace.mobile.data.repository


import com.crashtrace.mobile.data.api.RetrofitInstance
import com.crashtrace.mobile.data.entity.ApiResponse
import com.crashtrace.mobile.data.entity.EmailVerificationRequest
import com.crashtrace.mobile.data.entity.EmailVerificationResponse
import com.crashtrace.mobile.data.entity.OtpVerificationRequest
import com.crashtrace.mobile.data.entity.ResetPasswordRequest

class PasswordResetRepository {

    suspend fun resetSendOtp(email: String): ApiResponse<EmailVerificationResponse>?{

        val request = EmailVerificationRequest(email)

        return try {
            val response = RetrofitInstance.api.sendOtp(request)
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


    suspend fun verifyOtpCode(email: String,otp: String) : ApiResponse<EmailVerificationResponse>?{
        

        val request = OtpVerificationRequest(email, otp)

        return try {
            val response = RetrofitInstance.api.verifyOtp(request)
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

    suspend fun resetPassword(email: String, password: String, confirmPassword: String): ApiResponse<EmailVerificationResponse>? {
        val request = ResetPasswordRequest(email, password, confirmPassword)
        return try {
            val response = RetrofitInstance.api.resetPassword(request)
            if (response.isSuccessful) {
                response.body()
            } else {
                println("API call failed: ${response.errorBody()?.string() ?: "Unknown error"}")
                null
            }
        } catch (e: Exception) {
            println("API call failed: ${e.message}")
            null
        }
    }


}