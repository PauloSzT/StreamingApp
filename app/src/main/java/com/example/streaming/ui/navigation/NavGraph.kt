package com.example.streaming.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.streaming.ui.home.HomeScreen
import com.example.streaming.ui.mediaplayer.MediaPlayerScreen

@Composable
fun NavigationGraph(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navHostController,
        startDestination = NavItem.HomeScreen.route,
        modifier = modifier.fillMaxSize()
    ) {
        composable(
            route = NavItem.MediaPlayerScreen.route,
            arguments = listOf(navArgument("songId") { type = NavType.IntType })
        ) {
            MediaPlayerScreen()
        }
        composable(route = NavItem.HomeScreen.route) {
            HomeScreen(navHostController = navHostController)
        }
    }
}
