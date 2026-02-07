package com.crashtrace.mobile.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }).build()

    val api: AuthApi by lazy {
        Retrofit.Builder()
            .baseUrl("http://13.215.162.106:3000")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(AuthApi::class.java)
    }
    val reportApi: ReportApi by lazy {
        Retrofit.Builder()
            .baseUrl("http://13.215.162.106:3000")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ReportApi::class.java)
    }
}

