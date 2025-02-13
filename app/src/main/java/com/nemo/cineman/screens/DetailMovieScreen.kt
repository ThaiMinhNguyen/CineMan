package com.nemo.cineman.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.nemo.cineman.R
import com.nemo.cineman.entity.DetailMovie
import com.nemo.cineman.entity.Genre
import com.nemo.cineman.entity.ProductionCompany
import com.nemo.cineman.entity.ProductionCountry
import com.nemo.cineman.entity.SpokenLanguage
import com.nemo.cineman.viewmodel.MovieDetailViewModel

@Composable
fun DetailMovieScreen(movieId: Int, navController: NavController, movieDetailViewModel: MovieDetailViewModel = hiltViewModel()){
    val movie by movieDetailViewModel.movie.observeAsState()
    val isLoading by movieDetailViewModel.isLoading.observeAsState(false)

    LaunchedEffect(Unit) {
        movieDetailViewModel.getMovieDetail(movieId)
    }

    if (isLoading == true){
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colorScheme.primary
            )
        }
    } else {
        Box(modifier = Modifier.fillMaxSize()) {
            // Ảnh nền (Backdrop)
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                model = "https://image.tmdb.org/t/p/w500${movie?.backdropPath ?: ""}",
                contentDescription = "Backdrop Image",
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.ic_launcher_background) // Hình mặc định nếu lỗi
            )

            // Nút quay lại
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .padding(16.dp)
                    .background(Color.Black.copy(alpha = 0.5f), shape = CircleShape)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 250.dp)
                    .background(Color.Black.copy(alpha = 0.8f)) // Tạo nền mờ
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                Text(
                    text = movie?.title ?: "Unknown Title",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )

                ActionButtonsRow(
                    { /* TODO: Xử lý sự kiện */ },
                    {/* TODO: Xử lý sự kiện */ },
                    {/* TODO: Xử lý sự kiện */ })

                Text(
                    text = "Release Date: ${movie?.releaseDate ?: "Unknown"}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )

                // Thể loại phim
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(movie?.genres?.count() ?: 0) { index ->
                        Text(
                            text = movie?.genres?.get(index)?.name ?: "Unknown Genre",
                            color = Color.White,
                            modifier = Modifier
                                .border(1.dp, Color.White, RoundedCornerShape(8.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                // Nội dung phim
                Text(
                    text = movie?.overview ?: "No description available.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )

                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Production: ")
                        }
                        append(movie?.productionCompanies?.joinToString { it.name } ?: "Unknown")
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )

                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Countries: ")
                        }
                        append(movie?.productionCountries?.joinToString { it.name } ?: "Unknown")
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Star, contentDescription = "Rating", tint = Color.Yellow)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${movie?.voteAverage ?: "N/A"} (${movie?.voteCount ?: 0} votes)",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun ActionButtonsRow(
    onFavouriteClick: () -> Unit,
    onMarkClick: () -> Unit,
    onShareClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.Start // Căn các item về bên phải
    ) {
        IconButton(
            onClick = onFavouriteClick,
            modifier = Modifier
                .padding(4.dp)
                .border(1.dp, Color.White, RoundedCornerShape(30.dp))
        ) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Favourite",
                modifier = Modifier.size(24.dp),
                tint = Color.White
            )
        }
        IconButton(
            onClick = onMarkClick,
            modifier = Modifier
                .padding(4.dp)
                .border(1.dp, Color.White, RoundedCornerShape(30.dp))
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Mark",
                modifier = Modifier.size(24.dp),
                tint = Color.White
            )
        }
        IconButton(
            onClick = onShareClick,
            modifier = Modifier
                .padding(4.dp)
                .border(1.dp, Color.White, RoundedCornerShape(30.dp))
        ) {
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = "Share",
                modifier = Modifier.size(24.dp),
                tint = Color.White
            )
        }
    }
}
