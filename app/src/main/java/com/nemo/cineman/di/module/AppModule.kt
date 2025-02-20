package com.nemo.cineman.di.module

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.nemo.cineman.api.AuthService
import com.nemo.cineman.api.MovieService
import com.nemo.cineman.api.UserService
import com.nemo.cineman.room.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@InstallIn(SingletonComponent::class) //xác định phạm vi của Module
@Module
object AppModule {
    private const val APIKEY = "62155d3012f8cbb0d312c96483845af9"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val originalHttpUrl = original.url
                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("api_key", APIKEY)
                    .build()
                val requestBuilder = original.newBuilder().url(url)
                val request = requestBuilder.build()
                chain.proceed(request)
            }.build()
    }


    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient) : Retrofit = Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/").client(client).addConverterFactory(GsonConverterFactory.create()).build()

    @Provides
    @Singleton
    fun provideMovieService(retrofit: Retrofit): MovieService = retrofit.create(MovieService::class.java)

    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthService = retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    fun provideUserService(retrofit: Retrofit): UserService = retrofit.create(UserService::class.java)

    @Provides
    @Singleton
    fun provideDb(@ApplicationContext context: Context): AppDatabase = Room.databaseBuilder(context, AppDatabase::class.java, "app_db").build()

    @Provides
    @Singleton
    fun provideMovieDao(appDb: AppDatabase) = appDb.movieDao()

    @Provides
    @Named("session_pref")
    @Singleton
    fun provideSessionPreferences(@ApplicationContext context: Context) : SharedPreferences {
        return context.getSharedPreferences("session_prefs", Context.MODE_PRIVATE)
    }
}