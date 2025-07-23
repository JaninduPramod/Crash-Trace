package com.crashtrace.mobile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crashtrace.mobile.data.entity.ApiResponse
import com.crashtrace.mobile.data.entity.EmailVerificationResponse
import com.crashtrace.mobile.data.repository.PasswordResetRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
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

    fun executeResetSendOtp(): Flow<ApiResponse<EmailVerificationResponse>?> {
        return flow{
            val response = repository.resetSendOtp(_email.value)
            emit(response)
        }.flowOn(Dispatchers.IO)
    }


    fun submitOtpCode(otp: String):Flow<ApiResponse<EmailVerificationResponse>?> {
        return flow {
            println("Email"+_email.value)
            val response = repository.verifyOtpCode(_email.value, otp)
            emit(response)
        }.flowOn(Dispatchers.IO)
    }

    fun resetPassword(password: String, newPassword: String):Flow<ApiResponse<EmailVerificationResponse>?> {
        return flow {
            val response = repository.resetPassword(_email.value, password, newPassword)
            emit(response)
        }.flowOn(Dispatchers.IO)
    }



}