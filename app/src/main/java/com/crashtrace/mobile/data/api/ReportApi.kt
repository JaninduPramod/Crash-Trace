package com.crashtrace.mobile.data.api

import com.crashtrace.mobile.data.entity.ApiResponse
import com.crashtrace.mobile.data.entity.Report
import com.crashtrace.mobile.data.entity.ReportRequest
import com.crashtrace.mobile.data.entity.ReportResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ReportApi {
    @POST("/api/reports/create")
    suspend fun submitReport(@Header("Authorization")token:String, @Body request: ReportRequest): Response<ApiResponse<ReportResponse>>

    @GET("/api/reports/approvedReports")
    suspend fun getApprovedReports(@Header("Authorization") token: String): Response<ApiResponse<List<Report>>>


}
