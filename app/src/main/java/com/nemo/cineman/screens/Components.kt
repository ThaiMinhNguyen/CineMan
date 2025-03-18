package com.nemo.cineman.screens

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import com.nemo.cineman.R
import com.nemo.cineman.entity.Movie
import com.nemo.cineman.entity.MovieList
import com.nemo.cineman.ui.theme.heavyTitle
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView


@Composable
fun AlertDialogExample(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Example Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}


@Preview
@Composable
fun PreviewAlertDialogExample() {
    val onDismiss = { /* Handle dismiss action */ }
    val onConfirm = { /* Handle confirm action */ }
    val icon = Icons.Default.Info // Example icon
    AlertDialogExample(
        onDismissRequest = onDismiss,
        onConfirmation = onConfirm,
        dialogTitle = "Session Expired",
        dialogText = "Your session has expired. Please log in again.",
        icon = icon
    )
}

@Composable
fun MovieCard(movie: Movie, navController: NavController) {
    Card(
        modifier = Modifier
            .width(380.dp)
            .height(150.dp)
            .padding(vertical = 8.dp)
            .clickable {
                navController.navigate("detailMovie/${movie.id}") {

                }
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Poster image
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500${movie.poster_path ?: ""}",
                contentDescription = "Movie poster",
                placeholder = painterResource(R.drawable.ic_launcher_foreground),
                error = painterResource(R.drawable.ic_launcher_background),
                modifier = Modifier
                    .size(100.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = movie.release_date,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = movie.overview,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Rating: ${movie.vote_average}/10",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun MovieGrid(movies: List<Movie>, navController: NavController) {

    val pagerState = rememberPagerState(pageCount = { (movies.size + 1) / 2 })

    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
    ) { page ->

        val movieIndex1 = page * 2
        val movieIndex2 = movieIndex1 + 1

        Column {

            if (movieIndex1 < movies.size) {
                MovieCard(movie = movies[movieIndex1], navController)
            }


            if (movieIndex2 < movies.size) {
                MovieCard(movie = movies[movieIndex2], navController)
            }
        }
    }
}

@Composable
fun MovieColumn(movies: LazyPagingItems<Movie>, navController: NavController){
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(movies.itemCount) { index ->
            movies[index]?.let { MovieCard(movie = it, navController) } // Hiển thị mỗi MovieCard
        }
        movies.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item {
                        Box(modifier = Modifier.fillMaxSize()){
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                    }
                }
                loadState.append is LoadState.Loading -> {
                    item {
                        Box(modifier = Modifier.fillMaxSize()){
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                    }
                }
                loadState.refresh is LoadState.Error -> {
                    val e = loadState.refresh as LoadState.Error
                    item {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Text(
                                text = "Error: ${e.error.localizedMessage}",
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TitleType(type: String, navController: NavController    ){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp), // Đảm bảo Row chiếm hết chiều rộng
        verticalAlignment = Alignment.CenterVertically, // Căn chỉnh theo chiều dọc
        horizontalArrangement = Arrangement.SpaceBetween // Tạo không gian giữa các thành phần
    ) {
        Text(
            text = type,
            fontSize = 20.sp, // Cỡ chữ lớn hơn
            fontWeight = FontWeight.Bold, // In đậm
            modifier = Modifier.weight(1f) // Căn lề trái
        )
        TextButton(onClick = {
            navController.navigate("listMovie/$type")
            Log.d("MyLog", "See more clicked")
            }
        ) {
            Text(
                text = "See More",
                color = Color.DarkGray
            )
        }
    }
}

@Preview
@Composable
fun TitleTypePreview(){
    TitleType(type = "Now Playing", rememberNavController())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuTopAppBar(
    title: String,
    navController: NavController,
    isExpanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onLogOut: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ),
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineLarge,
                fontFamily = heavyTitle
            )
        },
        actions = {
            IconButton(onClick = { onExpandedChange(true) }) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = "Menu trailing icon",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
            DropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { onExpandedChange(false) }
            ) {
                DropdownMenuItem(
                    text = { Text("Account") },
                    onClick = {
                        onExpandedChange(false)
                        navController.navigate("accountDetail")
                    }
                )
                DropdownMenuItem(
                    text = { Text("Log out") },
                    onClick = {
                        Log.d("MyLog", "Log out pressed")
                        onLogOut()
                        onExpandedChange(false)
                    }
                )
            }
        },
        modifier = modifier
    )
}

@Composable
fun DefaultBottomBar(navController: NavController){
    BottomAppBar(contentColor = MaterialTheme.colorScheme.onPrimaryContainer, containerColor = MaterialTheme.colorScheme.primaryContainer) {
        IconButton(
            onClick = {
                navController.navigate("menu") {
                    launchSingleTop = true
                }
            },
            modifier = Modifier.weight(1f)
        ) {
            Icon(imageVector = Icons.Default.Home, contentDescription = "Home")
        }
        IconButton(
            onClick = {
                navController.navigate("searchMovie") {
                    launchSingleTop = true
                }
            },
            modifier = Modifier.weight(1f)
        ) {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
        }
        IconButton(
            onClick = {
                navController.navigate("accountDetail"){
                    launchSingleTop = true
                }
            },
            modifier = Modifier.weight(1f)
        ) {
            Icon(imageVector = Icons.Default.Person, contentDescription = "Account")
        }
        IconButton(
            onClick = { /* Handle Home action */ },
            modifier = Modifier.weight(1f)
        ) {
            Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings")
        }
    }
}

@Composable
fun YouTubePlayer(videoKey: String) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    AndroidView(factory = { ctx ->
        YouTubePlayerView(ctx).apply {
            lifecycle.addObserver(this)

            addYouTubePlayerListener(object :AbstractYouTubePlayerListener(){
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.loadVideo(videoKey, 0f)
                }
            })

        }
    })
}

@Composable
fun UserListDialog(
    movieLists: LazyPagingItems<MovieList>,
    onDismiss: () -> Unit,
    onListSelected: (Int) -> Unit,
    onCreateNewList: (String, String, String) -> Unit
) {
    var isCreateDialogOpen by remember {
        mutableStateOf(false)
    }
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnClickOutside = true,
            dismissOnBackPress = true
        )
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Danh sách Playlist",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .weight(1f, fill = false)
                        .heightIn(max = 400.dp)
                ) {
                    LazyColumn(
                        modifier = Modifier.padding(4.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)

                    ) {
                        items(movieLists.itemCount) { index ->
                            movieLists[index]?.let { movieList ->
                                Card(
                                    modifier = Modifier
                                        .animateItem(
                                            fadeInSpec = null,
                                            fadeOutSpec = null
                                        )
                                        .fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp),
                                    onClick = {
                                        onListSelected(movieList.id)
                                        onDismiss()
                                    },
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.surface
                                    )
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(12.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Text(
                                                text = movieList.name,
                                                style = MaterialTheme.typography.bodyLarge,
                                                fontWeight = FontWeight.Medium
                                            )
                                            Text(
                                                text = movieList.description,
                                                style = MaterialTheme.typography.bodyMedium,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                                maxLines = 2,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                        }
                                    }
                                }
                                if (index < movieLists.itemCount - 1) {
                                    HorizontalDivider(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        thickness = 1.dp,
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f) // Màu tùy chỉnh
                                    )
                                }
                            }
                        }


                        movieLists.apply {
                            when {
                                loadState.refresh is LoadState.Loading -> {
                                    item {
                                        CircularProgressIndicator(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(16.dp)
                                        )
                                    }
                                }
                                loadState.append is LoadState.Loading -> {
                                    item {
                                        CircularProgressIndicator(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(8.dp)
                                        )
                                    }
                                }
                                loadState.refresh is LoadState.Error -> {
                                    val error = loadState.refresh as LoadState.Error
                                    item {
                                        Text(
                                            text = "Lỗi: ${error.error.localizedMessage}",
                                            color = MaterialTheme.colorScheme.error,
                                            style = MaterialTheme.typography.bodyLarge,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(16.dp),
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { isCreateDialogOpen = !isCreateDialogOpen },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Tạo Playlist Mới",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
        if(isCreateDialogOpen){
            CreateListDialog(
                onDismiss = {isCreateDialogOpen = !isCreateDialogOpen},
                onCreateRequest = { name, description, language ->
                    onCreateNewList(name, description, language)
                }
            )
        }
    }
}

@Composable
fun CreateListDialog(
    onDismiss: () -> Unit,
    onCreateRequest: (String, String, String) -> Unit,
){
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnClickOutside = true,
            dismissOnBackPress = true
        )
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                var name by remember { mutableStateOf("") }
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                var description by remember { mutableStateOf("") }
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth()
                )


                var language by remember { mutableStateOf("") }
                OutlinedTextField(
                    value = language,
                    onValueChange = { language = it },
                    label = { Text("Language") },
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = {
                        onCreateRequest(name, description, language)
                        onDismiss()
                    },
                    modifier = Modifier
                        .align(Alignment.End)
                ) {
                    Text("Create")
                }
            }
        }
    }
}