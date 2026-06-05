package com.example.monitoreoapp.data.model

import com.google.gson.annotations.SerializedName

data class LoginRequest (
    val email : String,
    val password: String
)

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val data: AuthData?
)
data class AuthData(
    @SerializedName("token_value") val tokenValue: String,
    @SerializedName("token_expired") val tokenExpired: String
)

data class ForgotRequest(
    val email: String
)

data class ForgotResponse(
    val success: Boolean,
    val message: String
)
data class Vehicle(
    val id: Int,
    val plate: String,
    val speed: String,
    val latitude: Double,
    val longitude: Double,
    val angle: Float,
    val status: String
)
data class VehicleResponse(
    val success: Boolean,
    val message: String,
    val data: List<Vehicle>
)

data class NotificationResponse(
    val success: Boolean,
    val message: String,
    val data: List<Notification>
)

data class Notification(
    val id: Int,
    @SerializedName("invoice_number") val invoiceNumber: String,
    val status: String,
    @SerializedName("created_at") val createdAt: String
)