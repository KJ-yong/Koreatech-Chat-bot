package com.example.koreatechchatbot.ui.chat

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.ChatUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatUseCase: ChatUseCase
): ViewModel() {
    val isLoading = mutableStateOf(false)
    val chatting = mutableStateOf(listOf<Chat>())
    val chatFailMessage = mutableStateOf("")

    fun chat(chat: String, chatScroll: (Int) -> Unit) {
        isLoading.value = true
        viewModelScope.launch {
            chatUseCase(chat)
                .onSuccess {
                    with(mutableListOf<Chat>()) {
                        addAll(chatting.value)
                        add(Chat.BySelf(chat))
                        add(Chat.ByBotOnlyText(it))
                        chatting.value = this
                        chatScroll(chatting.value.size)
                    }
                }
                .onFailure {
                    chatFailMessage.value = it.message.toString()
                }
        }
        isLoading.value = false
    }
}