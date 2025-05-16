package com.example.myapplication.network

import com.example.myapplication.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("user")
    fun registerUser(@Body user: User): Call<Map<String, Boolean>>
}
