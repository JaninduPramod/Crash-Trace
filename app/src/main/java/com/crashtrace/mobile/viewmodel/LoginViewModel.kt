package com.crashtrace.mobile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.crashtrace.mobile.data.repository.LoginRepository
import kotlinx.coroutines.launch
class LoginViewModel (private val repository: LoginRepository) : ViewModel(){

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> get() = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> get() = _password

    fun updateEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }


    fun executeUserLogin() {
        viewModelScope.launch {
            repository.userLogin(_email.value, _password.value)
        }
    }

}