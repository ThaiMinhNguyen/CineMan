package com.nemo.cineman.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.nemo.cineman.viewmodel.MainViewModel
import com.nemo.cineman.viewmodel.MovieDetailViewModel

@Composable
fun FavouriteMovieScreen (
    navController: NavController,
    movieDetailViewModel: MovieDetailViewModel = hiltViewModel(),
    mainViewModel: MainViewModel = hiltViewModel(),
    ){
    var isExpanded by remember{ mutableStateOf(false) }

    val movies = movieDetailViewModel.getFavouriteMovie().collectAsLazyPagingItems()

    val logOut = {
        mainViewModel.onLogOutHandled()
        navController.navigate("login") {
            popUpTo(navController.graph.startDestinationRoute!!) { inclusive = true }
            launchSingleTop = true
        }
    }

    Scaffold(
        topBar = {
            MenuTopAppBar(
                title = "Playlist",
                navController = navController,
                isExpanded = isExpanded,
                onExpandedChange = { isExpanded = it },
                onLogOut = { logOut() })
        },
        content = { paddingValue ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValue)
            ){
                MovieColumn(movies = movies, navController = navController)
            }
        },
        bottomBar = {
            DefaultBottomBar(
                navController = navController,
            )
        }
    )
}
