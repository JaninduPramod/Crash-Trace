package com.crashtrace.mobile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crashtrace.mobile.data.repository.SignUpRepository
import kotlinx.coroutines.launch

class SignUpViewModel(private val repository: SignUpRepository) : ViewModel() {

    fun submitSignUpData(name: String, nic: String, email: String, password: String) {
        viewModelScope.launch {
            repository.saveSignUpData(name, nic, email, password)
        }
    }
}