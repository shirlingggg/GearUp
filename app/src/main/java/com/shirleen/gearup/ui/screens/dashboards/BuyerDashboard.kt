package com.shirleen.gearup.ui.screens.dashboards

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Garage
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.shirleen.gearup.navigation.ROUT_ACCESSORIES
import com.shirleen.gearup.navigation.ROUT_BOOKAPPOINTMENT
import com.shirleen.gearup.navigation.ROUT_BUYCAR
import com.shirleen.gearup.navigation.ROUT_BUYERPROFILESCREEN
import com.shirleen.gearup.navigation.ROUT_EXPLORE
import com.shirleen.gearup.navigation.ROUT_SERVICES
import com.shirleen.gearup.ui.theme.newBlue
import com.shirleen.gearup.ui.theme.newBluu

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuyerDashboardScreen(navController: NavController) {
    var selectedIndex by remember { mutableStateOf(0) }

    val navBarColors = NavigationBarItemDefaults.colors(
        selectedIconColor = newBlue,
        selectedTextColor = newBlue,
        unselectedIconColor = Color.White,
        unselectedTextColor = Color.White
    )

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = newBluu
            ) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = selectedIndex == 0,
                    onClick = { selectedIndex = 0 },
                    colors = navBarColors
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Explore, contentDescription = "Explore") },
                    label = { Text("Explore") },
                    selected = selectedIndex == 1,
                    onClick = { navController.navigate(ROUT_EXPLORE) },
                    colors = navBarColors
                )

                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                    label = { Text("Profile") },
                    selected = selectedIndex == 3,
                    onClick = { navController.navigate(ROUT_BUYERPROFILESCREEN) },
                    colors = navBarColors
                )
            }
        },

        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                // Hero Section
                item {
                    Text(
                        text = "Welcome to GearUp",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp).padding(top = 20.dp),
                        color = newBlue
                    )
                    Text(
                        "Your one-stop hub for cars, services & accessories!",
                        fontSize = 17.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 20.dp, bottom = 20.dp)
                    )
                }

                // Quick Actions
                item {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Corrected the QuickAction list to use the QuickAction data class for all items.
                        // A placeholder route "" is used for actions without a specific navigation target.
                        val quickActions = listOf(
                            QuickAction("Buy a Car", Icons.Default.DirectionsCar, newBlue, ROUT_BUYCAR),
                            QuickAction("Services", Icons.Default.Build, newBlue, ROUT_SERVICES),
                            QuickAction("Accessories", Icons.Default.ShoppingCart, newBlue, ROUT_ACCESSORIES),
                            QuickAction("Book", Icons.Default.Event, newBlue, ROUT_BOOKAPPOINTMENT)
                        )
                        // Destructuring is now correct as all items are of the same type.
                        quickActions.forEach { (title, icon, color, route) ->
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .width(70.dp)
                                    // Use navController to navigate when a route is defined.
                                    .clickable {
                                        if (route.isNotEmpty()) {
                                            navController.navigate(route)
                                        }
                                    }
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(50.dp)
                                        .background(color.copy(alpha = 0.1f), CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(icon, contentDescription = title, tint = color)
                                }
                                Text(title, fontSize = 12.sp, textAlign = TextAlign.Center)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }

                // Featured Cars
                item {
                    Text(
                        "Featured Cars",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )
                }
                item {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp)
                    ) {
                        val sampleCars = listOf("Car Model A", "Car Model B", "Car Model C")
                        items(sampleCars) { car ->
                            Card(
                                modifier = Modifier
                                    .width(200.dp)
                                    .padding(end = 10.dp),
                                shape = RoundedCornerShape(12.dp),
                                elevation = CardDefaults.cardElevation(4.dp)
                            ) {
                                Column {
                                    Box(
                                        modifier = Modifier
                                            .height(120.dp)
                                            .fillMaxWidth()
                                            .background(Color.LightGray),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text("Image", color = Color.DarkGray)
                                    }
                                    Text(
                                        car,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(8.dp)
                                    )
                                    Text(
                                        "$25,000",
                                        color = newBlue,
                                        modifier = Modifier.padding(horizontal = 8.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                // Recommended for You
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        "Recommended for You",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )
                }
                items(3) { index ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 8.dp),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(80.dp)
                                    .background(Color.LightGray),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("Img")
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text("Service or Car ${index + 1}", fontWeight = FontWeight.Bold)
                                Text("Brief description...", fontSize = 12.sp, color = Color.Gray)
                            }
                        }
                    }
                }
            }
        }

    )
}

// Data class is already correct
data class QuickAction(val title: String, val icon: androidx.compose.ui.graphics.vector.ImageVector, val color: Color, val route: String )


@Preview(showBackground = true)
@Composable
fun BuyerDashboardScreenPreview() {
    BuyerDashboardScreen(rememberNavController())
}