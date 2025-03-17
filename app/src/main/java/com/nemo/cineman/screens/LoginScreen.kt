package com.nemo.cineman.screens

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
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
    val icon = Icons.Default.Info // Example icon

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
                    if (passwordText.isEmpty() || userText.isEmpty()){
                        Toast.makeText(context, "Username or password cannot be empty", Toast.LENGTH_SHORT).show()
                    } else {
                        scope.launch {
                            Log.d("MyLog", "Login pressed")
                            viewModel.signInWithLogin(userText, passwordText)
                        }
                    }
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
                        viewModel.signInWithTMDB()
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
            Button(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(10.dp),
                onClick = {
                    scope.launch {
                        viewModel.signInWithGuest()
                    }
                }
            ) {
                Text(text = "Sign in with Guest")
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


