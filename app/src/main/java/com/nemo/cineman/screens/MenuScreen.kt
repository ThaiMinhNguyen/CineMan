package com.nemo.cineman.screens

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.nemo.cineman.entity.ListType
import com.nemo.cineman.viewmodel.MainViewModel


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
    val nowPlayingMovies by viewModel.nowPlayingMovies.observeAsState()
    val popularMovies by viewModel.popularMovies.observeAsState()

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

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = Color.Black,
                ),
                title = {
                    Text(text = "Menu")
                },
                navigationIcon = {
                    IconButton(onClick = logOut) {
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
        },
        bottomBar = {
            BottomAppBar {
                IconButton(
                    onClick = { /* Handle Home action */ },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(imageVector = Icons.Default.Home, contentDescription = "Home")
                }
                IconButton(
                    onClick = { /* Handle Home action */ },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Home")
                }
                IconButton(
                    onClick = { /* Handle Home action */ },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(imageVector = Icons.Default.Person, contentDescription = "Home")
                }
                IconButton(
                    onClick = { /* Handle Home action */ },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings")
                }
            }
        },
        content = { paddingValues ->
            // Nội dung chính của màn hình, có padding để tránh bị che khuất bởi BottomAppBar
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
            ) {
                TitleType(ListType.NowPlaying.name, navController)
                MovieGrid(movies = nowPlayingMovies ?: emptyList())

                TitleType(ListType.Popular.name, navController)
                MovieGrid(movies = popularMovies ?: emptyList())

                TitleType(ListType.TopRated.name, navController)
                MovieGrid(movies = popularMovies ?: emptyList())
            }
        }
    )

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

@Preview
@Composable
fun MenuPreview() {
    val navController = rememberNavController()
    MenuScreen(navController = navController)
}


