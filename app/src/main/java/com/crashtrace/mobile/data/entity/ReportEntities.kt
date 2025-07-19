package com.crashtrace.mobile.data.entity

import okhttp3.Address

data class ReportRequest(
    val vehicleNumber: String,
    val description: String,
    val location: String,
    val address: String,
    val date: String
)

data class ReportResponse(
    val message: String
)
