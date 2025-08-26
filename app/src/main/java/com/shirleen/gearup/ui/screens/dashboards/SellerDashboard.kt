package com.shirleen.gearup.ui.screens.dashboards

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.shirleen.gearup.R
import com.shirleen.gearup.navigation.ROUT_ABOUT
import com.shirleen.gearup.navigation.ROUT_ADD_ACCESSORY
import com.shirleen.gearup.navigation.ROUT_ADD_CAR
import com.shirleen.gearup.navigation.ROUT_SELLERDASHBOARD
import com.shirleen.gearup.navigation.ROUT_SELLERPROFILESCREEN
import com.shirleen.gearup.ui.theme.newBlue
import com.shirleen.gearup.ui.theme.newBluu

// Data classes to represent a seller's listing (can be a car or accessory)
data class SellerListing(val name: String, val price: String, val imageRes: Int, val isCar: Boolean)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SellerDashboardScreen(navController: NavController) {

    // Dummy data for listings
    val myListings = listOf(
        SellerListing("Toyota Corolla", "KSh 1,200,000", R.drawable.corolla, true),
        SellerListing("LED Headlights", "KSh 8,500", R.drawable.ledlights, false),
        SellerListing("Nissan X-Trail", "KSh 2,400,000", R.drawable.xtrail, true),
        SellerListing("Floor Mats", "KSh 5,000", R.drawable.floormats, false),
        SellerListing("Car Charger", "KSh 1,500", R.drawable.carcharger, false)
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Seller Dashboard",
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 24.sp
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.2f))
                    ) {
                        Icon(

                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp),

                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = newBluu
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = newBluu,
                modifier = Modifier.shadow(elevation = 16.dp)
            ) {
                // Home/Dashboard Icon
                NavigationBarItem(
                    icon = {
                        Icon(
                            Icons.Default.Home,
                            contentDescription = "Dashboard",
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    label = { Text("Dashboard") },
                    selected = currentRoute == ROUT_SELLERDASHBOARD,
                    onClick = {
                        if (currentRoute != ROUT_SELLERDASHBOARD) {
                            navController.navigate(ROUT_SELLERDASHBOARD) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = newBlue,
                        unselectedIconColor = Color.White,
                        selectedTextColor = newBlue,
                        unselectedTextColor = Color.White
                    )
                )
            }
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color(0xFFF8F9FA))
            ) {
                // Header Section
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(newBluu, newBlue)
                                )
                            )
                            .padding(24.dp)
                    ) {
                        Column {
                            Text(
                                "Manage Your Listings",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                "Track and add new items to sell",
                                fontSize = 14.sp,
                                color = Color.White.copy(alpha = 0.9f),
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                }

                // Quick Actions to Add New Items
                item {
                    Text(
                        "Add New Listing",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 24.dp, top = 24.dp, end = 24.dp, bottom = 16.dp),
                        color = Color.Black
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .horizontalScroll(rememberScrollState()),

                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        QuickActionCard(
                            title = "Sell a Car",
                            icon = Icons.Default.DirectionsCar,
                            onClick = { navController.navigate(ROUT_ADD_CAR) }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        QuickActionCard(
                            title = "Add Accessory",
                            icon = Icons.Default.Build, // Changed from ShoppingCart to Build
                            onClick = { navController.navigate(ROUT_ADD_ACCESSORY) }
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        QuickActionCard(
                            title = "About us",
                            icon = Icons.Default.Info,
                            onClick = { navController.navigate(ROUT_ABOUT) }
                        )

                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }

                // My Listings Section
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "My Listings",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(
                            "${myListings.size} Items",
                            fontSize = 14.sp,
                            color = Color.Gray,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // List of current listings
                items(myListings) { listing ->
                    ListingCard(listing)
                    Spacer(modifier = Modifier.height(8.dp))
                }

                item {
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }
    )
}

@Composable
fun QuickActionCard(title: String, icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .height(120.dp)
            .clickable(onClick = onClick)
            .shadow(8.dp, RoundedCornerShape(16.dp), spotColor = newBlue.copy(alpha = 0.3f)),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(newBlue.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = newBlue,
                    modifier = Modifier.size(28.dp)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                title,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.DarkGray,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ListingCard(listing: SellerListing) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Image with better styling
            Image(
                painter = painterResource(id = listing.imageRes),
                contentDescription = listing.name,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.LightGray),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    listing.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    listing.price,
                    color = newBlue,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                // Add a tag to show if it's a car or accessory
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (listing.isCar) newBlue.copy(alpha = 0.1f) else Color.Green.copy(alpha = 0.1f))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        if (listing.isCar) "CAR" else "ACCESSORY",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (listing.isCar) newBlue else newBlue
                    )
                }
            }


        }
    }
}

@Preview(showBackground = true)
@Composable
fun SellerDashboardScreenPreview() {
    SellerDashboardScreen(rememberNavController())
}