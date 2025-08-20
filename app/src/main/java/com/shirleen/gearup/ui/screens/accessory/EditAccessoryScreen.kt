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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
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
import com.shirleen.gearup.navigation.ROUT_ADD_ACCESSORY
import com.shirleen.gearup.navigation.ROUT_ACCESSORY_LIST
import com.shirleen.gearup.ui.theme.newBluu
import com.shirleen.gearup.viewmodel.AccessoryViewModel
import androidx.compose.runtime.collectAsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditAccessoryScreen(accessoryId: Int?, navController: NavController, viewModel: AccessoryViewModel) {
    val context = LocalContext.current
    val allAccessories by viewModel.allAccessories.collectAsState(initial = emptyList())

    // Find the accessory by ID
    val accessory = allAccessories.find { it.id == accessoryId }

    // Editable states
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf("") }
    var showMenu by remember { mutableStateOf(false) }

    // Update states when accessory is loaded
    LaunchedEffect(accessory) {
        accessory?.let {
            name = it.name
            description = it.description
            price = it.price
            phone = it.phone
            imageUri = it.imageUri
        }
    }

    // Image picker
    val imagePicker = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            imageUri = it.toString()
            Toast.makeText(context, "Image Selected!", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Accessory", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                actions = {
                    IconButton(onClick = { showMenu = true }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Menu", tint = Color.White)
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Accessory List") },
                            onClick = {
                                navController.navigate(ROUT_ACCESSORY_LIST)
                                showMenu = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Add Accessory") },
                            onClick = {
                                navController.navigate(ROUT_ADD_ACCESSORY)
                                showMenu = false
                            }
                        )
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = newBluu)
            )
        },
        bottomBar = { BottomNavigationBarAccessory(navController) }
    ) { paddingValues ->
        if (accessory != null) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
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
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
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
                        value = phone,
                        onValueChange = { phone = it },
                        label = { Text("Phone Number") },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = newBluu,
                            focusedLabelColor = newBluu,
                            cursorColor = newBluu
                        )
                    )
                }
                item {
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
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    imageVector = Icons.Default.AddAPhoto,
                                    contentDescription = "Pick Image",
                                    tint = Color.DarkGray
                                )
                                Text("Tap to pick image", color = Color.DarkGray)
                            }
                        }
                    }
                }
                item {
                    Button(
                        onClick = {
                            val updatedAccessory = accessory.copy(
                                name = name,
                                description = description,
                                price = price,
                                phone = phone,
                                imageUri = imageUri
                            )
                            viewModel.updateAccessory(updatedAccessory)
                            Toast.makeText(context, "Accessory Updated!", Toast.LENGTH_SHORT).show()
                            navController.navigate(ROUT_ACCESSORY_LIST) {
                                popUpTo(ROUT_ACCESSORY_LIST) { inclusive = true }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 40.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = newBluu)
                    ) {
                        Text("Update Accessory", color = Color.White)
                    }
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Accessory not found", color = MaterialTheme.colorScheme.error)
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { navController.popBackStack() }) {
                    Text("Go Back")
                }
            }
        }
    }
}
