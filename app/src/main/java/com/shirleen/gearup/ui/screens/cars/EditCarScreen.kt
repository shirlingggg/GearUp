package com.shirleen.gearup.ui.screens.cars

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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.shirleen.gearup.navigation.ROUT_ADD_CAR
import com.shirleen.gearup.navigation.ROUT_CAR_LIST
import com.shirleen.gearup.viewmodel.CarViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.shirleen.gearup.ui.theme.newBluu
import com.shirleen.gearup.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditCarScreen(carId: Int?, navController: NavController, viewModel: CarViewModel) {
    val context = LocalContext.current
    val allCars by viewModel.cars.collectAsState()

    val car = remember(allCars) { allCars.find { it.id == carId } }

    var brand by remember { mutableStateOf(car?.brand ?: "") }
    var model by remember { mutableStateOf(car?.model ?: "") }
    var yearOfManufacture by remember { mutableStateOf(car?.yearOfManufacture ?: "") }
    var mileage by remember { mutableStateOf(car?.mileage ?: "") }
    var price by remember { mutableStateOf(car?.price ?: "") }
    var phone by remember { mutableStateOf(car?.phone ?: "") }
    var imageUri by remember { mutableStateOf(car?.imageUri ?: "") }
    var showMenu by remember { mutableStateOf(false) }

    val imagePicker = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            imageUri = it.toString()
            Toast.makeText(context, "Image Selected!", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Car", color = Color.White) },
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
                            text = { Text("Car List") },
                            onClick = {
                                navController.navigate(ROUT_CAR_LIST)
                                showMenu = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Add Car") },
                            onClick = {
                                navController.navigate(ROUT_ADD_CAR)
                                showMenu = false
                            }
                        )
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = newBluu)
            )
        },
        bottomBar = { BottomNavigationBar2(navController) }
    ) { paddingValues ->
        if (car != null) {
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
                        value = brand,
                        onValueChange = { brand = it },
                        label = { Text("Car Brand") },
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
                        value = model,
                        onValueChange = { model = it },
                        label = { Text("Car Model") },
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
                        value = yearOfManufacture,
                        onValueChange = { yearOfManufacture = it },
                        label = { Text("Year of Manufacture") },
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
                        value = mileage,
                        onValueChange = { mileage = it },
                        label = { Text("Car Mileage") },
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
                        value = price,
                        onValueChange = { price = it },
                        label = { Text("Car Price") },
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
                                contentDescription = "Car Image",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop

                            )


                        } else {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    imageVector = Icons.Default.Add,
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
                            val updatedCar = car.copy(
                                brand = brand,
                                model = model,
                                yearOfManufacture = yearOfManufacture,
                                mileage = mileage,
                                price = price,
                                phone = phone,
                                imageUri = imageUri
                            )
                            viewModel.updateCar(updatedCar)
                            Toast.makeText(context, "Car Updated!", Toast.LENGTH_SHORT).show()
                            navController.popBackStack()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 40.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = newBluu)
                    ) {
                        Text("Update Car", color = Color.White)
                    }
                }
            }
        } else {
            // This block will be displayed if the car object is null
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Car not found", color = MaterialTheme.colorScheme.error)
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { navController.popBackStack() }) {
                    Text("Go Back")
                }
            }
        }
    }
}

// Bottom Navigation Bar
@Composable
fun BottomNavigationBar2(navController: NavController) {
    NavigationBar(
        containerColor = newBluu,
        contentColor = Color.White
    ) {
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(ROUT_CAR_LIST) },
            icon = { Icon(Icons.Default.Home, contentDescription = "Car List", tint = Color.White) },
            label = { Text("Car List", color = Color.White) }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(ROUT_ADD_CAR) },
            icon = { Icon(Icons.Default.AddCircle, contentDescription = "Add Car", tint = Color.White) },
            label = { Text("Add Car", color = Color.White) }
        )
    }
}