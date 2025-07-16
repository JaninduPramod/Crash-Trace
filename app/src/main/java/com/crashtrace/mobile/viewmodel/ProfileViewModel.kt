package com.crashtrace.mobile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crashtrace.mobile.data.Utils.DataStoreManager

import com.crashtrace.mobile.data.repository.ProfileRepository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull

import kotlinx.coroutines.launch

class ProfileViewModel (private val repository: ProfileRepository, private val dataStoreManager: DataStoreManager) : ViewModel(){

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> get() = _name

    private val _nic = MutableStateFlow("")
    val nic: StateFlow<String> get() = _nic

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> get() = _email

    private val _role = MutableStateFlow("")
    val role: StateFlow<String> get() = _role


    fun setName(newName: String) {
        _name.value = newName
    }

    fun setNic(newNic: String) {
        _nic.value = newNic
    }

    fun setEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun setRole(newRole: String) {
        _role.value = newRole
    }



    fun executeUserProfile() {

        viewModelScope.launch {
            val jwtToken = dataStoreManager.jwtToken.firstOrNull() ?: ""

            val response = repository.userProfile(jwtToken)
            if(response?.success == true)
            {

                setName(response.data?.name ?: "")
                setNic(response.data?.nic ?: "")
                setEmail(response.data?.email ?: "")
                setRole(response.data?.role ?: "")

                println("Email : "+_email.value)
                println("Name : "+_name.value)
                println("NIC : "+_nic.value)
                println("Role : "+_role.value)

            }
            else{
                println(response?.message)
            }


        }
    }

}