package com.example.koreatechchatbot.ui.chat

import android.os.Bundle
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
import com.example.koreatechchatbot.ui.theme.KoreatechChatBotTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatBotActivity : ComponentActivity() {
    private val chatViewModel by viewModels<ChatViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
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
                    ChatScreen(chatViewModel.chatting.value) { string, onScroll ->
                        chatViewModel.chat(string, onScroll)
                    }
                }
            }
        }
    }
}