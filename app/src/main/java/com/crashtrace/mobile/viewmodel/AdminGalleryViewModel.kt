package com.crashtrace.mobile.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crashtrace.mobile.data.Utils.DataStoreManager
import com.crashtrace.mobile.data.entity.ApiResponse
import com.crashtrace.mobile.data.entity.ReportResponse
import com.crashtrace.mobile.data.repository.ReportRepository
import com.crashtrace.mobile.ui.components.CardItem
import com.crashtrace.mobile.ui.components.CardItemAdmin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import org.koin.core.KoinApplication.Companion.init

class AdminGalleryViewModel(private val repository: ReportRepository, private val dataStoreManager: DataStoreManager) : ViewModel() {

    private val _adminNewsList = MutableStateFlow<List<CardItemAdmin>>(emptyList())
    val adminNewsList: StateFlow<List<CardItemAdmin>> = _adminNewsList.asStateFlow()

    private val _cardId= MutableStateFlow("")
    val cardId: StateFlow<String> get() = _cardId

    private val _title = MutableStateFlow("")
    val title: StateFlow<String> get() = _title

    private val _description = MutableStateFlow("")
    val description: StateFlow<String> get() = _description

    private val _damageRate = MutableStateFlow(0)
    val damageRate: StateFlow<Int> get() = _damageRate

    private val _reporterId = MutableStateFlow("")
    val reporterId: StateFlow<String> get() = _reporterId

    private val _vehicleNo = MutableStateFlow("")
    val vehicleNo: StateFlow<String> get() = _vehicleNo

    private val _location = MutableStateFlow("")
    val location: StateFlow<String> get() = _location

    fun setCardId(newCardId: String) {
        _cardId.value = newCardId
    }
    fun setTitle(newTitle: String) {
        _title.value = newTitle
    }
    fun setDescription(newDescription: String) {
        _description.value = newDescription
    }
    fun setDamageRate(newDamageRate: Int) {
        _damageRate.value = newDamageRate
    }
    fun setReporterId(newReporterId: String) {
        _reporterId.value = newReporterId
    }
    fun setVehicleNo(newVehicleNo: String) {
        _vehicleNo.value = newVehicleNo
    }
    fun setLocation(newLocation: String) {
        _location.value = newLocation
    }

    // function to reset all fields
    fun resetFields() {
        _cardId.value = ""
        _title.value = ""
        _description.value = ""
        _damageRate.value = 0
        _reporterId.value = ""
        _vehicleNo.value = ""
        _location.value = ""
    }


    fun saveReport() {
        viewModelScope.launch {
            val jwtToken = dataStoreManager.jwtToken.firstOrNull() ?: ""

            val response = repository.editReport(
                jwtToken,
                _cardId.value,
                _title.value,
                _description.value,
                _damageRate.value,
                _vehicleNo.value,
                _location.value
            )

            if (response?.success == true) {
                println(response.message)

                // Update the adminNewsList with the edited report
                val updatedList = _adminNewsList.value.map { report ->
                    if (report.cardId == _cardId.value) {
                        report.copy(
                            title = _title.value,
                            description = _description.value,
                            damageRate = _damageRate.value,
                            vehicleNo = _vehicleNo.value,
                            location = _location.value
                        )
                    } else {
                        report
                    }
                }
                _adminNewsList.value = updatedList

                // Optionally reset fields if needed
                resetFields()
            } else {
                println("Failed to update report: ${response?.message ?: "Unknown error"}")
            }
        }
    }


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


    fun chooseOptionReport(cardId: String,option:String) {
        viewModelScope.launch {
            val jwtToken = dataStoreManager.jwtToken.firstOrNull() ?: ""
            val response = repository.optionReport(jwtToken,cardId, option)
            println(response)

        }
    }




    fun deleteItem(cardId: String) {
        val list = adminNewsList.value.toMutableList()
        val filteredList = list.filter { it.cardId != cardId }
        _adminNewsList.value = filteredList
    }


}

