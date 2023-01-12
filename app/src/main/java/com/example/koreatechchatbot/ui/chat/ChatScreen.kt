package com.example.koreatechchatbot.ui.chat

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.koreatechchatbot.R
import com.example.koreatechchatbot.ui.theme.KoreatechChatBotTheme
import com.google.accompanist.insets.ProvideWindowInsets

@Composable
fun ChatScreen(chats: List<Chat>) {
    ConstraintLayout(
        modifier = Modifier
            .systemBarsPadding()
            .navigationBarsPadding()
            .fillMaxSize()
    ) {
        val (appBar, chatting, chatBar) = createRefs()
        TopAppBar(
            modifier = Modifier
                .constrainAs(appBar) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                },
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = MaterialTheme.colors.onPrimary,
            elevation = 0.dp
        ) {
            Box {
                Text(
                    text = stringResource(id = R.string.chat_bot),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                )
            }
        }
        LazyColumn(
            modifier = Modifier
                .constrainAs(chatting) {
                    top.linkTo(appBar.bottom)
                    bottom.linkTo(chatBar.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    height = Dimension.fillToConstraints
                },
            reverseLayout = false
        ) {
            items(chats.size) { index ->
                with(chats[index]) {
                    when (this) {
                        is Chat.BySelf -> ChatBySelf(chat = this)
                        is Chat.ByBotOnlyText -> ChatByBotOnlyText(chat = this)
                    }
                }
            }
        }
        ChatBar(
            modifier = Modifier
                .imePadding()
                .constrainAs(chatBar) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                },
            sendButtonOnClick = { TODO("전송 버튼 클릭") }
        )
    }
}

@Preview
@Composable
fun ChatScreenPreview() {
    val chatList = mutableListOf<Chat>()
    for (i in 1..10) {
        chatList.add(Chat.ByBotOnlyText(content = "내용 12345678 \n 12345 테스트"))
        chatList.add(Chat.BySelf(content = "안녕하세요 이것은 굉장히 긴 내용을 쳤을 때 줄 바꿈이 어떻게 되는지 궁금해서 해보는 겁니다."))
        chatList.add(Chat.ByBotOnlyText(content = "굉장히 긴 내용을 쳤을 때 줄 바꿈이 어떻게 이루어지는 지를 확인하기 위한 테스트 입니다."))
    }
    KoreatechChatBotTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            ChatScreen(chatList)
        }
    }
}