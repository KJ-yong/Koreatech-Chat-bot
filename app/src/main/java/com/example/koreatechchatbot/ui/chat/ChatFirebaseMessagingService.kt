package com.example.koreatechchatbot.ui.chat

import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatFirebaseMessagingService (
) : FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        Log.e("test", "메세지 수신됨 ${message}")
        if (message.data.isNotEmpty()) {
            Log.e("test", "메세지 비어있지 않음 : ${message.data}")
            message.data["message"]?.let { sendMessageToBroadcastReceiver(it) }
        }
    }

    override fun onNewToken(token: String) {
        Log.e("test_token", "onNewToken : $token")
        val intent = Intent("Token Receiver")
        intent.putExtra("fcmToken", token)
        sendBroadcast(intent)
    }

    private fun sendMessageToBroadcastReceiver(message: String) {
        val intent = Intent("Chat Receiver")
        intent.putExtra("message", message)
        sendBroadcast(intent)
    }
}