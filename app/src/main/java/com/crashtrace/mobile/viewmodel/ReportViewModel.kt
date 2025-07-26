package com.crashtrace.mobile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crashtrace.mobile.data.Utils.DataStoreManager
import com.crashtrace.mobile.data.entity.ApiResponse
import com.crashtrace.mobile.data.entity.ReportResponse
import com.crashtrace.mobile.data.repository.ReportRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class ReportViewModel(private val repository: ReportRepository,private val dataStoreManager: DataStoreManager): ViewModel() {

    private val _vehicleNumber = MutableStateFlow("")
    val vehicleNumber: StateFlow<String> get() = _vehicleNumber

    private val _description = MutableStateFlow("")
    val description: StateFlow<String> get() = _description

    private val _location = MutableStateFlow("")
    val location: StateFlow<String> get() = _location

    private val _address = MutableStateFlow("")
    val address: StateFlow<String> get() = _address

    private val _date = MutableStateFlow("")
    val date: StateFlow<String> get() = _date

    private val _reporter = MutableStateFlow("")
    val reporter: StateFlow<String> get() = _reporter

    private val _title = MutableStateFlow("")
    val title: StateFlow<String> get() = _title

    private val _damageRate = MutableStateFlow(0)
    val damageRate: StateFlow<Int> get() = _damageRate

    private val _lat = MutableStateFlow(0.0)
    val lat: StateFlow<Double> get() = _lat

    private val _lng = MutableStateFlow(0.0)
    val lng: StateFlow<Double> get() = _lng

    private val _cardId = MutableStateFlow<String?>(null)
    val cardId: StateFlow<String?> get() = _cardId

    private val _imageUrl = MutableStateFlow<String?>(null)
    val imageUrl: StateFlow<String?> get() = _imageUrl

    private val _reportCount = MutableStateFlow<Int?>(null)
    val reportCount: StateFlow<Int?> get() = _reportCount

    private val _userCount = MutableStateFlow<Int?>(null)
    val userCount: StateFlow<Int?> get() = _userCount

    private val _approvedPercentage = MutableStateFlow<Int?>(null)
    val approvedPercentage: StateFlow<Int?> get() = _approvedPercentage


    fun setVehicleNumber(newVehicleNumber: String) {
        _vehicleNumber.value = newVehicleNumber
    }
    fun setDescription(newDescription: String) {
        _description.value = newDescription
    }
    fun setLocation(newLocation: String) {
        _location.value = newLocation
    }
    fun setAddress(newAddress: String) {
        _address.value = newAddress
    }
    fun setDate(newDate: String) {
        _date.value = newDate
    }

    fun setCardId(id: String?) {
        _cardId.value = id
    }

    fun setImageUrl(url: String?) {
        _imageUrl.value = url
    }

    // function to reset all fields
    fun resetFields() {
        _vehicleNumber.value = ""
        _description.value = ""
        _location.value = ""
        _address.value = ""
        _date.value = ""
        _title.value = ""
        _damageRate.value = 0
        _reporter.value = ""
        _cardId.value = null
        _imageUrl.value = null
        _reportCount.value = 0
        _userCount.value = 0
        _approvedPercentage.value = 0
    }


    fun submitReport(): Flow<ApiResponse<ReportResponse>?> {
        return flow {

            val jwtToken = dataStoreManager.jwtToken.firstOrNull() ?: ""
               val response =  repository.submitReport(
                    _vehicleNumber.value,
                    _description.value,
                    _location.value,
                    _address.value,
                    _date.value,
                    jwtToken,
                    _imageUrl.value,
                    _cardId.value
                )
            if(response?.success == true) {
                resetFields()
            }
            emit(response)

        }.flowOn(Dispatchers.IO)

    }

    fun searchReport() {
        viewModelScope.launch {
            val jwtToken = dataStoreManager.jwtToken.firstOrNull() ?: ""
            val response = repository.searchReport(jwtToken, _vehicleNumber.value)

            println("here is the response:"+response?.data?.location?.get(1))

            if (response?.success == true) {
                _date.value = response.data?.date ?: ""
                _location.value = response.data?.location.toString()
                _address.value = response.data?.address ?: ""
                _description.value = response.data?.description ?: ""
                _reporter.value = response.data?.reporterId?.name ?: ""
                _title.value = response.data?.title ?: ""
                _damageRate.value = response.data?.damageRate ?: 0

                _lat.value = response.data?.location?.get(0)?.toDoubleOrNull() ?: 0.0
                _lng.value = response.data?.location?.get(1)?.toDoubleOrNull() ?: 0.0

                _vehicleNumber.value = ""

            } else {
                resetFields()
            }
        }
    }


    fun getHomeStat() {
        viewModelScope.launch {
            val jwtToken = dataStoreManager.jwtToken.firstOrNull() ?: ""
            val response = repository.homeStats(jwtToken)

            println("here is the response:"+response?.data)

            if (response?.success == true) {
                _reportCount.value = response.data?.totalReports ?: 0
                _userCount.value = response.data?.totalUsers ?: 0
                _approvedPercentage.value = response.data?.approvedPercentage ?: 0

            } else {
                resetFields()
            }
        }
    }


}
