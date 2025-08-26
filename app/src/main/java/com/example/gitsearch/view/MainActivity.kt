package com.example.gitsearch.view

import UsersScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = "users"
            ) {
                composable("users") {
                    UsersScreen(
                        onUserClick = { login ->
                            navController.navigate("userDetail/$login")
                        }
                    )
                }

                composable(
                    "userDetail/{login}",
                    arguments = listOf(navArgument("login") { type = NavType.StringType })
                ) { backStackEntry ->
                    val login = backStackEntry.arguments?.getString("login") ?: ""
                    UserDetailScreen(login = login, navController = navController)
                }

            }

        }
    }
}
