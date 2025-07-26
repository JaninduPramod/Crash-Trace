package com.crashtrace.mobile.data.repository

import com.crashtrace.mobile.data.api.RetrofitInstance
import com.crashtrace.mobile.data.entity.AllReports
import com.crashtrace.mobile.data.entity.ApiResponse
import com.crashtrace.mobile.data.entity.HomeStatResponse
import com.crashtrace.mobile.data.entity.Report
import com.crashtrace.mobile.data.entity.ReportOptionRequest
import com.crashtrace.mobile.data.entity.ReportRequest
import com.crashtrace.mobile.data.entity.ReportResponse
import com.crashtrace.mobile.data.entity.SearchReportRequest
import com.crashtrace.mobile.data.entity.SearchReportResponse
import com.crashtrace.mobile.data.entity.UpdateReportRequest

class ReportRepository() {
    suspend fun submitReport(
        vehicleNo: String,
        description: String,
        location: String,
        address: String,
        date: String,
        token: String,
        value: String?,
        value1: String?

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


    suspend fun approvedReports(token: String): ApiResponse<List<Report>>? {

        return try {
            val response = RetrofitInstance.reportApi.getApprovedReports("Bearer $token")
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

    suspend fun searchReport(token: String,vehicleNo: String): ApiResponse<SearchReportResponse>?{

        val request = SearchReportRequest(vehicleNo)

        return try {
            val response = RetrofitInstance.reportApi.searchReport("Bearer $token", request)
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

    suspend fun getAllReports(token: String): ApiResponse<List<AllReports>>? {
        return try {
            val response = RetrofitInstance.reportApi.getAllReports("Bearer $token")
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


    suspend fun editReport(token: String,cardID: String,title:String,description:String,damageRate:Int,vehicleNo:String,address: String): ApiResponse<ReportResponse>?{

        val request = UpdateReportRequest(cardID,title,description,damageRate,vehicleNo,address)

        return try {
            val response = RetrofitInstance.reportApi.editReport("Bearer $token", request)
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


    suspend fun optionReport(token: String, cardID: String, option:String): ApiResponse<ReportResponse>? {


        val request = ReportOptionRequest(cardID,option)

        return try {
            val response = RetrofitInstance.reportApi.reportOption("Bearer $token", request)
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

    suspend fun homeStats(token: String): ApiResponse<HomeStatResponse>? {


        return try {
            val response = RetrofitInstance.reportApi.getHomeStat("Bearer $token")
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