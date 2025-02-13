package com.nemo.cineman.screens

import android.util.Log
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import com.nemo.cineman.R
import com.nemo.cineman.entity.ListType
import com.nemo.cineman.entity.Movie


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
//        onClick = rememberNavController().navigate(DetailMovieScreen()),
        modifier = Modifier
            .width(380.dp)
            .height(150.dp)
            .padding(vertical = 8.dp)
//            .clickable { navController.navigate("menu") },
            .clickable { navController.navigate("detailMovie/${movie.id}"){

            } },
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
                    text = movie.title ?: "Unknown Title",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = movie.release_date ?: "Unknown Release Date",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = movie.overview ?: "No overview available",
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Rating: ${movie.vote_average ?: 0}/10",
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

@Composable
fun DefaultBottomBar(navController: NavController){
    BottomAppBar(contentColor = Color.White, containerColor = MaterialTheme.colorScheme.primary) {
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
}