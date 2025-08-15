package com.shirleen.gearup.ui.screens.profilescreens

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuyerProfileScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Profile", fontWeight = FontWeight.Bold, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = newBluu)
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
                // Profile Header - Now includes editing logic
                EditableProfileHeader(
                    initialName = "John Doe",
                    initialEmail = "john.doe@example.com",
                    profilePicResId = R.drawable.corolla
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Account Actions
                Text("Account", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column {
                        ProfileOptionItem(
                            icon = Icons.Default.ShoppingCart,
                            title = "My Orders",
                            onClick = { /* Navigate to orders screen */ }
                        )
                        Divider()
                        ProfileOptionItem(
                            icon = Icons.Default.Build,
                            title = "My Service Bookings",
                            onClick = { /* Navigate to bookings screen */ }
                        )
                        Divider()
                        ProfileOptionItem(
                            icon = Icons.Default.Event,
                            title = "My Appointments",
                            onClick = { /* Navigate to appointments screen */ }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Settings
                Text("Settings", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column {
                        ProfileOptionItem(
                            icon = Icons.Default.Settings,
                            title = "Account Settings",
                            onClick = { /* Navigate to settings */ }
                        )
                        Divider()
                        ProfileOptionItem(
                            icon = Icons.Default.ExitToApp,
                            title = "Logout",
                            onClick = { /* Handle logout */ }
                        )
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditableProfileHeader(initialName: String, initialEmail: String, profilePicResId: Int) {
    var isEditing by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf(initialName) }
    var email by remember { mutableStateOf(initialEmail) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray.copy(alpha = 0.2f), RoundedCornerShape(12.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = profilePicResId),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            if (isEditing) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                Text(name, fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color.Black)
                Text(email, color = Color.Gray, fontSize = 14.sp)
            }
        }
        IconButton(onClick = {
            isEditing = !isEditing
            if (!isEditing) {
                // Here you would save the data to your database
                // e.g., viewModel.saveProfile(name, email)
            }
        }) {
            Icon(
                imageVector = if (isEditing) Icons.Default.Check else Icons.Default.Edit,
                contentDescription = if (isEditing) "Save" else "Edit",
                tint = newBlue
            )
        }
    }
}

@Composable
fun ProfileOptionItem(icon: androidx.compose.ui.graphics.vector.ImageVector, title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = newBlue,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(title, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = "Navigate",
            tint = Color.Gray
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BuyerProfileScreenPreview() {
    BuyerProfileScreen(rememberNavController())
}