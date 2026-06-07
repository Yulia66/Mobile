package com.example.myapplication.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.ui.screen.MainScreen
import com.example.myapplication.ui.screen.SearchScreen
import com.example.myapplication.ui.screen.SettingsScreen

@Composable
fun AppNavigation(hostController: NavHostController) {
    NavHost(
        navController = hostController,
        startDestination = Destinations.HOME.route
    ) {
        composable(Destinations.HOME.route) {
            MainScreen(
                onSearchClick = { moveToSearch(hostController) },
                onSettingsClick = { moveToSettings(hostController) }
            )
        }
        
        composable(Destinations.SEARCH.route) {
            SearchScreen(
                onBackClick = { hostController.popBackStack() }
            )
        }
        
        composable(Destinations.SETTINGS.route) {
            SettingsScreen(
                onBackClick = { hostController.popBackStack() }
            )
        }
    }
}

private fun moveToSearch(navCtrl: NavController) {
    navCtrl.navigate(Destinations.SEARCH.route)
}

private fun moveToSettings(navCtrl: NavController) {
    navCtrl.navigate(Destinations.SETTINGS.route)
}

sealed class Destinations(val route: String) {
    data object HOME : Destinations("main_screen")
    data object SEARCH : Destinations("search_screen")
    data object SETTINGS : Destinations("settings_screen")
}