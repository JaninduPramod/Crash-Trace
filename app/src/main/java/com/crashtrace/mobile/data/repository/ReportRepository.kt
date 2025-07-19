package com.crashtrace.mobile.data.repository

import com.crashtrace.mobile.data.api.ReportApi
import com.crashtrace.mobile.data.entity.ReportRequest
import com.crashtrace.mobile.data.entity.ReportResponse
import retrofit2.Response

class ReportRepository {
    suspend fun submitReport(vehicleNumber: String, description: String,location: String,address:String, date: String){
//        val request = ReportRequest(vehicleNumber, description, location, date)
//
//        println(request)

//        return try {
//            val response: Response<ReportResponse> = ReportApi.submitReport(request)
//            if (response.isSuccessful) {
//                val reportResponse = response.body()
//                reportResponse?.message ?: "Report submitted successfully"
//            } else {
//                val errorMessage = response.errorBody()?.string() ?: "Unknown error"
//                println("API call failed: $errorMessage")
//            }
//        } catch (e: Exception) {
//            println("API call failed: ${e.message}")
//        }

        println("Vehicle Number:"+vehicleNumber)
        println("Description:"+description)
        println("Location:"+location)
        println("Address:"+address)
        println("Date:"+date)
    }
}
