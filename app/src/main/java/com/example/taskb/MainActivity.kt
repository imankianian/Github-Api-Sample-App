package com.example.taskb

import Motrack
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
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
import com.motrack.sdk.MotrackEvent
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
                                { login ->
                                    Log.d("Motrack", "id clicked")
                                    val event = MotrackEvent("FrDh5HKm")
                                    Motrack.trackEvent(event)
                                    navController.navigate("user_details/$login") }
                        }
                        composable(Routes.Repos.route) {
                            val userDetailsViewModel = hiltViewModel<UserDetailsViewModel>()
                            UserDetailsScreen(userDetailsViewModel, { htmlUrl ->
                                Log.d("Motrack", "user repo clicked")
                                val revenueEvent = MotrackEvent("2AvzWR4d")
                                Motrack.trackEvent(revenueEvent)
                                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(htmlUrl)))
                            }) {
                                navController.navigateUp()
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