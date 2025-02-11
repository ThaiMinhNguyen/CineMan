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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.nemo.cineman.R
import com.nemo.cineman.entity.DetailMovie
import com.nemo.cineman.entity.Genre
import com.nemo.cineman.entity.ProductionCompany
import com.nemo.cineman.entity.ProductionCountry
import com.nemo.cineman.entity.SpokenLanguage

//{
//    "adult": false,
//    "backdrop_path": "/hZkgoQYus5vegHoetLkCJzb17zJ.jpg",
//    "belongs_to_collection": null,
//    "budget": 63000000,
//    "genres": [
//    {
//        "id": 18,
//        "name": "Drama"
//    },
//    {
//        "id": 53,
//        "name": "Thriller"
//    },
//    {
//        "id": 35,
//        "name": "Comedy"
//    }
//    ],
//    "homepage": "http://www.foxmovies.com/movies/fight-club",
//    "id": 550,
//    "imdb_id": "tt0137523",
//    "original_language": "en",
//    "original_title": "Fight Club",
//    "overview": "A ticking-time-bomb insomniac and a slippery soap salesman channel primal male aggression into a shocking new form of therapy. Their concept catches on, with underground \"fight clubs\" forming in every town, until an eccentric gets in the way and ignites an out-of-control spiral toward oblivion.",
//    "popularity": 61.416,
//    "poster_path": "/pB8BM7pdSp6B6Ih7QZ4DrQ3PmJK.jpg",
//    "production_companies": [
//    {
//        "id": 508,
//        "logo_path": "/7cxRWzi4LsVm4Utfpr1hfARNurT.png",
//        "name": "Regency Enterprises",
//        "origin_country": "US"
//    },
//    {
//        "id": 711,
//        "logo_path": "/tEiIH5QesdheJmDAqQwvtN60727.png",
//        "name": "Fox 2000 Pictures",
//        "origin_country": "US"
//    },
//    {
//        "id": 20555,
//        "logo_path": "/hD8yEGUBlHOcfHYbujp71vD8gZp.png",
//        "name": "Taurus Film",
//        "origin_country": "DE"
//    },
//    {
//        "id": 54051,
//        "logo_path": null,
//        "name": "Atman Entertainment",
//        "origin_country": ""
//    },
//    {
//        "id": 54052,
//        "logo_path": null,
//        "name": "Knickerbocker Films",
//        "origin_country": "US"
//    },
//    {
//        "id": 4700,
//        "logo_path": "/A32wmjrs9Psf4zw0uaixF0GXfxq.png",
//        "name": "The Linson Company",
//        "origin_country": "US"
//    },
//    {
//        "id": 25,
//        "logo_path": "/qZCc1lty5FzX30aOCVRBLzaVmcp.png",
//        "name": "20th Century Fox",
//        "origin_country": "US"
//    }
//    ],
//    "production_countries": [
//    {
//        "iso_3166_1": "US",
//        "name": "United States of America"
//    }
//    ],
//    "release_date": "1999-10-15",
//    "revenue": 100853753,
//    "runtime": 139,
//    "spoken_languages": [
//    {
//        "english_name": "English",
//        "iso_639_1": "en",
//        "name": "English"
//    }
//    ],
//    "status": "Released",
//    "tagline": "Mischief. Mayhem. Soap.",
//    "title": "Fight Club",
//    "video": false,
//    "vote_average": 8.433,
//    "vote_count": 26280
//}


@Composable
fun DetailMovieScreen(movieId: Int, navController: NavController){
//    val moviebackdrop_path = "/hZkgoQYus5vegHoetLkCJzb17zJ.jpg"
//    val movieName = "This is the Name"
//    val genres = listOf(
//        Genre(id = 35, name = "Comedy"),
//        Genre(id = 27, name = "Horror")
//    )
//    Column(
//       modifier = Modifier
//           .fillMaxSize()
//           .background(Color.DarkGray)
//    ) {
//        Box(
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            IconButton(
//                onClick = {navController.popBackStack()},
//                modifier = Modifier
//                    .align(Alignment.TopStart)
//                    .zIndex(2f)
//            ){
//                Icon(Icons.AutoMirrored.Default.ArrowBack,
//                    contentDescription = "Back button",
//                    tint = Color.White
//                )
//            }
//            AsyncImage(
//                modifier = Modifier.fillMaxWidth(),
//                model = ImageRequest.Builder(LocalContext.current)
//                    .data("https://image.tmdb.org/t/p/w500$moviebackdrop_path")
//                    .crossfade(true)
//                    .error(R.drawable.ic_launcher_background)
//                    .build(),
//                contentDescription = "Backdrop Image",
//            )
//            Column(
//                modifier = Modifier
//                    .align(Alignment.BottomCenter) // Canh dưới
//                    .fillMaxWidth()
//                    .background(Color.Black.copy(alpha = 0.2f)) // Nền mờ
//                    .padding(8.dp),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Text(
//                    text = movieName,
//                    color = Color.White,
//                    fontSize = 16.sp,
//                    fontWeight = FontWeight.Bold
//                )
//
//                Spacer(modifier = Modifier.height(4.dp))
//
//                Button(
//                    colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black),
//                    onClick = { /* TODO: Xử lý sự kiện */ }
//                ) {
//                    Row(
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Icon(
//                            Icons.Default.PlayArrow,
//                            contentDescription = "Play Trailer",
//                        )
//                        Spacer(modifier = Modifier.width(8.dp))
//                        Text(
//                            text = "Play trailer",
//                        )
//                    }
//                }
//            }
//        }
//        Row(
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Text(
//                modifier = Modifier
//                    .padding(8.dp),
//                text = "Genre",
//                color = Color.White,
//                fontSize = 20.sp,
//                fontWeight = FontWeight.Bold
//            )
//            LazyRow() {
//                items(genres.count()) { index ->
//                    Text(
//                        text = genres[index].name,
//                        color = Color.White,
//                        fontSize = 20.sp,
//                        modifier = Modifier
//                            .padding(8.dp)
//                            .border(2.dp, Color.White, RoundedCornerShape(8.dp))
//                            .padding(8.dp)
//                    )
//
//                }
//            }
//        }
//
//    }
    val movie = DetailMovie(
        adult = false,
        backdropPath = null,
        belongsToCollection = null,
        budget = 0,
        genres = listOf(
            Genre(id = 35, name = "Comedy"),
            Genre(id = 27, name = "Horror")
        ),
        homepage = "",
        id = 321348,
        imdbId = "tt0867605",
        originCountry = listOf("US"),
        originalLanguage = "en",
        originalTitle = "Oh My Ghost!",
        overview = "A horror-comedy story, Oh my Ghost is about Trixie (Rufa Mae Quinto), " +
                "a beautiful and sexy ad agency executive who focuses on her career and less of her love life. " +
                "Even her best friend and partner in the agency, Alvin (Marvin Agustin), tries to make moves on her " +
                "but just doesn't work. Meanwhile, three young egotistic guys - Dennis (Paolo Contis), Jeff (Carlos Agassi) " +
                "and Buboy (Uma Khouny), meet Trixie in the gym who get so attracted and madly in love with her. " +
                "Because of their arrogance and conceitedness, Trixie turns each one down that makes the three angry at her. " +
                "To get even with her, they attempt to rape her that lead to a fatal accident. " +
                "So sudden and untimely, Trixie's ghost cannot accept it and decides to take revenge. " +
                "Horror-comic situations follow as she haunts the three to obtain justice.",
        popularity = 0.608,
        posterPath = null,
        productionCompanies = listOf(
            ProductionCompany(
                id = 47208,
                logoPath = "/wC6oTPnoGM40h3Hj19dT7Dwk5H5.png",
                name = "OctoArts Films",
                originCountry = "PH"
            )
        ),
        productionCountries = listOf(
            ProductionCountry(iso31661 = "PH", name = "Philippines")
        ),
        releaseDate = "2006-01-01",
        revenue = 0,
        runtime = 0,
        spokenLanguages = listOf(
            SpokenLanguage(englishName = "Tagalog", iso6391 = "tl", name = null.toString())
        ),
        status = "Released",
        tagline = "",
        title = "Oh My Ghost!",
        video = false,
        voteAverage = 0.0,
        voteCount = 0
    )
    Box(modifier = Modifier.fillMaxSize()) {
        // Ảnh nền (Backdrop)
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            model = "https://image.tmdb.org/t/p/w500${movie.backdropPath}",
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
                text = movie.title,
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            ActionButtonsRow({ /* TODO: Xử lý sự kiện */}, {/* TODO: Xử lý sự kiện */ }, {/* TODO: Xử lý sự kiện */ })

            Text(
                text = "Release Date: ${movie.releaseDate}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )

            // Thể loại phim
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(movie.genres.count()) { index ->
                    Text(
                        text = movie.genres[index].name,
                        color = Color.White,
                        modifier = Modifier
                            .border(1.dp, Color.White, RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            // Nội dung phim
            Text(
                text = movie.overview,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )

            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Production: ")
                    }
                    append(movie.productionCompanies.joinToString { it.name })
                },
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )

            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Countries: ")
                    }
                    append(movie.productionCountries.joinToString { it.name })
                },
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Star, contentDescription = "Rating", tint = Color.Yellow)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${movie.voteAverage} (${movie.voteCount} votes)",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )
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
            modifier = Modifier.padding(4.dp).border(1.dp, Color.White, RoundedCornerShape(30.dp))
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
            modifier = Modifier.padding(4.dp).border(1.dp, Color.White, RoundedCornerShape(30.dp))
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
            modifier = Modifier.padding(4.dp).border(1.dp, Color.White, RoundedCornerShape(30.dp))
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
