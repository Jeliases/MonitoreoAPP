package com.example.monitoreoapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monitoreoapp.data.model.LoginRequest
import com.example.monitoreoapp.data.network.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.math.log


sealed  class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val token: String) : LoginState()
    data class Error(val message: String) : LoginState()
}
class LoginViewModel: ViewModel(){

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)

    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    fun login(email: String, clave: String){

        if (email.isBlank() || clave.isBlank()){
            _loginState.value = LoginState.Error("Porfavor completa todos los campos")
            return
        }

        _loginState.value = LoginState.Loading

        viewModelScope.launch {

            try {
                val request = LoginRequest(email = email, password = clave)
                val response = ApiClient.retrofitService.login((request))

                if (response.isSuccessful && response.body() != null){
                    val loginResponse = response.body()!!

                    if(loginResponse.succes && loginResponse.data != null){
                        _loginState.value = LoginState.Success(loginResponse.data.tokenValue)
                    } else{
                        _loginState.value = LoginState.Error(loginResponse.message)
                    }
                } else{

                    _loginState.value = LoginState.Error("Errore en el Servidor: ${response.code()}")
                }
            } catch (e: Exception){
                _loginState.value = LoginState.Error("Error de conexion. Verifica tu conexion")
            }

        }

    }

    fun resetState(){
        _loginState.value= LoginState.Idle
    }

}