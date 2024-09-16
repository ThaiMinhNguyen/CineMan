package com.nemo.cineman.di

import android.content.Context
import androidx.room.Room
import com.nemo.cineman.api.MovieService
import com.nemo.cineman.di.module.AppModule
import com.nemo.cineman.entity.MovieDao
import com.nemo.cineman.room.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import org.mockito.Mockito
import org.mockito.Mockito.mock
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModule::class]
)
object TestModule {

    @Provides
    @Singleton
    fun provideInMemoryDb(@ApplicationContext context: Context): AppDatabase {
        return Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries() // For testing, allow Room queries on the main thread
            .build()
    }


    @Provides
    @Singleton
    fun provideMovieDao(appDb: AppDatabase): MovieDao {
        return appDb.movieDao()
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/") // URL của API thật
            .addConverterFactory(GsonConverterFactory.create()) // Sử dụng Gson để parse JSON
            .build()
    }

    @Provides
    @Singleton
    fun provideMovieService(retrofit: Retrofit): MovieService {
        return retrofit.create(MovieService::class.java)
    }

}