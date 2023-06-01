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
            fcmToken?.let { fcmToken ->
                if (chat.trim() == "/초기화") {
                    chatUseCase.initContext(fcmToken)
                        .onSuccess {
                            Log.e("test", "context 초기화 요청 성공")
                            removeAllChatLog()
                        }
                        .onFailure {
                            Log.e("test", "context 초기화 요청 실패 : ${it.message.toString()}")
                            chatFailMessage.value = it.message.toString()
                        }
                } /* else if (chat.trim() == "/출처") {
                    chatUseCase.getSource(fcmToken)
                        .onSuccess {
                            Log.e("test", "출처 요청 성공")
                            displayUserChat(chat)
                        }
                        .onFailure {
                            Log.e("test", "출처 요청 실패 : ${it.message.toString()}")
                            chatFailMessage.value = it.message.toString()
                        }
                }*/ else {
                    chatUseCase(chat, fcmToken)
                        .onSuccess {
                            Log.e("test", "chat 보내기 성공")
                            displayUserChat(chat)
                        }
                        .onFailure {
                            Log.e("test", "chat 보내기 실패 : ${it.message.toString()}")
                            chatFailMessage.value = it.message.toString()
                        }
                }
            }
        }
        isLoading.value = false
    }

    fun displayUserChat(chat: String) {
        insertChatRoom(true, chat)
        with(mutableListOf<Chat>()) {
            addAll(chatting.value)
            add(Chat.BySelf(chat))
            chatting.value = this
        }
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

    fun initChatFailMessage() {
        chatFailMessage.value = ""
    }

    fun removeAllChatLog() {
        viewModelScope.launch {
            chatRoomUseCase.removeAllChat()
                .onSuccess {
                    Log.e("test", "채팅 기록 삭제 완료")
                    maxId = 0
                    chatting.value = listOf()
                }
                .onFailure {
                    Log.e("test", "채팅기록 삭제 실패 : ${it.message.toString()}")
                }
        }
    }
}