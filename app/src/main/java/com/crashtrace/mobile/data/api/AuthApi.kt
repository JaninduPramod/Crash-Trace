package com.crashtrace.mobile.data.api

import com.crashtrace.mobile.data.entity.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("/api/auth/register")
    suspend fun registerUser(@Body request: RegisterRequest): Response<String>
}
