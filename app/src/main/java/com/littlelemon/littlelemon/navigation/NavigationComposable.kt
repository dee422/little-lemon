package com.littlelemon.littlelemon.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.littlelemon.littlelemon.composables.Home
import com.littlelemon.littlelemon.composables.Onboarding
import com.littlelemon.littlelemon.composables.Profile
import com.littlelemon.littlelemon.viewmodel.MenuViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.littlelemon.littlelemon.database.DatabaseProvider
import com.littlelemon.littlelemon.repository.MenuRepository
import com.littlelemon.littlelemon.viewmodel.MenuViewModelFactory

@Composable
fun NavigationComposable(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Destinations.Onboarding.route) {
            Onboarding(navController)
        }

        composable(Destinations.Home.route) {

            val context = LocalContext.current
            val database = remember {
                DatabaseProvider.getDatabase(context)
            }
            val repository = remember {
                MenuRepository(database)
            }

            val menuViewModel: MenuViewModel = viewModel(
                factory = MenuViewModelFactory(repository)
            )

            Home(navController = navController, viewModel = menuViewModel)
        }


        composable(Destinations.Profile.route) {
            Profile(navController)
        }
    }
}


