package com.example.taskb

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.taskb.ui.screens.MainScreen
import com.example.taskb.ui.screens.UserDetailsScreen
import com.example.taskb.ui.theme.TaskBTheme
import com.example.taskb.ui.viewmodel.MainViewModel
import com.example.taskb.ui.viewmodel.UserDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskBTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val mainViewModel = hiltViewModel<MainViewModel>()
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = Routes.Home.route) {
                        composable(Routes.Home.route) {
                            MainScreen(mainViewModel)
                                { login -> navController.navigate("user_details/$login") }
                        }
                        composable(Routes.Repos.route) {
                            val userDetailsViewModel = hiltViewModel<UserDetailsViewModel>()
                            UserDetailsScreen(userDetailsViewModel) { htmlUrl ->
                                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(htmlUrl)))
                            }
                        }
                    }
                }
            }
        }
    }
}

sealed class Routes(val route: String) {
    object Home: Routes("main")
    object Repos: Routes("user_details/{login}")
}