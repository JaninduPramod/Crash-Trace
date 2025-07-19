package com.crashtrace.mobile.data.repository

import com.crashtrace.mobile.data.Utils.DataStoreManager
import com.crashtrace.mobile.data.api.RetrofitInstance
import com.crashtrace.mobile.data.entity.ApiResponse
import com.crashtrace.mobile.data.entity.ReportRequest
import com.crashtrace.mobile.data.entity.ReportResponse

class ReportRepository() {
    suspend fun submitReport(
        vehicleNo: String,
        description: String,
        location: String,
        address: String,
        date: String,
        token:String

    ): ApiResponse<ReportResponse>? {
        val request = ReportRequest(vehicleNo, description, location, address, date)


        return try {


            // Make the API call using Retrofit
        val response = RetrofitInstance.reportApi.submitReport("Bearer $token", request)           
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