package com.nemo.cineman.screens

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.nemo.cineman.ui.theme.playwrite
import com.nemo.cineman.viewmodel.AuthViewModel

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
    val url = "https://www.themoviedb.org/authenticate/${viewModel.requestToken}"
    val context = LocalContext.current
//    LaunchedEffect(isLoading) {
//        if(!isLoading && viewModel.requestToken != null){
//            Log.d("MyLog", viewModel.requestToken)
//            openUrlInCustomTab(context, url)
//        }
//    }

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
                    viewModel.getRequestToken()
                    if (viewModel.requestToken != null) {
                        Log.d("MyLog", viewModel.requestToken)
                        openUrlInCustomTab(context, url)
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

fun openUrlInCustomTab(context: Context,url: String){
    val customTabsIntent = CustomTabsIntent.Builder().build()
    customTabsIntent.launchUrl(context, Uri.parse(url))
}

