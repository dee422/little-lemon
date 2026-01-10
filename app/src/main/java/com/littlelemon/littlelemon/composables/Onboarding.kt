package com.littlelemon.littlelemon.composables

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.littlelemon.littlelemon.R
import com.littlelemon.littlelemon.navigation.Destinations
import com.littlelemon.littlelemon.ui.theme.LittleLemonTheme
import android.util.Patterns

@Composable
fun Onboarding(navController: NavHostController) {

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    val isFirstNameValid = firstName.isNotBlank()
    val isLastNameValid = lastName.isNotBlank()
    val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    var errorMessage by remember { mutableStateOf("") }
    var successMessage by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                focusManager.clearFocus()
            }
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(WindowInsets.statusBars.asPaddingValues())
        ) {

            // ===== Top Logo =====
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Little Lemon Logo",
                    modifier = Modifier.height(40.dp)
                )
            }

            // ===== Green Header =====
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF495E57))
                    .padding(vertical = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Let's get to know you",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineSmall
                )
            }

            // ===== Form Section =====
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {

                Text(
                    text = "Personal information",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(text = "First name", style = MaterialTheme.typography.titleSmall)

                OutlinedTextField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    label = { Text("First name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                )

                Text(text = "Last name", style = MaterialTheme.typography.titleSmall)

                OutlinedTextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    label = { Text("Last name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                )

                Text(text = "Email", style = MaterialTheme.typography.titleSmall)

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp)
                )

                // ===== Error Message =====
                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                // ===== Success Message =====
                if (successMessage.isNotEmpty()) {
                    Text(
                        text = successMessage,
                        color = Color(0xFF2E7D32),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                // ===== Register Button =====
                Button(
                    onClick = {
                        focusManager.clearFocus()

                        if (!isFirstNameValid || !isLastNameValid || !isEmailValid) {
                            errorMessage = "Please enter valid personal information"
                        } else {
                            val sharedPref = context.getSharedPreferences(
                                "LittleLemon",
                                Context.MODE_PRIVATE
                            )
                            with(sharedPref.edit()) {
                                putString("firstName", firstName)
                                putString("lastName", lastName)
                                putString("email", email)
                                putBoolean("isLoggedIn", true)
                                apply()
                            }

                            errorMessage = ""
                            successMessage = "Registration successful!"

                            navController.navigate(Destinations.Home.route) {
                                popUpTo(Destinations.Onboarding.route) { inclusive = true }
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF4CE14),
                        contentColor = Color.Black
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text(text = "Register")
                }

            } // Form Column end
        } // Main Column end
    } // Box end
}

@Preview(showBackground = true)
@Composable
fun OnboardingPreview() {
    LittleLemonTheme {
        // Preview 无法传 NavHostController，这里留空即可
    }
}
