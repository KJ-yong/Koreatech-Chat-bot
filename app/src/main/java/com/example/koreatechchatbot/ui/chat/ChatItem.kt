package com.example.koreatechchatbot.ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.koreatechchatbot.R
import com.example.koreatechchatbot.ui.theme.KoreatechChatBotTheme
import com.example.koreatechchatbot.ui.theme.Yellow

@Composable
fun ChatBySelf(chat: Chat.BySelf) {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.TopEnd
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .background(Yellow, RoundedCornerShape(6.dp))
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                text = chat.content,
                style = MaterialTheme.typography.body2,
                color = Color.Black
            )
        }
    }
}

@Composable
fun ChatByBotOnlyText(
    chat: Chat.ByBotOnlyText
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = stringResource(id = R.string.chat_bot),
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .background(Color.White, RoundedCornerShape(6.dp))
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                text = chat.content,
                style = MaterialTheme.typography.body2,
                color = Color.Black
            )
        }
    }
}

@Preview
@Composable
fun ChatByBotPreview() {
    KoreatechChatBotTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Column {
                ChatByBotOnlyText(Chat.ByBotOnlyText(content = "내용 12345678 \n 12345 테스트"))
                ChatBySelf(Chat.BySelf(content = "안녕하세요 이것은 굉장히 긴 내용을 쳤을 때 줄 바꿈이 어떻게 되는지 궁금해서 해보는 겁니다."))
                ChatByBotOnlyText(Chat.ByBotOnlyText(content = "굉장히 긴 내용을 쳤을 때 줄 바꿈이 어떻게 이루어지는 지를 확인하기 위한 테스트 입니다."))
            }
        }
    }
}