package com.shirleen.gearup.ui.screens.dashboards

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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.shirleen.gearup.navigation.ROUT_SERVICEPROVIDERDASHBOARD
import com.shirleen.gearup.navigation.ROUT_SERVICEPROVIDERPROFILESCREEN
import com.shirleen.gearup.ui.theme.newBlue
import com.shirleen.gearup.ui.theme.newBluu

// Assuming QuickActionCard is now in a separate file,
// so this import will correctly link to it.
import com.shirleen.gearup.ui.screens.dashboards.QuickActionCard

// Data classes for demonstration
data class Appointment(val clientName: String, val service: String, val time: String)
data class Metric(val title: String, val value: String, val icon: androidx.compose.ui.graphics.vector.ImageVector)

// Define the bottom navigation items for the service provider
data class SPBottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceProviderDashboardScreen(navController: NavController) {

    // Dummy data for the dashboard
    val upcomingAppointments = listOf(
        Appointment("John Doe", "Oil Change", "10:00 AM"),
        Appointment("Jane Smith", "Full Car Service", "02:30 PM")
    )

    val keyMetrics = listOf(
        Metric("Appointments Today", "2", Icons.Default.EventAvailable),
        Metric("Earnings This Month", "Ksh 50,000", Icons.Default.Payments)
    )

    val navBarItems = listOf(
        SPBottomNavItem("Home", Icons.Default.Home, ROUT_SERVICEPROVIDERDASHBOARD),
        SPBottomNavItem("Profile", Icons.Default.Person, ROUT_SERVICEPROVIDERPROFILESCREEN)
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dashboard", fontWeight = FontWeight.Bold, color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = newBluu)
            )
        },

        bottomBar = {
            NavigationBar(containerColor = newBluu) {
                navBarItems.forEach { item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) },
                        selected = currentRoute == item.route,
                        onClick = {
                            if (currentRoute != item.route) {
                                navController.navigate(item.route) {
                                    // Pop up to the start destination to avoid a growing back stack
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    // Avoids recreating the same screen multiple times
                                    launchSingleTop = true
                                    // Restores state when re-selecting a previously selected item
                                    restoreState = true
                                }
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = newBlue,
                            selectedTextColor = newBlue,
                            unselectedIconColor = Color.White,
                            unselectedTextColor = Color.White
                        )
                    )
                }
            }
        },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
                .padding(16.dp)
        ) {
            // Welcome Section
            item {
                Text(
                    "Welcome, Garage XYZ!",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = newBlue,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            // Key Metrics
            item {
                Text("Your Performance", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(bottom = 8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    keyMetrics.forEach { metric ->
                        MetricCard(metric = metric)
                    }
                }
            }

            // Upcoming Appointments
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Text("Upcoming Appointments", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(bottom = 8.dp))
            }
            items(upcomingAppointments) { appointment ->
                AppointmentCard(appointment = appointment)
            }

            // Quick Actions
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Text("Quick Actions", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(bottom = 8.dp))
            }
            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    QuickActionCard(
                        title = "Manage Services",
                        icon = Icons.Default.Build,
                        onClick = { navController.navigate("ROUT_MANAGE_SERVICES") }
                    )
                    QuickActionCard(
                        title = "Messages",
                        icon = Icons.Default.Mail,
                        onClick = { navController.navigate("ROUT_MESSAGES") }
                    )
                }
            }
        }
    }
}

@Composable
fun MetricCard(metric: Metric) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .height(100.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = newBluu)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Icon(metric.icon, contentDescription = metric.title, tint = newBlue, modifier = Modifier.size(24.dp))
            Text(metric.title, color = Color.White, fontSize = 12.sp)
            Text(metric.value, fontWeight = FontWeight.Bold, color = newBlue, fontSize = 16.sp)
        }
    }
}

@Composable
fun AppointmentCard(appointment: Appointment) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { /* View appointment details */ },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Schedule, contentDescription = "Time", tint = newBlue, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(appointment.service, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text("Client: ${appointment.clientName}", fontSize = 14.sp)
            }
            Text(appointment.time, color = newBlue, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ServiceProviderDashboardPreview() {
    ServiceProviderDashboardScreen(rememberNavController())
}