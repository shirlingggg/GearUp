package com.shirleen.gearup.ui.screens.dashboards

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.shirleen.gearup.R
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
            TopAppBar(
                title = { Text("Seller Dashboard", fontWeight = FontWeight.Bold, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = newBluu
                )
            )
        },
        bottomBar = {
            NavigationBar(containerColor = newBluu) {
                // Home/Dashboard Icon
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Dashboard") },
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
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                // Quick Actions to Add New Items
                item {
                    Text(
                        "Add New Listing",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        QuickActionCard(
                            title = "Sell a Car",
                            icon = Icons.Default.DirectionsCar,
                            onClick = { navController.navigate(ROUT_ADD_CAR) }
                        )
                        QuickActionCard(
                            title = "Add Accessory",
                            icon = Icons.Default.ShoppingCart,
                            onClick = { navController.navigate(ROUT_ADD_ACCESSORY) }
                        )
                    }
                }

                // My Listings Section
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        "My Listings",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                // List of current listings
                items(myListings) { listing ->
                    ListingCard(listing)
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
            .height(100.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = newBlue,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(title, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
fun ListingCard(listing: SellerListing) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Use the image resource ID from the listing data
            Image(
                painter = painterResource(id = listing.imageRes),
                contentDescription = listing.name,
                modifier = Modifier
                    .size(80.dp)
                    .background(Color.LightGray, RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(listing.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(
                    listing.price,
                    color = newBlue,
                    fontWeight = FontWeight.Bold
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun SellerDashboardScreenPreview() {
    SellerDashboardScreen(rememberNavController())
}
