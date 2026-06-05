package com.example.monitoreoapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monitoreoapp.data.model.ForgotRequest
import com.example.monitoreoapp.data.network.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class ForgotState {
    object Idle : ForgotState()
    object Loading : ForgotState()
    data class Success(val message: String) : ForgotState()
    data class Error(val message: String) : ForgotState()
}

class ForgotViewModel : ViewModel() {
    private val _forgotState = MutableStateFlow<ForgotState>(ForgotState.Idle)
    val forgotState: StateFlow<ForgotState> = _forgotState.asStateFlow()

    fun recoverPassword(email: String) {
        if (email.isBlank()) {
            _forgotState.value = ForgotState.Error("Por favor, ingresa tu usuario.")
            return
        }

        _forgotState.value = ForgotState.Loading

        viewModelScope.launch {
            try {
                val response = ApiClient.retrofitService.forgotPassword(ForgotRequest(email))
                if (response.isSuccessful && response.body() != null) {
                    val body = response.body()!!
                    if (body.success) {
                        _forgotState.value = ForgotState.Success(body.message)
                    } else {
                        _forgotState.value = ForgotState.Error(body.message)
                    }
                } else {
                    _forgotState.value = ForgotState.Error("Error al procesar la solicitud.")
                }
            } catch (e: Exception) {
                _forgotState.value = ForgotState.Error("Error de conexión. Verifica tu internet.")
            }
        }
    }

    fun resetState() {
        _forgotState.value = ForgotState.Idle
    }
}