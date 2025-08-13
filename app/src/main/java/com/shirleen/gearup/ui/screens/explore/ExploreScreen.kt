package com.shirleen.gearup.ui.screens.explore

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.shirleen.gearup.R
import com.shirleen.gearup.navigation.ROUT_BUYERDASHBOARD
import com.shirleen.gearup.navigation.ROUT_BUYCAR
import com.shirleen.gearup.ui.screens.buycar.BottomNavigationBar
import com.shirleen.gearup.ui.theme.newBlue
import com.shirleen.gearup.ui.theme.newBluu
import com.shirleen.gearup.navigation.ROUT_SERVICES
import com.shirleen.gearup.navigation.ROUT_ACCESSORIES

// A generic data class to represent a discoverable item
data class ExploreItem(
    val title: String,
    val imageRes: Int,
    val route: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen(navController: NavController) {

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
        ) {
            item {
                Text(
                    "Explore",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
                )
            }

            // Trending Cars Section
            item {
                Text(
                    "Trending Cars",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
                )
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp)
                ) {
                    val trendingCars = listOf(
                        ExploreItem("Trending Car 1", R.drawable.corolla, ROUT_BUYCAR),
                        ExploreItem("Trending Car 2", R.drawable.xtrail, ROUT_BUYCAR)
                    )
                    items(trendingCars) { car ->
                        TrendingItemCard(car, navController)
                    }
                }
            }

            // Popular Services Section
            item {
                Text(
                    "Popular Services",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
                )
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp)
                ) {
                    val popularServices = listOf(
                        ExploreItem("Oil Change", R.drawable.oilchange, ROUT_SERVICES),
                        ExploreItem("Brake Check", R.drawable.brakecheck, ROUT_SERVICES)
                    )
                    items(popularServices) { service ->
                        TrendingItemCard(service, navController)
                    }
                }
            }

            // Popular Accessories Section
            item {
                Text(
                    "Popular Accessories",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
                )
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp)
                ) {
                    val popularAccessories = listOf(
                        ExploreItem("Phone Mount", R.drawable.phonemount, ROUT_ACCESSORIES),
                        ExploreItem("Car Wax Kit", R.drawable.carwaxkit, ROUT_ACCESSORIES)
                    )
                    items(popularAccessories) { accessory ->
                        TrendingItemCard(accessory, navController)
                    }
                }
            }

            // You can add more sections here for curated lists, etc.
        }
    }
}

@Composable
fun TrendingItemCard(item: ExploreItem, navController: NavController) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .height(140.dp)
            .padding(end = 12.dp)
            .clickable { navController.navigate(item.route) },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column {
            Image(
                painter = painterResource(id = item.imageRes),
                contentDescription = item.title,
                modifier = Modifier
                    .height(90.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                item.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExploreScreenPreview() {
    ExploreScreen(rememberNavController())
}