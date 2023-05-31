package com.example.koreatechchatbot.ui.chat

import android.graphics.Rect
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.koreatechchatbot.R
import kotlinx.coroutines.launch

@Composable
fun ChatScreen(
    viewModel: ChatViewModel,
    onSendButtonClick: (chat: String) -> Unit
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val textFieldFocusRequester = remember { FocusRequester() }
    var previousChat = remember { mutableListOf<Chat>() }
    val helpPopupOpen = remember { mutableStateOf(false) }
    val isKeyboardOpen = keyboardAsState()
    val chat = viewModel.chatting.value
    val context = LocalContext.current

    with(viewModel) {
        if (chatFailMessage.value.isNotEmpty()) {
            Toast.makeText(context, chatFailMessage.value, Toast.LENGTH_SHORT).show()
            viewModel.initChatFailMessage()
        }
    }

    ConstraintLayout(
        modifier = Modifier
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
                IconButton(
                    modifier = Modifier
                        .align(Alignment.CenterEnd),
                    onClick = { helpPopupOpen.value = !helpPopupOpen.value }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.question_mark_24dp),
                        contentDescription = "",
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    DropdownMenu(
                        expanded = helpPopupOpen.value,
                        onDismissRequest = { helpPopupOpen.value = false },
                        modifier = Modifier.
                                width(300.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.chat_bot_help),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.body2,
                            color = Color.Black,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp, bottom = 12.dp)
                        )
                        Text(
                            text = stringResource(id = R.string.chat_bot_help_message) ,
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.body2,
                            color = Color.Black,
                            modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 16.dp)
                        )
                        Text(
                            text = stringResource(id = R.string.chat_bot_command),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.body2,
                            color = Color.Black,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 12.dp)
                        )
                        Text(
                            text = stringResource(id = R.string.chat_bot_command_source) ,
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.body2,
                            color = Color.Black,
                            modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 8.dp)
                        )
                        Text(
                            text = stringResource(id = R.string.chat_bot_command_init_context) ,
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.body2,
                            color = Color.Black,
                            modifier = Modifier.padding(start = 12.dp, end = 12.dp)
                        )
                    }
                }
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
                }
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        textFieldFocusRequester.freeFocus()
                    })
                },
            reverseLayout = false,
            state = listState
        ) {
            items(chat.size) { index ->
                with(chat[index]) {
                    when (this) {
                        is Chat.BySelf -> ChatBySelf(chat = this)
                        is Chat.ByBotOnlyText -> ChatByBotOnlyText(chat = this)
                    }
                }
            }
            if (previousChat.size != chat.size || isKeyboardOpen.value) {
                coroutineScope.launch {
                    if (chat.isNotEmpty()) {
                        listState.scrollToItem(chat.size - 1)
                    }
                }
            }
            val list = mutableListOf<Chat>()
            list.addAll(chat)
            previousChat = list
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
            sendButtonOnClick = {
                onSendButtonClick(it)
            },
            focusRequester = textFieldFocusRequester
        )
    }
}

@Composable
fun keyboardAsState(): State<Boolean> {
    val keyboardState = remember { mutableStateOf(false) }
    val view = LocalView.current
    DisposableEffect(view) {
        val onGlobalListener = ViewTreeObserver.OnGlobalLayoutListener {
            val rect = Rect()
            view.getWindowVisibleDisplayFrame(rect)
            val screenHeight = view.rootView.height
            val keypadHeight = screenHeight - rect.bottom
            keyboardState.value = keypadHeight > screenHeight * 0.15
        }
        view.viewTreeObserver.addOnGlobalLayoutListener(onGlobalListener)

        onDispose {
            view.viewTreeObserver.removeOnGlobalLayoutListener(onGlobalListener)
        }
    }

    return keyboardState
}