package com.littlelemon.littlelemon

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.littlelemon.littlelemon.database.DatabaseProvider
import com.littlelemon.littlelemon.navigation.Destinations
import com.littlelemon.littlelemon.navigation.NavigationComposable
import com.littlelemon.littlelemon.repository.MenuRepository
import com.littlelemon.littlelemon.ui.theme.LittleLemonTheme
import com.littlelemon.littlelemon.viewmodel.MenuViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LittleLemonTheme {

                val navController = rememberNavController()
                val context = this

                val isLoggedIn = remember {
                    val sharedPref = context.getSharedPreferences("LittleLemon", Context.MODE_PRIVATE)
                    sharedPref.getBoolean("isLoggedIn", false)
                }

                val startDestination = if (isLoggedIn) {
                    Destinations.Home.route
                } else {
                    Destinations.Onboarding.route
                }

                NavigationComposable(
                    navController = navController,

                    startDestination = startDestination
                )
            }
        }

    }
}
