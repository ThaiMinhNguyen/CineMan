package com.nemo.cineman.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.nemo.cineman.R
import com.nemo.cineman.entity.RatingBar
import com.nemo.cineman.viewmodel.MovieDetailViewModel
import kotlin.math.roundToInt

@Composable
fun DetailMovieScreen(
    movieId: Int,
    navController: NavController,
    movieDetailViewModel: MovieDetailViewModel = hiltViewModel()
) {
    val movie by movieDetailViewModel.movie.observeAsState()
    val isLoading by movieDetailViewModel.isLoading.collectAsState(false)
    val videoResults by movieDetailViewModel.videoResults.observeAsState()
    val isWatchlist by movieDetailViewModel.isWatchlist.observeAsState(false)
    val isFavourite by movieDetailViewModel.isFavourite.observeAsState(false)
    val ratedValue by movieDetailViewModel.ratedValue.observeAsState(0.0)


    LaunchedEffect(Unit) {
        movieDetailViewModel.getMovieDetail(movieId)
        movieDetailViewModel.getMovieTrailer(movieId)
    }
    Box(modifier = Modifier.fillMaxSize()) {

        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
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
                        isFavourite,
                        isWatchlist,
                        ratedValue,
                        { /* TODO: Xử lý sự kiện */ },
                        {/* TODO: Xử lý sự kiện */ },
                        { ratingValue ->
                            movieDetailViewModel.updateRating(movieId, ratingValue)
                        },
                        {/* TODO: Xử lý sự kiện */ }
                    )

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
                            append(movie?.productionCompanies?.joinToString { it.name }
                                ?: "Unknown")
                        },
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White
                    )

                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Countries: ")
                            }
                            append(movie?.productionCountries?.joinToString { it.name }
                                ?: "Unknown")
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
                    Text(
                        text = "Trailer & More",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    videoResults?.let {
                        // Khởi tạo trạng thái cho Pager
                        val size = videoResults!!.size
                        val pagerState = rememberPagerState(pageCount = { size })

                        // Sử dụng HorizontalPager thay cho LazyRow
                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier.fillMaxWidth(),
                            contentPadding = PaddingValues(horizontal = 20.dp)
                        ) { page ->
                            Box(
                                modifier = Modifier
                                    .width(300.dp)
                                    .height(200.dp)
                            ) {
                                YouTubePlayer(videoKey = videoResults!![page].key)
                            }
                        }
                    }
                }
            }
        }
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.6f)), // Nền trong suốt làm mờ
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    strokeWidth = 4.dp
                )
            }
        }
    }

}


@Composable
fun ActionButtonsRow(
    isFavourite: Boolean,
    isWatchlist: Boolean,
    ratedValue: Double,
    onFavouriteClick: () -> Unit,
    onMarkClick: () -> Unit,
    onRateClick: (Double) -> Unit,
    onShareClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.End
    ) {
        IconButton(
            onClick = onFavouriteClick,
            modifier = Modifier
                .padding(4.dp)
                .border(
                    1.dp,
                    if (isFavourite) Color.Red else Color.White,
                    RoundedCornerShape(30.dp)
                )
        ) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Favourite",
                modifier = Modifier.size(24.dp),
                tint = if (isFavourite) Color.Red else Color.White
            )
        }
        IconButton(
            onClick = onMarkClick,
            modifier = Modifier
                .padding(4.dp)
                .border(
                    1.dp,
                    if (isWatchlist) Color.Yellow else Color.White,
                    RoundedCornerShape(30.dp)
                )
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Mark",
                modifier = Modifier.size(24.dp),
                tint = if (isWatchlist) Color.Yellow else Color.White
            )
        }
        RatingButton(ratedValue = ratedValue, onRateClick)
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


@Composable
fun RatingButton(ratedValue: Double, updateRating: (Double) -> Unit) {
    var ratedV by remember { mutableStateOf(ratedValue) }
    var isPopupVisible by remember { mutableStateOf(false) }
    val buttonModifier = Modifier
        .padding(4.dp)
        .border(
            1.dp,
            if (ratedV != 0.0) Color.Yellow else Color.White,
            RoundedCornerShape(30.dp)
        )

    val showPopup = { isPopupVisible = true }


    val closePopup = { isPopupVisible = false }

    val updateRatingValue = { value: Double ->
        ratedV = value
        updateRating(value)
        closePopup()
    }


    TextButton(
        onClick = { showPopup() },
        modifier = buttonModifier
    ) {
        Text(
            text = ratedV.toString(),
            color = if (ratedV != 0.0) Color.Yellow else Color.White,
        )
    }


    if (isPopupVisible) {
        Popup(
            alignment = Alignment.TopCenter,
            offset = IntOffset(300, -300),
            onDismissRequest = { closePopup() }
        ) {
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .background(Color.Gray.copy(alpha = 0.8f), shape = RoundedCornerShape(8.dp))
                    .padding(8.dp)
            ) {
                RatingBar(onRatingChanged = updateRatingValue, rating = ratedV)
            }
        }
    }
}

