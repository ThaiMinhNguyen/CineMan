package com.nemo.cineman.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.nemo.cineman.viewmodel.MainViewModel
import com.nemo.cineman.viewmodel.MovieDetailViewModel
import com.nemo.cineman.viewmodel.MovieListViewModel


@Composable
fun PlaylistScreen(
    navController: NavController,
    movieDetailViewModel: MovieDetailViewModel = hiltViewModel(),
    mainViewModel: MainViewModel = hiltViewModel(),
    userMovieListViewModel: MovieListViewModel = hiltViewModel()
    ){

    var isExpanded by remember{ mutableStateOf(false) }
    val movieList = movieDetailViewModel.getUserList().collectAsLazyPagingItems()
    var onConfirm by remember {
        mutableStateOf(false)
    }
    var onEdit by remember {
        mutableStateOf(false)
    }
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var language by remember { mutableStateOf("en") }
    var listId by remember { mutableStateOf(0) }



    val logOut = {
        mainViewModel.onLogOutHandled()
        navController.navigate("login") {
            popUpTo(navController.graph.startDestinationRoute!!) { inclusive = true }
            launchSingleTop = true
        }
    }

    val message by userMovieListViewModel.message.observeAsState()
    val context = LocalContext.current
    val isLoading by userMovieListViewModel.isLoading.observeAsState(false)
    var onConfirmText by remember {
        mutableStateOf("")
    }
    var onConfirmTextDetail by remember {
        mutableStateOf("")
    }
    var onConfirmAction: () -> Unit = {}

    if (message != null) {
        Toast.makeText(
            context,
            message,
            Toast.LENGTH_SHORT
        ).show()
        userMovieListViewModel.onMessageHandled()
    }

    Scaffold(
        topBar = {
            MenuTopAppBar(
                title = "Playlist",
                navController = navController,
                isExpanded = isExpanded,
                onExpandedChange = { isExpanded = it },
                onLogOut = { logOut()})
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(10.dp)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 80.dp),
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    items(movieList.itemCount) { index ->
                        var showMenu by remember { mutableStateOf(false) }

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.outlineVariant,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .clickable {
                                    userMovieListViewModel.setListId(movieList[index]?.id ?: 0)
                                    navController.navigate("playlist/${movieList[index]?.id}") {
                                        launchSingleTop = true
                                    }
                                },
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface,
                                contentColor = MaterialTheme.colorScheme.onSurface
                            )
                        ) {
                            Box {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text(
                                            text = movieList[index]?.name ?: "Title",
                                            style = MaterialTheme.typography.headlineSmall.copy(
                                                fontWeight = FontWeight.Bold
                                            ),
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Spacer(modifier = Modifier.height(6.dp))
                                        Text(
                                            text = movieList[index]?.description ?: "Description",
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            ),
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                    Box {
                                        IconButton(onClick = { showMenu = true }) {
                                            Icon(
                                                imageVector = Icons.Default.MoreVert,
                                                contentDescription = "More options",
                                                tint = MaterialTheme.colorScheme.onSurface
                                            )
                                        }
                                        DropdownMenu(
                                            expanded = showMenu,
                                            onDismissRequest = { showMenu = false }
                                        ) {
                                            DropdownMenuItem(
                                                text = { Text("Clear") },
                                                leadingIcon = { Icon(Icons.Default.Clear, contentDescription = "Clear") },
                                                onClick = {
                                                    showMenu = false
                                                    onConfirm = true
                                                    onConfirmText = "Clear List"
                                                    onConfirmTextDetail = "All movies will be removed from this list and this action cannot be undone."
                                                    onConfirmAction = {
                                                        Log.d("MyLog", "List ID on clear: ${movieList[index]?.id}")
                                                        userMovieListViewModel.clearList(movieList[index]?.id ?: 0)
                                                    }

                                                }
                                            )
                                            DropdownMenuItem(
                                                text = { Text("Edit") },
                                                leadingIcon = { Icon(Icons.Default.Edit, contentDescription = "Edit") },
                                                onClick = {
                                                    onConfirmText = "Edit List"
                                                    showMenu = false
                                                    onEdit = true
                                                    name = movieList[index]?.name ?: ""
                                                    description = movieList[index]?.description ?: ""
                                                    language = movieList[index]?.iso_639_1 ?: ""
                                                    listId = movieList[index]?.id ?: 0
                                                    onConfirmAction = {
                                                        userMovieListViewModel.editList(listId, name, description, language)
                                                    }
                                                }
                                            )
                                            DropdownMenuItem(
                                                text = { Text("Delete") },
                                                leadingIcon = { Icon(Icons.Default.Delete, contentDescription = "Delete") },
                                                onClick = {
                                                    showMenu = false
                                                    onConfirm = true
                                                    onConfirmText = "Delete List"
                                                    onConfirmTextDetail = "This action cannot be undone."
                                                    onConfirmAction = {
                                                        userMovieListViewModel.deleteList(movieList[index]?.id ?: 0)
                                                    }
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                Button(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth(),
                    onClick = {
                        onConfirmText = "Create New List"
                        onEdit = true
                        name = ""
                        description = ""
                        language = "en"
                        onConfirmAction = {
                            userMovieListViewModel.createUserList(name, description, language)
                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text(text = "Create New List")
                }
            }
            if (onConfirm){
                AlertDialog(
                    onDismissRequest = { onConfirm = false },
                    confirmButton = {
                        TextButton(
                            onClick = { 
                                onConfirmAction()
                                onConfirm = false
                            }
                        ) {
                            Text("Confirm")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = { onConfirm = false }
                        ) {
                            Text("Cancel")
                        }
                    },
                    title = {
                        Text(
                            text = onConfirmText,
                            style = MaterialTheme.typography.titleMedium
                        )
                    },
                    text = {
                        Text(
                            text = onConfirmTextDetail,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                )
            }
            if(onEdit) {
                AlertDialog(
                    onDismissRequest = { onEdit = false },
                    title = { Text(onConfirmText) },
                    text = {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedTextField(
                                value = name,
                                onValueChange = { name = it },
                                label = { Text("Name") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            
                            OutlinedTextField(
                                value = description,
                                onValueChange = { description = it },
                                label = { Text("Description") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            
                            OutlinedTextField(
                                value = language,
                                onValueChange = { language = it },
                                label = { Text("Language") },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                onConfirmAction()
                                onEdit = false
                            }
                        ) {
                            Text("Save")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { onEdit = false }) {
                            Text("Cancel")
                        }
                    }
                )
            }
            if(isLoading){
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        },
        bottomBar = {
            DefaultBottomBar(
                navController = navController,
            )
        }
    )
}

