package com.littlelemon.littlelemon.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.littlelemon.littlelemon.composables.Home
import com.littlelemon.littlelemon.composables.Onboarding
import com.littlelemon.littlelemon.composables.Profile

@Composable
fun NavigationComposable() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Destinations.Onboarding.route
    ) {

        composable(Destinations.Onboarding.route) {
            Onboarding(navController)
        }

        composable(Destinations.Home.route) {
            Home(navController)
        }

        composable(Destinations.Profile.route) {
            Profile(navController)
        }
    }
}
