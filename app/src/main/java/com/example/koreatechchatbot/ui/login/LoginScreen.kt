package com.example.koreatechchatbot.ui.login

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.koreatechchatbot.R

@Composable
fun LoginScreen(viewModel: LoginViewModel) {
    val context = LocalContext.current
    val idState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }

    if (viewModel.loginSuccess.value) {
        (context as LoginActivity).goMainActivity()
    }
    with(viewModel.loginFailMessage.value) {
        if (isNotEmpty()) Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
    }
    ConstraintLayout {
        val (appBar, idTextField, passwordTextField, loginButton, loadingProgress) = createRefs()
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
                    text = stringResource(id = R.string.login),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                )
            }
        }
        TextField(
            modifier = Modifier
                .constrainAs(idTextField) {
                    start.linkTo(parent.start, 30.dp)
                    end.linkTo(parent.end, 30.dp)
                    bottom.linkTo(passwordTextField.top, 10.dp)
                    width = Dimension.fillToConstraints
                },
            value = idState.value,
            onValueChange = {
                idState.value = it
            },
            placeholder = {
                Text(
                    text = stringResource(id = R.string.login_id_hint)
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                backgroundColor = MaterialTheme.colors.background,
                placeholderColor = MaterialTheme.colors.onBackground
            ),
            shape = RoundedCornerShape(8.dp),
            singleLine = true
        )
        TextField(
            modifier = Modifier
                .constrainAs(passwordTextField) {
                    start.linkTo(parent.start, 30.dp)
                    end.linkTo(parent.end, 30.dp)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                },
            value = passwordState.value,
            onValueChange = {
                passwordState.value = it
            },
            placeholder = {
                Text(
                    text = stringResource(id = R.string.login_password_hint)
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                backgroundColor = MaterialTheme.colors.background,
                placeholderColor = MaterialTheme.colors.onBackground
            ),
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(
                mask = '⬤'
            )
        )
        Button(
            modifier = Modifier
                .constrainAs(loginButton) {
                    start.linkTo(parent.start, 30.dp)
                    end.linkTo(parent.end, 30.dp)
                    top.linkTo(passwordTextField.bottom, 40.dp)
                    width = Dimension.fillToConstraints
                },
            onClick = {
                if (idState.value.isBlank())
                    Toast.makeText(context, R.string.login_id_not_input, Toast.LENGTH_SHORT).show()
                else if (passwordState.value.isBlank())
                    Toast.makeText(context, R.string.login_password_not_input, Toast.LENGTH_SHORT).show()
                else viewModel.login(idState.value, passwordState.value)
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.secondary,
                contentColor = MaterialTheme.colors.onSecondary
            ),
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(vertical = 15.dp)
        ) {
            Text(
                text = stringResource(id = R.string.login),
                fontSize = 16.sp
            )
        }
        if (viewModel.isLoading.value) {
            CircularProgressIndicator(
                modifier = Modifier
                    .constrainAs(loadingProgress) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .width(100.dp)
                    .height(100.dp)
            )
        }
    }
}