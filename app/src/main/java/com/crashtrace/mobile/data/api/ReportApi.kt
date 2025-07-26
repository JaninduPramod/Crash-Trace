package com.crashtrace.mobile.data.api

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
import com.crashtrace.mobile.data.entity.VoteReportRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface ReportApi {
    @POST("/api/reports/create")
    suspend fun submitReport(@Header("Authorization")token:String, @Body request: ReportRequest): Response<ApiResponse<ReportResponse>>

    @GET("/api/reports/approvedReports")
    suspend fun getApprovedReports(@Header("Authorization") token: String): Response<ApiResponse<List<Report>>>

    @POST("/api/reports/searchReport")
    suspend fun searchReport(@Header("Authorization") token: String,@Body request: SearchReportRequest): Response<ApiResponse<SearchReportResponse>>

    @GET("/api/reports/allReports")
    suspend fun getAllReports(@Header("Authorization") token: String): Response<ApiResponse<List<AllReports>>>

    @PUT("/api/reports/editReport")
    suspend fun editReport(@Header("Authorization") token: String,@Body request: UpdateReportRequest): Response<ApiResponse<ReportResponse>>

    @POST("/api/reports/processReport")
    suspend fun reportOption(@Header("Authorization")token:String, @Body request: ReportOptionRequest): Response<ApiResponse<ReportResponse>>

    @GET("/api/stats")
    suspend fun getHomeStat(@Header("Authorization") token: String): Response<ApiResponse<HomeStatResponse>>

    @POST("/api/reports/vote")
    suspend fun voteReport(@Header("Authorization") token: String, @Body request: VoteReportRequest): Response<ApiResponse<ReportResponse>>


}
