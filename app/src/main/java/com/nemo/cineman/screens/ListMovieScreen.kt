package com.nemo.cineman.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.nemo.cineman.entity.ListType
import com.nemo.cineman.viewmodel.MainViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListMovieScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel(),
    type: ListType
) {

    val movies = viewModel.getMoviesByType(type).collectAsLazyPagingItems()

    Column {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = Color.Black,
            ),
            title = {
                Text(
                    text = type.name + " Movies"
                )
            },
            navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack()
                    Log.d("MyLog", "Pop Back Stack")
                } ) {
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
        MovieColumn(movies = movies)
    }
}