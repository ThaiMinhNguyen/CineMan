package com.nemo.cineman.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.nemo.cineman.ui.theme.playwrite

@Composable
fun LoginScreen(navController: NavController){
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
fun LoginForm(navController: NavController){
    var userText by remember {
        mutableStateOf("")
    }
    var passwordText by remember {
        mutableStateOf("")
    }
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
                navController.navigate("menu") }
        ) {
            Text(
                text = "Login"
            )
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

