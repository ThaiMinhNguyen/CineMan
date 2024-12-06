package com.nemo.cineman.screens

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
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
            .fillMaxWidth(),
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
        lineHeight = 80.sp
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
    val isLoading by viewModel.isLoading.observeAsState(false)
    val tokenValue by viewModel.requestToken.observeAsState(null)
    val scope = rememberCoroutineScope()

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
                value = userText,
                onValueChange = { userText = it },
                label = { Text("Username") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            )
            TextField(
                value = passwordText,
                onValueChange = { passwordText = it },
                label = { Text("Password") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            )
            FilledTonalButton(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(10.dp),
                onClick = {
                    Log.d("MyLog", "Login pressed")
                    navController.navigate("menu")
                }
            ) {
                Text(
                    text = "Login"
                )
            }
            Button(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(10.dp),
                onClick = {
                    scope.launch {
                        val result = viewModel.getRequestToken()
                        result.onSuccess { token ->
                            val url = "https://www.themoviedb.org/authenticate/$token"
                            Log.d("MyLog", "Requested Token On Success: $token")
                            Log.d("MyLog", "Requested Token when press button: " + viewModel.requestToken.value + "/" + tokenValue)
                            Log.d("MyLog", url)
                            val encodedUrl = Uri.encode(url)  // Mã hóa URL trước khi điều hướng
                            navController.navigate("webview/$encodedUrl")
                        }.onFailure {
                            Log.d("MyLog", "Failed to get token: ${viewModel.error.value}")
                        }
                    }
                }
            ) {
                Text(text = "Sign in using TMDB account")
            }
            TextButton(onClick = {
                Log.d("MyLog", "No account pressed")
            }) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Don't have account? Register now",
                    textAlign = TextAlign.Center
                )
            }

        }

    }
}


