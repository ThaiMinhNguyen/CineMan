package com.nemo.cineman.screens

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.nemo.cineman.ui.theme.body
import com.nemo.cineman.ui.theme.playwrite
import com.nemo.cineman.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navController: NavController,

){
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface
            ),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        WelcomeText()
        LoginForm(navController = navController)
    }
}

@Preview(showBackground = true, showSystemUi = true, device = "id:pixel_8_pro")
@Composable
fun LoginPreview(){
    LoginScreen(navController = rememberNavController())
}


@Preview
@Composable
fun WelcomeTextPreview(){
    WelcomeText()
}

@Composable
fun WelcomeText(){
    Text(
        text = "Welcome to CineMan",
        fontFamily = playwrite,
        fontSize = 50.sp,
        textAlign = TextAlign.Center,
        lineHeight = 80.sp,
        color = MaterialTheme.colorScheme.onBackground
    )
}

@Composable
fun LoginForm(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
    ) {
    var userText by remember {
        mutableStateOf("")
    }
    var passwordText by remember {
        mutableStateOf("")
    }

    val inputValid by remember {
        mutableStateOf(false)
    }

    val isLoading by viewModel.isLoading.observeAsState(false)
    val tokenValue by viewModel.requestToken.observeAsState(null)
    val scope = rememberCoroutineScope()
    val navigationEvent by viewModel.navigationEvent.collectAsState()
    val error by viewModel.error.observeAsState()
    val context = LocalContext.current
    val onDismiss = {
        viewModel.onErrorHandled()
    }

    val onConfirm = {
        viewModel.onErrorHandled()

    }
    val icon = Icons.Default.Info
    val keyboardController = LocalSoftwareKeyboardController.current

    val handleLogin = {
        if (passwordText.isEmpty() || userText.isEmpty()) {
            Toast.makeText(context, "Username or password cannot be empty", Toast.LENGTH_SHORT).show()
        } else {
            keyboardController?.hide() // Ẩn bàn phím
            scope.launch {
                Log.d("MyLog", "Login pressed")
                viewModel.signInWithLogin(userText, passwordText)
            }
        }
    }

    val textFieldColors = TextFieldDefaults.colors(
        focusedTextColor = MaterialTheme.colorScheme.onSurface,
        unfocusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
        focusedContainerColor = MaterialTheme.colorScheme.surface,
        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
        focusedIndicatorColor = MaterialTheme.colorScheme.primary,
        unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurfaceVariant,
        focusedLabelColor = MaterialTheme.colorScheme.primary,
        unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
        cursorColor = MaterialTheme.colorScheme.primary
    )

    val buttonColor = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    )

    LaunchedEffect(Unit) {
        viewModel.initApplication()
    }

    if (navigationEvent != null) {
        LaunchedEffect(navigationEvent) {
            navController.navigate(navigationEvent!!)
            viewModel.onNavigationHandled()
        }
    }

    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colorScheme.primary
            )
        }

    } else {
        Column {
            TextField(
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                maxLines = 1,
                value = userText,
                onValueChange = { userText = it },
                label = { Text("Username") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                colors = textFieldColors
            )
            TextField(
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Go
                ),
                keyboardActions = KeyboardActions(
                    onGo = {
                        handleLogin()
                    }
                ),
                maxLines = 1,
                value = passwordText,
                onValueChange = { passwordText = it },
                label = { Text("Password") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                colors = textFieldColors
            )
            FilledTonalButton(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(10.dp)
                    .fillMaxWidth(0.8f),
                onClick = {
                    handleLogin()
                },
                colors = buttonColor
            ) {
                Text(
                    text = "Login",
                    style = MaterialTheme.typography.bodyLarge,
                    fontFamily = body
                )
            }
            Button(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(10.dp)
                    .fillMaxWidth(0.8f),
                onClick = {
                    scope.launch {
                        viewModel.signInWithTMDB()
                    }
                },
                colors = buttonColor
            ) {
                Text(
                    text = "Sign in using TMDB account",
                    style = MaterialTheme.typography.bodyLarge,
                    fontFamily = body
                )
            }
            TextButton(onClick = {
                Log.d("MyLog", "No account pressed")
            }) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Don't have account? Register now",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge,
                    fontFamily = body
                )
            }
            Button(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(10.dp)
                    .fillMaxWidth(0.8f),
                onClick = {
                    scope.launch {
                        viewModel.signInWithGuest()
                    }
                }
            ) {
                Text(
                    text = "Sign in with Guest",
                    style = MaterialTheme.typography.bodyLarge,
                    fontFamily = body
                )
            }
            if (error != null) {
                AlertDialogExample(
                    onDismissRequest = onDismiss,
                    onConfirmation = onConfirm,
                    dialogTitle = "Session Expired",
                    dialogText = error!!,
                    icon = icon
                )
            }
        }

    }
}


