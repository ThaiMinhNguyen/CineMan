package com.nemo.cineman.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.nemo.cineman.entity.ListType
import com.nemo.cineman.ui.theme.heavyTitle
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

    var isExpanded by remember{ mutableStateOf(false) }

    val logOut = {
        viewModel.onLogOutHandled()
        navController.navigate("login") {
            popUpTo(navController.graph.startDestinationRoute!!) { inclusive = true }
            launchSingleTop = true
        }
    }

    val onConfirm = {
        viewModel.onNotificationHandled()
        viewModel.onLogOutHandled()
        navController.navigate("login") {
            popUpTo(navController.graph.startDestinationRoute!!) { inclusive = true }
            launchSingleTop = true
        }
    }
    val icon = Icons.Default.Info

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
                title = {
                    Text(
                        text = "Menu",
                        style = MaterialTheme.typography.headlineLarge,
                        fontFamily = heavyTitle
                    )
                },
                actions = {
                    IconButton(onClick = { isExpanded = true }) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = "Menu trailing icon",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    DropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false }) {
                        DropdownMenuItem(
                            text = { Text("Account") },
                            onClick = {
                                isExpanded = false
                                navController.navigate("accountDetail")
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Log out") },
                            onClick = {
                                Log.d("MyLog", "Log out pressed")
                                logOut()
                                isExpanded = false

                            }
                        )
                    }
                }
            )
        },
        bottomBar = {
            DefaultBottomBar(navController)
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
            ) {
                TitleType(ListType.NowPlaying.name, navController)
                MovieGrid(movies = nowPlayingMovies ?: emptyList(), navController)

                TitleType(ListType.Popular.name, navController)
                MovieGrid(movies = popularMovies ?: emptyList(), navController)

                TitleType(ListType.TopRated.name, navController)
                MovieGrid(movies = popularMovies ?: emptyList(), navController)
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


