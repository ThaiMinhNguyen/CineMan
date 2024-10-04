package com.nemo.cineman

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.runner.AndroidJUnitRunner
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
        val latch = CountDownLatch(1)  // Tạo latch để chờ API trả về
        
        val movies = movieRepository.getLocalMovie()
        assertEquals(0, movies.size)

        // Gọi API để lấy danh sách phim
        movieRepository.fetchMovie ({ result ->
            if (result.isSuccess) {
                val moviesList = result.getOrNull()
                println("Movies: $moviesList")  // In ra danh sách phim
            } else {
                val error = result.exceptionOrNull()
                println("Error: ${error?.message}")  // In ra lỗi nếu có
            }
            latch.countDown()  // Giảm count của latch sau khi có kết quả
        },1)



        latch.await(5, TimeUnit.SECONDS)  // Chờ tối đa 5 giây cho API trả về
    }
}