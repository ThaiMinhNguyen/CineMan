package com.nemo.cineman.screens

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
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(navController: NavController){
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
               IconButton(onClick = { navController.popBackStack() }) {
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

   }
}

@Preview
@Composable
fun MenuPreview(){
    val navController = rememberNavController()
    MenuScreen(navController = navController)
}

