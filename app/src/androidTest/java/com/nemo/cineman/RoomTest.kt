package com.nemo.cineman

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.runner.AndroidJUnitRunner
import com.nemo.cineman.api.MovieRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject


@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@SmallTest
class RoomTest {

    @Inject
    lateinit var movieRepository: MovieRepository

    @get:Rule
    var hiltRule = HiltAndroidRule(this)


    @Before
    fun setup(){
        hiltRule.inject()
    }

    @Test
    fun insertAndTakeLocalMovie(){
        val movies = movieRepository.getLocalMovie()
        assertEquals(0, movies.size)
        println(movieRepository.fetchMovie().toString())
    }
}