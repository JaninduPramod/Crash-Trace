package com.crashtrace.mobile.data.entity

import com.google.gson.annotations.SerializedName
import okhttp3.Address

data class Report(
    val _id: String,
    val title: String,
    val vehicleNo: String,
    val description: String,
    val location: List<String>,
    val address: String,
    val reporterId: String,
    val date: String,
    val status: String,
    val trustRate: Int,
    val damageRate: Int,
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

data class SearchReportRequest(
    val vehicleNo: String
)

data class SearchReportResponse(
    val description: String,
    val location: List<String>,
    val address: String,
    val reporterId: Reporter?, // This will now map correctly
    val date: String,
    val trustRate: Int,
)

data class Reporter(
    val name: String
)

data class AllReports(
    val cardID: String,
    val vehicleNo: String,
    val title: String,
    val description: String,
    val location: List<String>,
    val address: String,
    val reporterId: Reporter?,
    val date: String,
    val status: String,
    val trustRate: Int,
    val damageRate: Int,
)

data class UpdateReportRequest(
    val cardID: String,
    val title: String,
    val description: String,
    val damageRate: Int,
    val vehicleNo: String,
    val address: String,
)

data class ReportOptionRequest(
    val cardID: String,
    val option: String,
)