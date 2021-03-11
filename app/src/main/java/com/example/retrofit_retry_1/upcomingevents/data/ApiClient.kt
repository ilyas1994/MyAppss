package com.example.retrofit_retry_1.upcomingevents.data

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.GET

interface ApiClient {
    @GET("v2/competitions/")
    fun upcomingEvents(): Call<ResponseBody>
}