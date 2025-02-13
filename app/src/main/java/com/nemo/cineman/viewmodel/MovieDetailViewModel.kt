package com.nemo.cineman.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nemo.cineman.api.MovieRepository
import com.nemo.cineman.entity.DetailMovie
import com.nemo.cineman.entity.Genre
import com.nemo.cineman.entity.Movie
import com.nemo.cineman.entity.ProductionCompany
import com.nemo.cineman.entity.ProductionCountry
import com.nemo.cineman.entity.SpokenLanguage
import com.nemo.cineman.entity.VideoResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieDetailViewModel  @Inject constructor(
   private val movieRepository: MovieRepository
) : ViewModel() {
    val alt_movie = DetailMovie(
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

    private val _movie = MutableLiveData<DetailMovie>()
    val movie: LiveData<DetailMovie> get() = _movie

    private val _similarMovies = MutableLiveData<List<Movie>>()
    val similarMovies: LiveData<List<Movie>> get() = _similarMovies

    private val _videoResults = MutableLiveData<List<VideoResult>>()
    val videoResults: LiveData<List<VideoResult>> get() = _videoResults

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> get() = _isLoading


    fun getMovieDetail(id: Int){
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val result = movieRepository.getMovieDetail(id)
                result.onSuccess {
                    _movie.value = it
                    Log.d("MyLog", "Fetched movie: $it")
                }.onFailure {
                    Log.e("MyLog", "Failed to fetch movie: ${it.message}")
                }
            } finally {
                _isLoading.value = false
            }
        }
    }


    fun getSimilarMovie(id: Int, page: Int) {
        viewModelScope.launch {
            val result = movieRepository.fetchSimilarMovie(id, page)
            result.onSuccess { movies ->
                _similarMovies.postValue(movies!!)
                Log.d("MyLog", "Fetch similar movie: ${movies}")
            }.onFailure { exception ->
                Log.e("MyLog", "Failed to fetch similar movie: ${exception.message}")
            }
        }
    }

    fun getMovieTrailer(id: Int){
        viewModelScope.launch {
            val result = movieRepository.fetchMovieTrailer(id)
            result.onSuccess { videos ->
                _videoResults.postValue(videos!!)
                Log.d("MyLog", "Fetch movie trailers: ${videos}")
            }.onFailure { exception ->
                Log.e("MyLog", "Failed to fetch movie trailer: ${exception.message}")
            }
        }
    }

}