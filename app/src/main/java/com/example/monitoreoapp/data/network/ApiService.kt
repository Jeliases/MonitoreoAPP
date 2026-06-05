package com.example.monitoreoapp.data.network


import com.example.monitoreoapp.data.model.ForgotRequest
import com.example.monitoreoapp.data.model.ForgotResponse
import com.example.monitoreoapp.data.model.LoginRequest
import com.example.monitoreoapp.data.model.LoginResponse
import com.example.monitoreoapp.data.model.VehicleResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("auth/forgot")
    suspend fun forgotPassword(@Body request: ForgotRequest): Response<ForgotResponse>

    @GET("vehicles")
    suspend fun getVehicles(): Response<VehicleResponse>
}