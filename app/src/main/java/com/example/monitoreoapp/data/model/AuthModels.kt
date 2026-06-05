package com.example.monitoreoapp.data.model

import com.google.gson.annotations.SerializedName

data class LoginRequest (
    val email : String,
    val password: String
)

data class LoginResponse(
    val succes: Boolean,
    val message: String,
    val data: AuthData?

)

data class AuthData(
    @SerializedName("token_value") val tokenValue: String,
    @SerializedName("token_expired") val tokenExpired: String
)
