package com.crashtrace.mobile.data.entity

import okhttp3.Address

data class Report(
    val _id: String,
    val vehicleNo: String,
    val description: String,
    val location: List<String>,
    val address: String,
    val reporterId: String,
    val date: String,
    val status: String,
    val trustRate: Int,
)


data class ReportRequest(
    val vehicleNo: String,
    val description: String,
    val location: String,
    val address: String,
    val date: String
)

data class ReportResponse(
    val message: String
)
