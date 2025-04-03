package com.nemo.cineman.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.nemo.cineman.entity.TMDBSortOptions
import com.nemo.cineman.viewmodel.MainViewModel
import com.nemo.cineman.viewmodel.MovieDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WatchlistScreen (
    navController: NavController,
    movieDetailViewModel: MovieDetailViewModel = hiltViewModel(),
    mainViewModel: MainViewModel = hiltViewModel(),
){

    var showSortMenu by remember { mutableStateOf(false) }
    val currentSortOption by movieDetailViewModel.sortOption.observeAsState(TMDBSortOptions.CREATE_AT_ASC)
    val movies = movieDetailViewModel.getWatchlistMovie().collectAsLazyPagingItems()
    val sortOptionTrigger by movieDetailViewModel.sortUpdateTrigger.observeAsState(0)
    LaunchedEffect(sortOptionTrigger) {
        movies.refresh()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
                title = {
                    Text(
                        text = "Favourite Movies",
                    )
                },
                actions = {
                    Box {
                        IconButton(onClick = { showSortMenu = true }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Sort,
                                contentDescription = "Sort options"
                            )
                        }
                        DropdownMenu(
                            expanded = showSortMenu,
                            onDismissRequest = { showSortMenu = false }
                        ) {
                            TMDBSortOptions.CREATE_OPTION.forEach { sortOption ->
                                DropdownMenuItem(
                                    text = {
                                        Text(TMDBSortOptions.getDisplayName(sortOption))
                                    },
                                    onClick = {
                                        movieDetailViewModel.setSortOption(sortOption)
                                        showSortMenu = false
                                    },
                                    leadingIcon = {
                                        if (currentSortOption == sortOption) {
                                            Icon(
                                                imageVector = Icons.AutoMirrored.Filled.Sort,
                                                contentDescription = null
                                            )
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            )
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