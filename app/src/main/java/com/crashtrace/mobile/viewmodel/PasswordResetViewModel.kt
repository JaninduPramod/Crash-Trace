package com.crashtrace.mobile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crashtrace.mobile.data.repository.PasswordResetRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PasswordResetViewModel (private val repository: PasswordResetRepository) : ViewModel(){

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> get() = _email

    private val _message = MutableStateFlow("")
    val message: StateFlow<String> get() = _message

    private val _success = MutableStateFlow(false)
    val success: StateFlow<Boolean> get() = _success

    private val _otpSuccess = MutableStateFlow(false)
    val otpSuccess: StateFlow<Boolean> get() = _otpSuccess

    private val _newPasswordSuccess = MutableStateFlow(false)
    val newPasswordSuccess: StateFlow<Boolean> get() = _newPasswordSuccess

    fun resetState() {
        _email.value = ""
        _message.value = ""
        _success.value = false
    }

    fun setEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun executeResetSendOtp() {
        viewModelScope.launch {
            val response = repository.resetSendOtp(_email.value)
            _message.value = response?.message ?: ""

            if(response?.success == true) {
                println(response?.message)
                _success.value = true

            } else {
                println(response?.message)
                _success.value = false
            }

        }
    }



    fun submitOtpCode(otp: String) {
        viewModelScope.launch {
            val response = repository.verifyOtpCode(_email.value, otp)

            println("Message from API: ${response?.message}")
            println("success: ${response?.success}");

            _message.value = response?.message ?: ""

            _otpSuccess.value = response?.success ?: false
        }
    }

    fun resetPassword(password: String, newPassword: String) {
        viewModelScope.launch {
            val response = repository.resetPassword(_email.value, password, newPassword)

            println("Message from API: ${response?.message}")
            println("success: ${response?.success}");

            _message.value = response?.message ?: ""
            _newPasswordSuccess.value = response?.success ?: false
        }
    }



}