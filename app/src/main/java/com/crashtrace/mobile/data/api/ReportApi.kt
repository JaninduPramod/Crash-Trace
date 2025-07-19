package com.crashtrace.mobile.data.api

import com.crashtrace.mobile.data.entity.ReportRequest
import com.crashtrace.mobile.data.entity.ReportResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ReportApi {
    @POST("/api/reports/create")
    suspend fun submitReport(@Body request: ReportRequest): Response<ReportResponse>
}
