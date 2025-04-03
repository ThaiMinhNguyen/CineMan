package com.nemo.cineman

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nemo.cineman.entity.ListType
import com.nemo.cineman.screens.AccountDetailScreen
import com.nemo.cineman.screens.DetailMovieScreen
import com.nemo.cineman.screens.FavouriteMovieScreen
import com.nemo.cineman.screens.ListMovieScreen
import com.nemo.cineman.screens.LoginScreen
import com.nemo.cineman.screens.MenuScreen
import com.nemo.cineman.screens.PlaylistScreen
import com.nemo.cineman.screens.SearchScreen
import com.nemo.cineman.screens.UserListMovieScreen
import com.nemo.cineman.screens.WatchlistScreen
import com.nemo.cineman.screens.WebViewScreen
import com.nemo.cineman.ui.theme.CineManTheme
import com.nemo.cineman.ui.theme.playwrite
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CineManTheme {
                Box() {
                   val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "login"
                    ) {
                        composable(route = "login") {
                            LoginScreen(navController = navController)
                        }
                        composable(route = "menu") {
                            MenuScreen(navController = navController)
                        }
                        composable(
                            route = "webview/{url}",
                            arguments = listOf(
                                navArgument("url") {
                                    type = NavType.StringType
                                }
                            )
                        ) { backStackEntry ->

                            val url = backStackEntry.arguments?.getString("url") ?: "https://www.google.com"  // Nếu URL rỗng hoặc null, dùng Google
                            val decodedUrl = Uri.decode(url)
                            WebViewScreen(url = decodedUrl, navController)
                        }
                        composable(
                            route = "listMovie/{type}",
                            arguments = listOf(
                                navArgument("type"){
                                    type = NavType.StringType
                                }
                            )
                        ) { backStackEntry ->
                            val listTypeString = backStackEntry.arguments?.getString("type")
                            Log.d("MyLog", "Received type: $listTypeString")
                            val listType = runCatching {
                                ListType.valueOf(listTypeString ?: ListType.NowPlaying.name)
                            }.getOrElse {
                                Log.e("MyLog", "Invalid ListType: $listTypeString. Defaulting to NowPlaying.", it)
                                ListType.NowPlaying
                            }
                            ListMovieScreen(navController, type = listType)
                        }
                        composable(
                            route = "detailMovie/{movieId}",
                            arguments = listOf(
                                navArgument("movieId"){
                                    type = NavType.IntType
                                }
                            )
                        ) { backStackEntry ->
                            val movieId = backStackEntry.arguments?.getInt("movieId")
                            Log.d("MyLog", "Received movieId: $movieId")
                            DetailMovieScreen(movieId!!, navController)
                        }
                        composable(
                            route = "searchMovie",
                        ) {
                            SearchScreen(navController = navController)
                        }
                        composable(
                            route = "accountDetail"
                        ) { 
                            AccountDetailScreen(navController = navController)
                        }
                        composable(
                            route = "playlist"
                        ) {
                            PlaylistScreen(navController = navController)
                        }
                        composable(
                            route = "playlist/{listId}",
                            arguments = listOf(
                                navArgument("listId") {
                                    type = NavType.IntType
                                }
                            )
                        ) { backStackEntry ->
                            val listId = backStackEntry.arguments?.getInt("listId")
                            if (listId != null) {
                                UserListMovieScreen(navController = navController, listId = listId)
                                Log.d("MyLog", "navigated to playlist: $listId")
                            } else {
                                Log.e("MyLog", "listId is null")
                                Toast.makeText(LocalContext.current, "listId is null", Toast.LENGTH_SHORT).show()
                            }
                        }
                        composable(
                            route = "favourite"
                        ) {
                            FavouriteMovieScreen(navController = navController)
                        }
                        composable(
                            route = "watchlist"
                        ){
                            WatchlistScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        fontFamily = playwrite,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CineManTheme {
        Greeting("Android")
    }
}