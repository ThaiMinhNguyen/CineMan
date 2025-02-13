package com.nemo.cineman

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.nemo.cineman.api.AuthRepository
import com.nemo.cineman.api.MovieRepository
import com.nemo.cineman.entity.SharedPreferenceManager
import com.nemo.cineman.viewmodel.AuthViewModel
import com.nemo.cineman.viewmodel.MainViewModel
import com.nemo.cineman.viewmodel.MovieDetailViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@SmallTest
class ApiTest {

    @Inject
    lateinit var movieRepository: MovieRepository

    @Inject
    lateinit var authRepository: AuthRepository

    @Inject
    lateinit var sharedPreferenceManager: SharedPreferenceManager

    lateinit var mainViewModel: MainViewModel
    lateinit var movieDetailViewModel: MovieDetailViewModel
    lateinit var authViewModel: AuthViewModel

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup(){
        hiltRule.inject()
        mainViewModel = MainViewModel(movieRepository, authRepository, sharedPreferenceManager)
        movieDetailViewModel = MovieDetailViewModel(movieRepository)
        authViewModel = AuthViewModel(authRepository, sharedPreferenceManager)
    }

    @Test
    fun fetchMovie(){

        var latch = CountDownLatch(5)

        mainViewModel.nowPlayingMovies.observeForever { movies ->
            if (movies != null) {
                Log.d("MyLogTest", "Fetched movies: $movies")
                latch.countDown()
            }
        }

        movieDetailViewModel.similarMovies.observeForever { movies ->
            if (movies != null) {
                Log.d("MyLogTest", "Fetched similar movies: $movies")
                latch.countDown()
            }
        }

        movieDetailViewModel.videoResults.observeForever { videos ->
            if (videos != null) {
                Log.d("MyLogTest", "Fetched movie trailers: $videos")
                latch.countDown()
            }
        }

        movieDetailViewModel.movie.observeForever{ movie ->
            if(movie != null){
                Log.d("MyLogTest", "Fetched movie detail: $movie")
                latch.countDown()
            } else {
                Log.e("MyLogTest", "Fetched movie detail: null")
            }

        }



        mainViewModel.getNowPlayingMovies(1)
        movieDetailViewModel.getSimilarMovie(213213, 1)
        movieDetailViewModel.getMovieTrailer(533535)
        movieDetailViewModel.getMovieDetail(939243)


        latch.await(5, TimeUnit.SECONDS)

    }

    @Test
    fun testGetMovieDetail() = runTest {
        // Gọi API thực tế
        val result = movieRepository.getMovieDetail(1)

        // In ra dữ liệu nhận được
        result.onSuccess { movie ->
            println("Fetched movie: $movie")
        }.onFailure { error ->
            println("Failed to fetch movie: ${error.message}")
        }


    }
}