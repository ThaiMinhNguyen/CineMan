package com.nemo.cineman.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.nemo.cineman.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.checkSession()
    }

    val notificationEvent by viewModel.notificationEvent.collectAsState()
    val onDismiss = {
        viewModel.onNotificationHandled()
    }

    val logOut = {
        viewModel.onLogOutHandled()
        navController.navigate("login"){
            popUpTo(navController.graph.startDestinationRoute!!){inclusive = true}
            launchSingleTop = true
        }
    }

    val onConfirm = {
        viewModel.onNotificationHandled()
        viewModel.onLogOutHandled()
        navController.navigate("login"){
            popUpTo(navController.graph.startDestinationRoute!!){inclusive = true}
            launchSingleTop = true
        }
    }
    val icon = Icons.Default.Info // Example icon

    Column {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = Color.Black,
            ),
            title = {
                Text(
                    text = "Menu"
                )
            },
            navigationIcon = {
                IconButton(onClick = logOut ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back button"
                    )
                }
            },
            actions = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "Menu trailing icon"
                    )
                }
            }
        )
//        Button(onClick = {
//            CoroutineScope(Dispatchers.IO).launch { viewModel.checkSession() }
//            }
//        ) {
//            Text(text = "TEST SESSION TIME OUT")
//        }
        if (notificationEvent != null) {
            AlertDialogExample(
                onDismissRequest = onDismiss,
                onConfirmation = onConfirm,
                dialogTitle = "Session Expired",
                dialogText = notificationEvent!!,
                icon = icon
            )

        }
    }
}

@Preview
@Composable
fun MenuPreview() {
    val navController = rememberNavController()
    MenuScreen(navController = navController)
}


