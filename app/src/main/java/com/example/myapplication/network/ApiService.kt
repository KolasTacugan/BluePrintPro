package com.example.myapplication.network

import com.example.myapplication.model.Architect
import com.example.myapplication.model.LoginArchi
import com.example.myapplication.model.LoginUser
import com.example.myapplication.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/user")
    fun registerUser(@Body user: User): Call<Map<String, Boolean>>
    @POST("/login")
    fun loginUser(@Body user: LoginUser): Call<Map<String, Boolean>>
    @POST("/architect")
    fun registerArchitect(@Body user: Architect): Call<Map<String, Boolean>>
    @POST("/archiLogin")
    fun loginArchi(@Body user: LoginArchi): Call<Map<String, Boolean>>
}
