package com.crashtrace.mobile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crashtrace.mobile.data.repository.ReportRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ReportViewModel(private val repository: ReportRepository): ViewModel() {

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
    fun submitReport() {
        viewModelScope.launch {
            try {
                repository.submitReport(
                    _vehicleNumber.value,
                    _description.value,
                    _location.value,
                    _address.value,
                    _date.value
                )
            } catch (e: Exception) {
                // Handle the exception, e.g., log it or show a message to the user
                println("Error submitting report: ${e.message}")
            }
        }


    }

}