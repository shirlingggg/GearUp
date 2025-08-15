package com.shirleen.gearup.ui.screens.profilescreens


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.shirleen.gearup.R
import com.shirleen.gearup.ui.theme.newBlue
import com.shirleen.gearup.ui.theme.newBluu


// Data class to represent a service
data class Service(val name: String, val icon: ImageVector)

// List of all available services for the provider
val allServices = listOf(
    Service("Full Car Service", Icons.Default.Build),
    Service("Oil Change", Icons.Default.OilBarrel),
    Service("Brakes, Battery, Tires Check", Icons.Default.CheckCircleOutline),
    Service("Diagnostics", Icons.Default.MonitorHeart),
    Service("AC Service", Icons.Default.AcUnit)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceProviderProfileScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Service Provider Profile", fontWeight = FontWeight.Bold, color = Color.White) },
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
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                // Profile Information Section
                ProfileInfoSection()
                Spacer(modifier = Modifier.height(24.dp))

                // Services Selection Section
                ServicesSection()
                Spacer(modifier = Modifier.weight(1f))

                // Save Button
                Button(
                    onClick = { /* Save profile to database */ },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = newBlue)
                ) {
                    Text("Save Profile", color = Color.White)
                }
            }
        }
    )
}

@Composable
fun ProfileInfoSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Placeholder for profile picture
            Image(
                painter = painterResource(id = R.drawable.corolla),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    "Garage XYZ",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.Black
                )
                Text(
                    "Service Provider",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun ServicesSection() {
    Text(
        "Offered Services",
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(bottom = 8.dp)
    )

    // Mutable state to track selected services
    var selectedServices by remember { mutableStateOf(setOf<String>()) }

    Column(modifier = Modifier.fillMaxWidth()) {
        allServices.forEach { service ->
            val isSelected = selectedServices.contains(service.name)
            ServiceToggleItem(
                service = service,
                isSelected = isSelected,
                onClick = {
                    selectedServices = if (isSelected) {
                        selectedServices - service.name
                    } else {
                        selectedServices + service.name
                    }
                }
            )
        }
    }
}

@Composable
fun ServiceToggleItem(service: Service, isSelected: Boolean, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) newBlue.copy(alpha = 0.2f) else Color.White
        ),
        border = if (isSelected) BorderStroke(2.dp, newBlue) else null,
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = service.icon,
                contentDescription = service.name,
                tint = newBlue,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                service.name,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = if (isSelected) newBlue else Color.Black
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ServiceProviderProfileScreenPreview() {
    ServiceProviderProfileScreen(rememberNavController())
}