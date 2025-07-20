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
    fun submitReport(): Flow<ApiResponse<ReportResponse>?> {
        return flow {
            // Retrieve the token from DataStoreManager
            val jwtToken = dataStoreManager.jwtToken.firstOrNull() ?: ""
               val response =  repository.submitReport(
                    _vehicleNumber.value,
                    _description.value,
                    _location.value,
                    _address.value,
                    _date.value,
                   jwtToken
                )
            if(response?.success == true) {
                _vehicleNumber.value = ""
                _description.value = ""
                _location.value = ""
                _address.value = ""
                _date.value = ""
            }
            emit(response)

        }.flowOn(Dispatchers.IO)


    }

}