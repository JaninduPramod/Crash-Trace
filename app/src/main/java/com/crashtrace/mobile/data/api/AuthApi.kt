package com.crashtrace.mobile.data.api

import com.crashtrace.mobile.data.entity.RegisterData
import com.crashtrace.mobile.data.entity.UserData
import com.crashtrace.mobile.data.entity.LoginResponse
import com.crashtrace.mobile.data.entity.LoginRequest
import com.crashtrace.mobile.data.entity.ApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("/api/auth/register")
    suspend fun registerUser(@Body request: RegisterData): Response<ApiResponse<UserData>>

    @POST("/api/auth/login")
    suspend fun loginUser(@Body request: LoginRequest): Response<ApiResponse<LoginResponse>>
}
