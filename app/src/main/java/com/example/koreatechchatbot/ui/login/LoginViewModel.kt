package com.example.koreatechchatbot.ui.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    val isLoading = mutableStateOf(false)
    val loginSuccess = mutableStateOf(false)
    val loginFailMessage = mutableStateOf("")
    fun login(id: String, password: String) {
        viewModelScope.launch {
            isLoading.value = true
            loginUseCase(id, password)
                .onSuccess {
                    loginSuccess.value = true
                }
                .onFailure {
                    loginSuccess.value = false
                    loginFailMessage.value = it.message.toString()
                }
            isLoading.value = false
        }
    }

    fun initFailMessage() {
        loginFailMessage.value = ""
    }
}