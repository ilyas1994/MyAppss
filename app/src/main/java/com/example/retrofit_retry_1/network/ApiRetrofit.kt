package com.example.retrofit_retry_1.network

import com.example.retrofit_retry_1.upcomingevents.data.ApiClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

private const val API_BASE_URL = "http://api.football-data.org/"

val apiRetrofit: Retrofit = Retrofit.Builder()
    .baseUrl(API_BASE_URL)
    .addConverterFactory(JacksonConverterFactory.create())
    .build()

val apiClient: ApiClient = apiRetrofit.create(ApiClient::class.java)