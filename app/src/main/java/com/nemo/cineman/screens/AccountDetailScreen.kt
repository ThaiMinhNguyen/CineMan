package com.nemo.cineman.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.nemo.cineman.entity.Account
import com.nemo.cineman.viewmodel.AccountViewModel
import com.nemo.cineman.viewmodel.AuthViewModel
import com.nemo.cineman.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountDetailScreen(
    navController: NavController,
    accountViewModel: AccountViewModel = hiltViewModel(),
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val account by accountViewModel.accountDetail.collectAsState()
    val errorMessage by accountViewModel.error.collectAsState()
    val isLoading by accountViewModel.isLoading.collectAsState()

    val onConfirm = {
        accountViewModel.onErrorHandled()
    }

    val logOut = {
        mainViewModel.onLogOutHandled()
        navController.navigate("login") {
            popUpTo(navController.graph.startDestinationRoute!!) { inclusive = true }
            launchSingleTop = true
        }
    }

    LaunchedEffect(Unit) {
        accountViewModel.fetchAccountDetail()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
                title = {
                    Text(text = "Account Detail")
                },

                )
        },
        bottomBar = {
            DefaultBottomBar(navController)
        },
        content = { paddingValues ->
            when {
                isLoading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                    }
                }
                account != null -> {
                    Box(modifier = Modifier.padding(paddingValues)) {
                        AccountDetailContent(account = account!!, logOut)
                    }
                }
                else -> {
                    Box(modifier = Modifier.padding(paddingValues)) {
                        Button(
                            onClick = logOut,
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Red,
                                contentColor = Color.White
                            ),
                            elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
                        ) {
                            Text(text = "Sign out of Guest Session")
                        }
                    }
                }
            }

        }
    )


    if (errorMessage != null) {
        AlertDialogExample(
            onDismissRequest = {
                accountViewModel.onErrorHandled()
                navController.popBackStack()
            },
            onConfirmation = onConfirm ,
            dialogTitle = "Session Expired",
            dialogText = errorMessage!!,
            icon = Icons.Default.Info
        )
    }
}


@Composable
fun AccountDetailContent(account: Account, logOut: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Avatar nếu có
        if (account.avatar?.gravatar?.hash?.isNotEmpty() == true) {
            val avatarUrl = "https://www.gravatar.com/avatar/${account.avatar.gravatar.hash}"
            AsyncImage(
                model = avatarUrl,
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
            )
        } else {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Default Avatar",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Thông tin tài khoản
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                AccountInfoRow("ID", account.id.toString())
                AccountInfoRow("Username", account.username)
                AccountInfoRow("Name", account.name)
                AccountInfoRow("Country", account.country)
                AccountInfoRow("Language", account.language)
                AccountInfoRow("Include Adult", if (account.includeAdult) "Yes" else "No")
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = logOut,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red, contentColor = Color.White),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
        ) {
            Text(text = "Sign out")
        }
    }
}

@Composable
fun AccountInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontWeight = FontWeight.Bold)
        Text(text = value)
    }
}