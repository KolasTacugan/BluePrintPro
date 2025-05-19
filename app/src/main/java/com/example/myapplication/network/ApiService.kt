package com.example.myapplication.network

import com.example.myapplication.model.Architect
import com.example.myapplication.model.LoginArchi
import com.example.myapplication.model.LoginResponse
import com.example.myapplication.model.LoginUser
import com.example.myapplication.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("/user")
    fun registerUser(@Body user: User): Call<Map<String, Boolean>>
    @POST("/login")
    fun loginUser(@Body user: LoginUser): Call<LoginResponse>
    @POST("/architect")
    fun registerArchitect(@Body user: Architect): Call<Map<String, Boolean>>
    @POST("/archiLogin")
    fun loginArchi(@Body user: LoginArchi): Call<LoginResponse>

    @GET("/architects")
    suspend fun getArchitects(
        @Query("designStyles") designStyles: String? = null,
        @Query("serviceLocation") serviceLocation: String? = null,
        @Query("laborCost") laborCost: String? = null,
        @Query("specializations") specializations: String? = null
    ): List<Architect>





}
