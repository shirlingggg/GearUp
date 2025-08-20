package com.shirleen.gearup.ui.screens.accessory

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.shirleen.gearup.model.Accessory
import com.shirleen.gearup.navigation.ROUT_ADD_ACCESSORY
import com.shirleen.gearup.navigation.ROUT_ACCESSORY_LIST
import com.shirleen.gearup.ui.theme.newBluu
import com.shirleen.gearup.viewmodel.AccessoryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAccessoryScreen(navController: NavController, viewModel: AccessoryViewModel) {
    val context = LocalContext.current

    // State variables to hold the input values
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf("") }

    // Launcher for selecting an image from the gallery
    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            imageUri = it.toString()
            Toast.makeText(context, "Image selected", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Accessory", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = newBluu)
            )
        },
        bottomBar = { BottomNavigationBarAccessory(navController) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Accessory Name") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = newBluu,
                        focusedLabelColor = newBluu,
                        cursorColor = newBluu
                    )
                )
            }
            item {
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = newBluu,
                        focusedLabelColor = newBluu,
                        cursorColor = newBluu
                    )
                )
            }
            item {
                OutlinedTextField(
                    value = price,
                    onValueChange = { price = it },
                    label = { Text("Price") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = newBluu,
                        focusedLabelColor = newBluu,
                        cursorColor = newBluu
                    )
                )
            }
            item {
                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Phone Number") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = newBluu,
                        focusedLabelColor = newBluu,
                        cursorColor = newBluu
                    )
                )
            }
            item {
                // Image Picker Box with Placeholder
                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .background(Color.LightGray, shape = RoundedCornerShape(10.dp))
                        .clickable { imagePicker.launch("image/*") },
                    contentAlignment = Alignment.Center
                ) {
                    if (imageUri.isNotEmpty()) {
                        Image(
                            painter = rememberAsyncImagePainter(model = Uri.parse(imageUri)),
                            contentDescription = "Accessory Image",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.AddAPhoto,
                                contentDescription = "Pick Image",
                                tint = Color.DarkGray,
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Tap to pick image",
                                color = Color.DarkGray,
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                    }
                }
            }
            item {
                Button(
                    onClick = {
                        if (name.isNotBlank() && price.isNotBlank()) {
                            val newAccessory = Accessory(
                                name = name,
                                description = description,
                                price = price,
                                phone = phone,
                                imageUri = imageUri
                            )
                            viewModel.addAccessory(newAccessory)
                            Toast.makeText(context, "Accessory added!", Toast.LENGTH_SHORT).show()
                            navController.navigate(ROUT_ACCESSORY_LIST) { // redirect
                                popUpTo(ROUT_ADD_ACCESSORY) { inclusive = true }
                            }
                        } else {
                            Toast.makeText(context, "Please fill in required fields", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = newBluu)
                ) {
                    Text("Add Accessory", color = Color.White)
                }
            }
        }
    }
}

// Reusable Bottom Navigation Bar for accessories screens
@Composable
fun BottomNavigationBarAccessory(navController: NavController) {
    NavigationBar(
        containerColor = newBluu,
        contentColor = Color.White
    ) {
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(ROUT_ACCESSORY_LIST) },
            icon = { Icon(Icons.Default.Home, contentDescription = "Accessory List", tint = Color.White) },
            label = { Text("Accessories", color = Color.White) }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(ROUT_ADD_ACCESSORY) },
            icon = { Icon(Icons.Default.AddCircle, contentDescription = "Add Accessory", tint = Color.White) },
            label = { Text("Add", color = Color.White) }
        )
    }
}
