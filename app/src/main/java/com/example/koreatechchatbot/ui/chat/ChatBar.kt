package com.example.koreatechchatbot.ui.chat

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.koreatechchatbot.R
import com.example.koreatechchatbot.ui.theme.Gray
import com.example.koreatechchatbot.ui.theme.GrayDisabled
import com.example.koreatechchatbot.ui.theme.KoreatechChatBotTheme
import com.example.koreatechchatbot.ui.theme.Yellow

@Composable
fun ChatBar(
    modifier: Modifier = Modifier,
    sendButtonOnClick: (String) -> Unit,
    focusRequester: FocusRequester
) {
    val chatState = remember { mutableStateOf("") }
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        val (divider, textField, button) = createRefs()
        Divider(
            modifier = Modifier
                .constrainAs(divider) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            color = MaterialTheme.colors.onBackground
        )
        BasicTextField(
            modifier = Modifier
                .focusRequester(focusRequester)
                .constrainAs(textField) {
                    top.linkTo(divider.bottom)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start, 16.dp)
                    end.linkTo(button.start, 16.dp)
                    width = Dimension.fillToConstraints
                },
            value = chatState.value,
            onValueChange = { chatState.value = it },
            textStyle = MaterialTheme.typography.body2
        )
        Button(
            modifier = Modifier
                .constrainAs(button) {
                    top.linkTo(divider.bottom)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end, 16.dp)
                },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Yellow,
                contentColor = Color.Black,
                disabledBackgroundColor = GrayDisabled,
                disabledContentColor = Gray
            ),
            enabled = chatState.value.isNotBlank(),
            shape = RoundedCornerShape(6.dp),
            onClick = {
                sendButtonOnClick(chatState.value)
                chatState.value = ""
            }
        ) {
            Text(
                text = stringResource(id = R.string.chat_bot_chat_send)
            )
        }
    }
}

@Preview
@Composable
fun ChatBarPreview() {
    KoreatechChatBotTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            val textFieldFocusRequester = remember { FocusRequester() }
            ChatBar(Modifier.fillMaxWidth(), {}, textFieldFocusRequester)
        }
    }
}