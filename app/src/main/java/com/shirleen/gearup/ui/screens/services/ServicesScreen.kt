package com.shirleen.gearup.ui.screens.services

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.shirleen.gearup.R
import com.shirleen.gearup.navigation.ROUT_BOOKAPPOINTMENT
import com.shirleen.gearup.ui.screens.cars.BottomNavigationBar
import com.shirleen.gearup.ui.theme.newBlue
import com.shirleen.gearup.ui.theme.newBluu

// Data classes for services and categories
data class Service(val name: String, val description: String, val imageRes: Int)
data class ServiceCategory(val name: String, val icon: ImageVector)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServicesScreen(navController: NavController) {

    Scaffold(
        bottomBar = {
            // Reusing your existing BottomNavigationBar component
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
                title = { Text("Car Services", fontWeight = FontWeight.Bold, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = newBluu
                )
            )

            // Search Bar
            OutlinedTextField(
                value = "",
                onValueChange = {},
                placeholder = { Text("Search for a service...") },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = null)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(50)
            )

            // Service Categories
            Text(
                "Service Categories",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                val serviceCategories = listOf(
                    ServiceCategory("Maintenance", Icons.Default.Build),
                    ServiceCategory("Repairs", Icons.Default.Construction),
                    ServiceCategory("Body Work", Icons.Default.DirectionsCar),
                    ServiceCategory("Tire Services", Icons.Default.LocalGasStation),
                    ServiceCategory("Detailing", Icons.Default.Star),
                    ServiceCategory("Oil Change", Icons.Default.OilBarrel),
                    ServiceCategory("Brake Check", Icons.Default.Settings)
                )
                items(serviceCategories) { category ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .clickable { /* Handle category click */ }
                    ) {
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .background(newBlue.copy(alpha = 0.1f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = category.icon,
                                contentDescription = category.name,
                                tint = newBlue,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(category.name, fontSize = 12.sp, textAlign = TextAlign.Center)
                    }
                }
            }

            // Featured Services
            Text(
                "Featured Service Providers",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            // List of services
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                val services = listOf(
                    Service("GearUp Auto Repair", "Expert diagnostics and engine repair", R.drawable.corolla), // Placeholder image
                    Service("Tire Haven", "Quick tire change and wheel alignment", R.drawable.xtrail), // Placeholder image
                    Service("Elite Bodyworks", "Accident repair and custom paint jobs", R.drawable.demio) // Placeholder image
                )
                items(services) { service ->
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(4.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable { /* Navigate to service provider details */ }
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = service.imageRes),
                                contentDescription = service.name,
                                modifier = Modifier
                                    .size(80.dp)
                                    .background(Color.LightGray, RoundedCornerShape(8.dp))
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(service.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                                Text(
                                    service.description,
                                    color = Color.Gray,
                                    fontSize = 14.sp,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                            Button(
                                onClick = { navController.navigate(ROUT_BOOKAPPOINTMENT) },
                                colors = ButtonDefaults.buttonColors(newBlue)
                            ) {
                                Text("Book")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ServicesScreenPreview() {
    ServicesScreen(rememberNavController())
}