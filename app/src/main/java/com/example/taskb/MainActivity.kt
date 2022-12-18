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
import com.example.taskb.ui.screens.UserReposScreen
import com.example.taskb.ui.screens.UsersScreen
import com.example.taskb.ui.theme.TaskBTheme
import com.example.taskb.ui.viewmodel.UserReposViewModel
import com.example.taskb.ui.viewmodel.UsersViewModel
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
                    val usersViewModel = hiltViewModel<UsersViewModel>()
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = Routes.Home.route) {
                        composable(Routes.Home.route) {
                            UsersScreen(usersViewModel)
                                { login -> navController.navigate("repo/$login") }
                        }
                        composable(Routes.Repos.route) {
                            val userReposViewModel = hiltViewModel<UserReposViewModel>()
                            UserReposScreen(userReposViewModel) { htmlUrl ->
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
    object Home: Routes("home")
    object Repos: Routes("repo/{login}")
}