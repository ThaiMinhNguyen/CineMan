package com.nemo.cineman.screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.nemo.cineman.entity.ListType
import com.nemo.cineman.entity.TMDBSortOptions
import com.nemo.cineman.viewmodel.MainViewModel
import com.nemo.cineman.viewmodel.MovieListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListMovieScreen (
    navController: NavController,
    movieListViewModel: MovieListViewModel = hiltViewModel(),
    listId: Int,
) {
    val movies = movieListViewModel.getMovies(listId).collectAsLazyPagingItems()
    val listName by movieListViewModel.listName.observeAsState("Loading...")
    val currentSortOption by movieListViewModel.sortOption.observeAsState(TMDBSortOptions.POPULARITY_DESC)
    var showSortMenu by remember { mutableStateOf(false) }
    val sortOptionTrigger by movieListViewModel.sortUpdateTrigger.observeAsState(0)

    LaunchedEffect(Unit) {
        movieListViewModel.setListId(listId)
        movieListViewModel.getListName()
    }

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
                        text = "$listName's Movies",
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                        Log.d("MyLog", "Pop Back Stack")
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back button"
                        )
                    }
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
                            TMDBSortOptions.ALL_OPTIONS.forEach { sortOption ->
                                DropdownMenuItem(
                                    text = { 
                                        Text(TMDBSortOptions.getDisplayName(sortOption))
                                    },
                                    onClick = {
                                        movieListViewModel.setSortOption(sortOption)
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
        bottomBar = {
            DefaultBottomBar(navController = navController)
        },
        content = { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                MovieColumn(movies = movies, navController)
            }
        }
    )
}