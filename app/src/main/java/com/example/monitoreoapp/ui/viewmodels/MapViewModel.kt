package com.example.monitoreoapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monitoreoapp.data.model.Vehicle
import com.example.monitoreoapp.data.network.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class MapState {
    object Loading : MapState()
    data class Success(val vehicles: List<Vehicle>) : MapState()
    data class Error(val message: String) : MapState()
}

class MapViewModel : ViewModel() {
    private val _mapState = MutableStateFlow<MapState>(MapState.Loading)
    val mapState: StateFlow<MapState> = _mapState.asStateFlow()

    init {

        fetchVehicles()
    }

    private fun fetchVehicles() {
        viewModelScope.launch {
            try {
                val response = ApiClient.retrofitService.getVehicles()
                if (response.isSuccessful && response.body() != null) {
                    val body = response.body()!!
                    if (body.success) {
                        _mapState.value = MapState.Success(body.data)
                    } else {
                        _mapState.value = MapState.Error(body.message)
                    }
                } else {
                    _mapState.value = MapState.Error("Error al cargar los vehículos.")
                }
            } catch (e: Exception) {
                _mapState.value = MapState.Error("Sin conexión a internet.")
            }
        }
    }
}