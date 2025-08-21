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
import com.shirleen.gearup.ui.screens.profilescreens.ProfileOptionItem
import com.shirleen.gearup.ui.theme.newBlue
import com.shirleen.gearup.ui.theme.newBluu
// Assume ProfileOptionItem is now in a separate common file, so you'll need to import it.
// e.g., import com.shirleen.gearup.ui.components.ProfileOptionItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SellerProfileScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Seller Profile", fontWeight = FontWeight.Bold, color = Color.White) },
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
                // Profile Header with editable business info
                EditableSellerHeader(
                    initialBusinessName = "GearUp Resale",
                    initialEmail = "resale@gearup.com",
                    profilePicResId = R.drawable.corolla
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Settings

                Text("We are so grateful to have you as a seller! Your passion and dedication are what make our marketplace special. Thank you for being a part of our community.", fontSize = 30.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 100.dp), color = Color.LightGray)

                Text("Thank you for choosing", fontSize = 30.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 100.dp), color = Color.LightGray)
                Spacer(modifier = Modifier.height(8.dp))
                Text("GearUp", fontSize = 50.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 90.dp, end = 10.dp), color = Color.LightGray)

            }

            }

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditableSellerHeader(initialBusinessName: String, initialEmail: String, profilePicResId: Int) {
    var isEditing by remember { mutableStateOf(false) }
    var businessName by remember { mutableStateOf(initialBusinessName) }
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
                    value = businessName,
                    onValueChange = { businessName = it },
                    label = { Text("Business Name") },
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
                Text(businessName, fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color.Black)
                Text(email, color = Color.Gray, fontSize = 14.sp)
            }
        }
        IconButton(onClick = {
            isEditing = !isEditing
            if (!isEditing) {
                // Here you would save the data to your database
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
// The ProfileOptionItem composable has been removed from this file.

@Preview(showBackground = true)
@Composable
fun SellerProfileScreenPreview() {
    SellerProfileScreen(rememberNavController())
}