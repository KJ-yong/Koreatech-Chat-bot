package com.example.koreatechchatbot.ui.chat

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.ChatRoomUseCase
import com.example.domain.usecase.ChatUseCase
import com.example.domain.usecase.FcmTokenUseCase
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatUseCase: ChatUseCase,
    private val fcmTokenUseCase: FcmTokenUseCase,
    private val chatRoomUseCase: ChatRoomUseCase
): ViewModel() {
    val isLoading = mutableStateOf(false)
    val chatting = mutableStateOf(listOf<Chat>())
    val chatFailMessage = mutableStateOf("")
    var maxId: Int = 0

    init {
        viewModelScope.launch {
            val chatList = chatRoomUseCase.getAllChat()
            if(chatList.isNotEmpty()) maxId = chatList.last().id
            val list = mutableListOf<Chat>()
            chatList.forEach {
                if (it.isUser) list.add(Chat.BySelf(it.text))
                else list.add(Chat.ByBotOnlyText(it.text))
            }
            chatting.value = list
        }
    }

    fun chat(chat: String) {
        isLoading.value = true
        viewModelScope.launch {
            var fcmToken = fcmTokenUseCase.getToken()
            if (fcmToken == null) {
                FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.e("test_token", "Fetching FCM registration token failed", task.exception)
                        chatFailMessage.value = "오류가 발생했습니다."
                        return@OnCompleteListener
                    }
                    fcmToken = task.result

                })
            }
            Log.e("test_token", "in viewModel $fcmToken")
            fcmToken?.let {
                chatUseCase(chat, it)
                    .onSuccess {
                        Log.e("test", "chat 보내기 성공")
                        insertChatRoom(true, chat)
                        with(mutableListOf<Chat>()) {
                            addAll(chatting.value)
                            add(Chat.BySelf(chat))
                            chatting.value = this
                        }
                    }
                    .onFailure {
                        Log.e("test", "chat 보내기 실패 : ${it.message.toString()}")
                        chatFailMessage.value = it.message.toString()
                    }
            }
        }
        isLoading.value = false
    }

    fun getMessage(message: String) {
        val chat = Chat.ByBotOnlyText(message)
        insertChatRoom(false, message)
        with(mutableListOf<Chat>()) {
            addAll(chatting.value)
            add(chat)
            chatting.value = this
        }
    }
    fun saveFcmToken(token: String) {
        viewModelScope.launch {
            fcmTokenUseCase.saveToken(token)
        }
    }

    fun insertChatRoom(isUser: Boolean, chat: String) {
        viewModelScope.launch {
            chatRoomUseCase.insertChat(com.example.domain.model.Chat(++maxId, isUser, chat))
        }
    }
}