package com.example.monitoreoapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monitoreoapp.data.model.Notification
import com.example.monitoreoapp.data.network.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class NotifyState {
    object Loading : NotifyState()
    data class Success(val notifications: List<Notification>) : NotifyState()
    data class Error(val message: String) : NotifyState()
}

class NotifyViewModel : ViewModel() {
    private val _notifyState = MutableStateFlow<NotifyState>(NotifyState.Loading)
    val notifyState: StateFlow<NotifyState> = _notifyState.asStateFlow()

    init {
        fetchNotifications()
    }

    private fun fetchNotifications() {
        viewModelScope.launch {
            try {
                val response = ApiClient.retrofitService.getNotifications()
                if (response.isSuccessful && response.body() != null) {
                    val body = response.body()!!
                    if (body.success) {
                        _notifyState.value = NotifyState.Success(body.data)
                    } else {
                        _notifyState.value = NotifyState.Error(body.message)
                    }
                } else {
                    _notifyState.value = NotifyState.Error("Error")
                }
            } catch (e: Exception) {
                _notifyState.value = NotifyState.Error("Error")
            }
        }
    }
}