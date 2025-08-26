package com.shirleen.gearup.ui.screens.dashboards

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.shirleen.gearup.ui.theme.newBlue
import com.shirleen.gearup.ui.theme.newBluu
import com.shirleen.gearup.R
import com.shirleen.gearup.navigation.ROUT_BUYACCESSORY
import com.shirleen.gearup.navigation.ROUT_BUYCAR
import com.shirleen.gearup.navigation.ROUT_CART
import androidx.compose.ui.layout.ContentScale
import com.shirleen.gearup.navigation.ROUT_ABOUT

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuyerDashboardScreen(navController: NavController) {
    var selectedIndex by remember { mutableStateOf(0) }

    // Sample data for Featured Cars - Use a data class
    val featuredCars = remember {
        listOf(
            FeaturedItem("Toyota Corolla 2023", R.drawable.corolla, "Ksh3,200,000"),
            FeaturedItem("Honda Civic Sport", R.drawable.hondacivic, "Ksh2,500,000"),
            FeaturedItem("Ford Mustang GT", R.drawable.mustanggt, "Ksh6,000,000"),
            FeaturedItem("BMW X5", R.drawable.bmwx5, "Ksh8,500,000")
        )
    }

    // Sample data for Featured Accessories - Use the same data class
    val featuredAccessories = remember {
        listOf(
            FeaturedItem("Car Charger", R.drawable.carcharger, "Ksh1,500"),
            FeaturedItem("Floor Mats", R.drawable.floormats, "Ksh5,000"),
            FeaturedItem("Car Seat Cover", R.drawable.leathercover, "Ksh10,000"),
            FeaturedItem("Phone Mount", R.drawable.phonemount, "Ksh1,200")
        )
    }

    val navBarColors = NavigationBarItemDefaults.colors(
        selectedIconColor = newBlue,
        selectedTextColor = newBlue,
        unselectedIconColor = Color.White,
        unselectedTextColor = Color.White
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "GearUp",
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        fontSize = 28.sp,
                        letterSpacing = 1.sp
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = newBluu
                ),
                actions = {
                    // This space intentionally left blank as per the user request to remove notifications icon
                }
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = newBluu,
                modifier = Modifier.shadow(elevation = 16.dp)
            ) {
                NavigationBarItem(
                    icon = {
                        Icon(
                            Icons.Default.Home,
                            contentDescription = "Home",
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    label = { Text("Home") },
                    selected = selectedIndex == 0,
                    onClick = { selectedIndex = 0 },
                    colors = navBarColors
                )
                NavigationBarItem(
                    icon = {
                        Icon(
                            Icons.Default.ShoppingCart,
                            contentDescription = "Cart",
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    label = { Text("Cart") },
                    selected = selectedIndex == 1,
                    onClick = {
                        navController.navigate(ROUT_CART)
                    },
                    colors = navBarColors
                )
            }
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(Color(0xFFF8F9FA))
            ) {
                // Header with greeting
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(newBluu, newBlue)
                                )
                            )
                            .padding(24.dp)
                    ) {
                        Text(
                            text = "Welcome!",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            "Find your perfect car and accessories",
                            fontSize = 16.sp,
                            color = Color.White.copy(alpha = 0.9f),
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }

                // Quick Actions
                item {
                    Text(
                        "Quick Actions",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 24.dp, top = 24.dp, end = 24.dp, bottom = 16.dp),
                        color = Color.Black
                    )
                }
                item {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        val quickActions = listOf(
                            QuickAction("Buy a Car", Icons.Default.DirectionsCar, newBlue,
                                ROUT_BUYCAR
                            ),
                            QuickAction("Accessories", Icons.Default.Build, newBlue,
                                ROUT_BUYACCESSORY
                            ),

                            QuickAction("About", Icons.Default.Info, newBlue,
                                ROUT_ABOUT
                            )
                        )

                        quickActions.forEach { (title, icon, color, route) ->
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .width(120.dp)
                                    .clickable {
                                        if (route.isNotEmpty()) {
                                            navController.navigate(route)
                                        }
                                    }
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(70.dp)
                                        .shadow(8.dp, CircleShape, spotColor = color.copy(alpha = 0.3f))
                                        .background(Color.White, CircleShape)
                                        .clip(CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        icon,
                                        contentDescription = title,
                                        tint = color,
                                        modifier = Modifier.size(32.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    title,
                                    fontSize = 14.sp,
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.SemiBold,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis,
                                    color = Color.DarkGray
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(32.dp))
                }

                // Featured Cars Section
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Featured Cars",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(
                            "View All",
                            fontSize = 14.sp,
                            color = newBlue,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier
                                .clickable { navController.navigate(ROUT_BUYCAR) }
                                .padding(8.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
                item {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(featuredCars) { car ->
                            FeaturedItemCard(item = car)
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }

                // Featured Accessories Section
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Featured Accessories",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(
                            "View All",
                            fontSize = 14.sp,
                            color = newBlue,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier
                                .clickable { navController.navigate(ROUT_BUYACCESSORY) }
                                .padding(8.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
                item {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(featuredAccessories) { accessory ->
                            FeaturedItemCard(item = accessory)
                        }
                    }
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }
    )
}

@Composable
fun FeaturedItemCard(item: FeaturedItem) {
    Card(
        modifier = Modifier
            .width(280.dp)
            .height(220.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp,
            pressedElevation = 4.dp,
            focusedElevation = 12.dp
        ),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = item.imageResId),
                contentDescription = item.name,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Gradient overlay for better text readability
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f)),
                            startY = 300f
                        )
                    )
            )

            // Item info
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    item.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    item.price,
                    fontSize = 14.sp,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

data class FeaturedItem(
    val name: String,
    val imageResId: Int,
    val price: String
)

data class QuickAction(
    val title: String,
    val icon: ImageVector,
    val color: Color,
    val route: String
)

@Preview(showBackground = true)
@Composable
fun BuyerDashboardScreenPreview() {
    BuyerDashboardScreen(rememberNavController())
}