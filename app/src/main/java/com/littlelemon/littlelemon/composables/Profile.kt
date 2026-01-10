package com.littlelemon.littlelemon.composables

import android.content.Context
import androidx.compose.foundation.Image
import com.littlelemon.littlelemon.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.littlelemon.littlelemon.navigation.Destinations
import androidx.core.content.edit

@Composable
fun Profile(navController: NavHostController) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("LittleLemon", Context.MODE_PRIVATE)

    val firstName = sharedPreferences.getString("firstName", "") ?: ""
    val lastName = sharedPreferences.getString("lastName", "") ?: ""
    val email = sharedPreferences.getString("email", "") ?: ""

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = "Profile image",
            modifier = Modifier.size(100.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Profile information", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "First name: $firstName")
        Text(text = "Last name: $lastName")
        Text(text = "Email: $email")

        Spacer(modifier = Modifier.weight(1f))

        Button(onClick = { navController.popBackStack() }) {
            Text("Back")
        }

        Button(onClick = {
            sharedPreferences.edit().clear().apply()
            navController.navigate(Destinations.Onboarding.route) {
                popUpTo(Destinations.Home.route) { inclusive = true }
            }
        }) {
            Text("Logout")
        }
    }
}

