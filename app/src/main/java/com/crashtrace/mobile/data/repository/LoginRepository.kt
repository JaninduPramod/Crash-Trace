package com.crashtrace.mobile.data.repository

class LoginRepository {
    fun userLogin(email: String, password: String) {
        // Simulate login with API call
        println("Repository received:Email=$email, Password=$password")
    }
}
