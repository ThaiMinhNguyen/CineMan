package com.nemo.cineman

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.nemo.cineman.api.MovieRepository
import com.nemo.cineman.viewmodel.MainViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.assertEquals
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

    lateinit var mainViewModel: MainViewModel

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup(){
        hiltRule.inject()
        mainViewModel = MainViewModel(movieRepository)
    }

    @Test
    fun fetchMovie(){

        var latch = CountDownLatch(1)

        mainViewModel.movies.observeForever { movies ->
            if (movies != null) {
                Log.d("MyLog", "Fetched movies: $movies")
                latch.countDown()
            }
        }


        mainViewModel.fetchMovies(1)
        Log.d("MyLog", mainViewModel.getSimilarMovie(213213, 1).toString())

        latch.await(5, TimeUnit.SECONDS)

    }
}