package com.crashtrace.mobile.viewmodel

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.crashtrace.mobile.data.repository.LoginRepository
import com.crashtrace.mobile.data.Utils.DataStoreManager
import com.crashtrace.mobile.data.entity.ApiResponse
import com.crashtrace.mobile.data.entity.LoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
class LoginViewModel (private val repository: LoginRepository,private val dataStoreManager: DataStoreManager) : ViewModel(){

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> get() = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> get() = _password

    private val _success = MutableStateFlow(false)
    val success: StateFlow<Boolean> get() = _success

    private val _message = MutableStateFlow("")
    val message: StateFlow<String> get() = _message



    // function to reset all fields
    fun resetFields() {
        _email.value = ""
        _password.value = ""
        _success.value = false
        _message.value = ""
    }



    fun setEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun setPassword(newPassword: String) {
        _password.value = newPassword
    }


    fun executeUserLogin(): Flow<ApiResponse<LoginResponse>?> {
        return flow {
            val response = repository.userLogin(_email.value, _password.value)
            if (response?.success == true) {
                val jwtToken = response.data?.token ?: ""
                val role = response.data?.role ?: ""
                dataStoreManager.saveUserData(jwtToken, role)
                _email.value = ""
                _password.value = ""
            }
            emit(response)
        }.flowOn(Dispatchers.IO)
    }


    // function to get jwt token and print it
    fun getJwtToken() {
        viewModelScope.launch {
            dataStoreManager.jwtToken.collect { token ->
                println("JWT Token: $token")
            }
        }
    }

    // function to remove jwt token
    fun removeJwtToken() {
        viewModelScope.launch {
            dataStoreManager.removeJwtToken()
        }
    }
}

