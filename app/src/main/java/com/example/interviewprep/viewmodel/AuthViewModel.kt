package com.example.interviewprep.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interviewprep.utils.Validators
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val _authState = MutableStateFlow<AuthResult>(AuthResult.Idle)
    val authState: StateFlow<AuthResult> = _authState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthResult.Loading
            
            // Validate input
            val emailError = Validators.getEmailError(email)
            val passwordError = Validators.getPasswordError(password)
            
            when {
                emailError != null -> {
                    _authState.value = AuthResult.Error(emailError)
                    return@launch
                }
                passwordError != null -> {
                    _authState.value = AuthResult.Error(passwordError)
                    return@launch
                }
            }
            
            // TODO: Replace with FirebaseService.signIn(email, password)
            kotlinx.coroutines.delay(1000) // Simulate delay
            _authState.value = AuthResult.Success
        }
    }

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthResult.Loading
            
            // Validate input
            val emailError = Validators.getEmailError(email)
            val passwordError = Validators.getPasswordError(password)
            
            when {
                emailError != null -> {
                    _authState.value = AuthResult.Error(emailError)
                    return@launch
                }
                passwordError != null -> {
                    _authState.value = AuthResult.Error(passwordError)
                    return@launch
                }
            }
            
            // TODO: Replace with FirebaseService.signUp(email, password)
            kotlinx.coroutines.delay(1000) // Simulate delay
            _authState.value = AuthResult.Success
        }
    }

    fun resetState() {
        _authState.value = AuthResult.Idle
    }
}

sealed class AuthResult {
    object Idle : AuthResult()
    object Loading : AuthResult()
    object Success : AuthResult()
    data class Error(val message: String?) : AuthResult()
} 