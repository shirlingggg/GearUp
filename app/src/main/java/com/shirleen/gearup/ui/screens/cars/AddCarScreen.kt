package com.shirleen.gearup.ui.screens.cars

import android.net.Uri
import android.util.Log
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
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.shirleen.gearup.R
import com.shirleen.gearup.model.Car
import com.shirleen.gearup.navigation.ROUT_ADD_CAR
import com.shirleen.gearup.navigation.ROUT_CAR_LIST
import com.shirleen.gearup.ui.theme.newBluu
import com.shirleen.gearup.viewmodel.CarViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCarScreen(navController: NavController, viewModel: CarViewModel) {
    var brand by remember { mutableStateOf("") }
    var model by remember { mutableStateOf("") }
    var yearOfManufacture by remember { mutableStateOf("") }
    var mileage by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var showMenu by remember { mutableStateOf(false) }

    val context = LocalContext.current

    // Image Picker Launcher
    val imagePicker = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            imageUri = it
            Log.d("ImagePicker", "Selected image URI: $it")
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Car", fontSize = 20.sp, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = newBluu, titleContentColor = Color.White),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = "Back", tint = Color.White)
                    }
                },
                actions = {
                    IconButton(onClick = { showMenu = true }) {
                        Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Menu", tint = Color.White)
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
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(navController)
        },
        containerColor = Color.LightGray,
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item {
                    // Car Brand
                    OutlinedTextField(
                        value = brand,
                        onValueChange = { brand = it },
                        label = { Text("Brand") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = newBluu,
                            focusedLabelColor = newBluu,
                            cursorColor = newBluu
                        )
                    )
                }

                item {
                    // Car model
                    OutlinedTextField(
                        value = model,
                        onValueChange = { model = it },
                        label = { Text("Model") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = newBluu,
                            focusedLabelColor = newBluu,
                            cursorColor = newBluu
                        )
                    )
                }

                item {
                    // Car Price
                    OutlinedTextField(
                        value = price,
                        onValueChange = { price = it },
                        label = { Text("Car Price") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = newBluu,
                            focusedLabelColor = newBluu,
                            cursorColor = newBluu
                        )
                    )
                }

                item {
                    // Year of manufacture
                    OutlinedTextField(
                        value = yearOfManufacture,
                        onValueChange = { yearOfManufacture = it },
                        label = { Text("Year of Manufacture") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = newBluu,
                            focusedLabelColor = newBluu,
                            cursorColor = newBluu
                        )
                    )
                }

                item {
                    //Mileage
                    OutlinedTextField(
                        value = mileage,
                        onValueChange = { mileage = it },
                        label = { Text("Mileage") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = newBluu,
                            focusedLabelColor = newBluu,
                            cursorColor = newBluu
                        )
                    )
                }

                item {
                    // Phone Number
                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = { Text("Phone Number") },
                        leadingIcon = { Icon(painter = painterResource(R.drawable.phone), contentDescription = "phone") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = newBluu,
                            focusedLabelColor = newBluu,
                            cursorColor = newBluu
                        )
                    )
                }



                item {
                    Spacer(modifier = Modifier.height(6.dp))
                }

                item {
                    // Image Picker Box
                    Box(
                        modifier = Modifier
                            .size(200.dp)
                            .background(Color.LightGray, shape = RoundedCornerShape(10.dp))
                            .clickable { imagePicker.launch("image/*") },
                        contentAlignment = Alignment.Center
                    ) {
                        if (imageUri != null) {
                            Image(
                                painter = rememberAsyncImagePainter(model = imageUri),
                                contentDescription = "Selected Image",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(painter = painterResource(R.drawable.image), contentDescription = "Pick Image")
                                Text("Tap to pick image", color = Color.DarkGray)
                            }
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(10.dp))
                }

                item {
                    // Correctly formed Add Car Button
                    Button(
                        onClick = {
                            val newCar = Car(
                                id = 0, // Room will generate this
                                brand = brand,
                                model = model,
                                yearOfManufacture = yearOfManufacture,
                                mileage = mileage,
                                price = price,
                                phone = phone,
                                imageUri = imageUri.toString()
                            )
                            viewModel.addCar(newCar)

                            Toast.makeText(context, "Car has been added!", Toast.LENGTH_SHORT).show()

                            navController.navigate(ROUT_CAR_LIST) {
                                popUpTo(ROUT_ADD_CAR) { inclusive = true }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = newBluu)
                    ) {
                        Text("Add Car", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    )
}


// Bottom Navigation Bar Component
@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar(
        containerColor = newBluu,
        contentColor = Color.White,
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(ROUT_CAR_LIST) },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Car List",
                    tint = Color.White
                )
            },
            label = { Text("Car List", color = Color.White) }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(ROUT_ADD_CAR) },
            icon = {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = "Add Car",
                    tint = Color.White
                )
            },
            label = { Text("Add", color = Color.White) }
        )
    }
}