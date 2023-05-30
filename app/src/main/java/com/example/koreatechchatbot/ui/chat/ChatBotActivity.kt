package com.example.koreatechchatbot.ui.chat

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.koreatechchatbot.ui.theme.KoreatechChatBotTheme
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatBotActivity : ComponentActivity() {
    private val chatViewModel by viewModels<ChatViewModel>()
    val chatBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val message = intent.getStringExtra("message")
            Log.e("test", "액티비티로 메세지 수신됨 : $message")
            message?.let { chatViewModel.getMessage(message) }
        }
    }
    val fcmTokenReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val token = intent.getStringExtra("fcmToken")
            Log.e("test_token", "on Receive : ${token ?: "token is null"}")
            token?.let { chatViewModel.saveFcmToken(token) }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        registerReceiver(
            chatBroadcastReceiver, IntentFilter("Chat Receiver")
        )
        registerReceiver(
            fcmTokenReceiver, IntentFilter("Token Receiver")
        )
        setContent {
            KoreatechChatBotTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .windowInsetsPadding(
                            WindowInsets.systemBars.only(
                                WindowInsetsSides.Vertical
                            )
                        ),
                    color = MaterialTheme.colors.background
                ) {
                    FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                        if (!task.isSuccessful) {
                            Log.e("test_token", "Fetching FCM registration token failed", task.exception)
                            return@OnCompleteListener
                        }
                        val token = task.result
                        Log.e("test_token", "get Token : $token")
                        chatViewModel.saveFcmToken(token)
                    })
                    ChatScreen(chatViewModel.chatting.value) { string ->
                        chatViewModel.chat(string)
                    }
                }
            }
        }
    }
}