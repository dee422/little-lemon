package com.littlelemon.littlelemon.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.littlelemon.littlelemon.R
import com.littlelemon.littlelemon.navigation.Destinations
import com.littlelemon.littlelemon.viewmodel.MenuViewModel
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions


// ===== Fonts =====
val MarkaziText = FontFamily(
    Font(R.font.markazitext_regular, FontWeight.Normal)
)

val Karla = FontFamily(
    Font(R.font.karla_regular, FontWeight.Normal)
)

@Composable
fun Home(
    navController: NavHostController,
    viewModel: MenuViewModel
) {

    LaunchedEffect(true) {
        viewModel.fetchMenu()
    }

    val menuItems by viewModel.filteredMenuItems.collectAsState(initial = emptyList())
    val searchQuery by viewModel.searchQuery.collectAsState(initial = "")
    val selectedCategory by viewModel.selectedCategory.collectAsState(initial = "All")

    val categories = listOf("All", "Starters", "Mains", "Desserts", "Drinks")

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                focusManager.clearFocus()
            }
    ) {

        // ===== Top Bar =====
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.weight(1f))

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Little Lemon Logo",
                modifier = Modifier.height(40.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "Profile",
                modifier = Modifier
                    .size(40.dp)
                    .clickable {
                        navController.navigate(Destinations.Profile.route)
                    }
            )
        }

        // ===== Hero Section =====
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF495E57))
                .padding(16.dp)
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Little Lemon",
                            fontFamily = MarkaziText,
                            fontSize = 36.sp,
                            color = Color(0xFFF4CE14)
                        )
                        Text(
                            text = "Chicago",
                            fontFamily = MarkaziText,
                            fontSize = 24.sp,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "We are a family-owned Mediterranean restaurant, focused on traditional recipes served with a modern twist.",
                            fontFamily = Karla,
                            fontSize = 16.sp,
                            color = Color.White
                        )
                    }

                    Image(
                        painter = painterResource(id = R.drawable.hero_image),
                        contentDescription = "Hero Image",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // ===== Search Bar =====
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFF2F2F2))
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = Color.Gray
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    BasicTextField(
                        value = searchQuery,
                        onValueChange = { viewModel.onSearchQueryChange(it) },
                        singleLine = true,
                        textStyle = TextStyle(
                            fontFamily = Karla,
                            fontSize = 16.sp,
                            color = Color.Black
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Search
                        ),
                        keyboardActions = KeyboardActions(
                            onSearch = {
                                focusManager.clearFocus()
                                keyboardController?.hide()
                            },
                            onDone = {
                                focusManager.clearFocus()
                                keyboardController?.hide()
                            }
                        ),
                        decorationBox = { innerTextField ->
                            if (searchQuery.isEmpty()) {
                                Text(
                                    text = "Enter search phrase",
                                    color = Color.Gray,
                                    fontFamily = Karla
                                )
                            }
                            innerTextField()
                        }
                    )

                }

            }
        }

        // ===== Order Section =====
        Text(
            text = "ORDER FOR DELIVERY!",
            fontFamily = Karla,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(16.dp)
        )

        // ===== Category Chips =====
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            items(categories) { category ->
                FilterChip(
                    selected = selectedCategory == category,
                    onClick = { viewModel.onCategorySelected(category) },
                    label = {
                        Text(
                            text = category,
                            fontFamily = Karla
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // ===== Menu List =====
        if (menuItems.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No menu items found",
                    fontFamily = Karla
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(menuItems) { item ->
                    MenuItemCard(item = item)
                }
            }
        }
    }
}
