package com.littlelemon.littlelemon.navigation

sealed class Destinations(val route: String) {
    object Home : Destinations("home")
    object Profile : Destinations("profile")
    object Onboarding : Destinations("onboarding")
}
