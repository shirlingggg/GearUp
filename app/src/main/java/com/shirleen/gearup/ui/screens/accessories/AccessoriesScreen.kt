package com.shirleen.gearup.ui.screens.accessories

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.shirleen.gearup.R
import com.shirleen.gearup.ui.screens.buycar.BottomNavigationBar
import com.shirleen.gearup.ui.theme.newBlue
import com.shirleen.gearup.ui.theme.newBluu

// Data class for an accessory item
data class Accessory(
    val name: String,
    val price: String,
    val imageRes: Int,
    val category: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccessoriesScreen(navController: NavController) {

    // Define the categories and state to track the selected one
    val categories = listOf("Interior", "Exterior", "Lighting", "Safety", "Car Care")
    var selectedCategory by remember { mutableStateOf(categories[0]) }

    // Dummy data for accessories
    val accessoriesList = listOf(
        // Interior
        Accessory("Leather Seat Cover", "KSh 3,200", R.drawable.leathercover, "Interior"),
        Accessory("Phone Mount", "KSh 1,200", R.drawable.phonemount, "Interior"),
        // Exterior
        Accessory("Full Car Cover", "KSh 2,800", R.drawable.fullcarcover, "Exterior"),
        Accessory("Alloy Rims (Set of 4)", "KSh 25,000", R.drawable.alloyrims, "Exterior"),
        // Lighting
        Accessory("LED Headlights", "KSh 8,500", R.drawable.ledlights, "Lighting"),
        Accessory("Underbody Glow", "KSh 4,500", R.drawable.underbodyglow, "Lighting"),
        // Safety
        Accessory("Warning Triangle", "KSh 500", R.drawable.warningtriangle, "Safety"),
        Accessory("Jumper Cables", "KSh 1,800", R.drawable.jumpercables, "Safety"),
        // Car Care
        Accessory("Car Wax Kit", "KSh 2,000", R.drawable.carwaxkit, "Car Care"),
        Accessory("Portable Vacuum", "KSh 5,000", R.drawable.vacuumcleaner, "Car Care"),
    )

    // Filter accessories based on the selected category
    val filteredAccessories = accessoriesList.filter { it.category == selectedCategory }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding)
        ) {
            // Top Bar
            TopAppBar(
                title = { Text("Car Accessories", fontWeight = FontWeight.Bold, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = newBluu
                )
            )

            // Search Bar (Optional, but good for consistency)
            OutlinedTextField(
                value = "",
                onValueChange = {},
                placeholder = { Text("Search accessories...") },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = null)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(50)
            )

            // Horizontal Tab/Category Bar
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(categories) { category ->
                    FilterChip(
                        selected = (category == selectedCategory),
                        onClick = { selectedCategory = category },
                        label = { Text(category) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = newBluu,
                            selectedLabelColor = Color.White,
                            containerColor = Color.LightGray.copy(alpha = 0.5f)
                        )
                    )
                }
            }

            // Grid for Accessories
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                items(filteredAccessories) { accessory ->
                    AccessoryCard(accessory)
                }
            }
        }
    }
}

@Composable
fun AccessoryCard(accessory: Accessory) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = accessory.imageRes),
                contentDescription = accessory.name,
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
                    .background(Color.LightGray, RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = accessory.name,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
            Text(
                text = accessory.price,
                color = newBlue,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
            Button(
                onClick = { /* Add to Cart */ },
                modifier = Modifier.padding(top = 8.dp),
                colors = ButtonDefaults.buttonColors(newBluu),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Icon(Icons.Default.ShoppingCart, contentDescription = "Add to Cart", modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Add", fontSize = 12.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AccessoriesScreenPreview() {
    AccessoriesScreen(rememberNavController())
}