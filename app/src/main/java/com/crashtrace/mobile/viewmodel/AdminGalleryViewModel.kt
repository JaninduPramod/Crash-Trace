package com.crashtrace.mobile.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crashtrace.mobile.data.Utils.DataStoreManager
import com.crashtrace.mobile.data.repository.ReportRepository
import com.crashtrace.mobile.ui.components.CardItem
import com.crashtrace.mobile.ui.components.CardItemAdmin
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class AdminGalleryViewModel(private val repository: ReportRepository, private val dataStoreManager: DataStoreManager) : ViewModel() {

    private val _adminNewsList = MutableStateFlow<List<CardItemAdmin>>(emptyList())
    val adminNewsList: StateFlow<List<CardItemAdmin>> = _adminNewsList.asStateFlow()



    fun getAllReports() {
        viewModelScope.launch {
            val jwtToken = dataStoreManager.jwtToken.firstOrNull() ?: ""
            val response = repository.getAllReports(jwtToken)

            if (response?.success == true) {
                val reports = response.data ?: emptyList()
                val mappedReports = reports.map { report ->
                    CardItemAdmin(
                        cardId = report.cardID ?: "Unknown",
                        title = report.title ?: "Untitled Report",
                        description = report.description ?: "No description available",
                        date = report.date ?: "Unknown Date",
                        location = report.address ?: "Unknown Location",
                        locationUrl = report.location?.joinToString(",") ?: "",
                        imageUrl = "https://example.com/default_image.jpg", // Replace with actual logic if available
                        vehicleNo = report.vehicleNo ?: "N/A",
                        address = report.address ?: "N/A",
                        reporterId = report.reporterId?.name ?: "Unknown Reporter",
                        trustRate = report.trustRate,
                        damageRate = report.damageRate,
                        status = report.status ?: "pending",
                        accentColor = Color.Blue, // Customize as needed
                        imagePlaceholderColor = Color.LightGray
                    )
                }
                _adminNewsList.value = mappedReports
            } else {
                println("Failed to fetch reports: ${response?.message ?: "Unknown error"}")
            }
        }
    }

    init {
        getAllReports()
    }


    fun updateItem(
        cardId: String,
        title: String,
        description: String,
        damageRate: Int,
        reporterId: String,
        vehicleNo: String,
        location: String
    ) {
        val list = adminNewsList.value.toMutableList()
        val index = list.indexOfFirst { it.cardId == cardId }
        if (index != -1) {
            val updatedItem = list[index].copy(
                title = title,
                description = description,
                damageRate = damageRate,
                reporterId = reporterId,
                vehicleNo = vehicleNo,
                location = location
            )
            list[index] = updatedItem
            _adminNewsList.value = list
        }
    }

    fun deleteItem(cardId: String) {
        val list = adminNewsList.value.toMutableList()
        val filteredList = list.filter { it.cardId != cardId }
        _adminNewsList.value = filteredList
    }


}

