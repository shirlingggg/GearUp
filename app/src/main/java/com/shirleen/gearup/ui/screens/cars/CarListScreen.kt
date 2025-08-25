package com.shirleen.gearup.ui.screens.cars

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.shirleen.gearup.model.Car
import com.shirleen.gearup.navigation.ROUT_ADD_CAR
import com.shirleen.gearup.navigation.ROUT_CAR_LIST
import com.shirleen.gearup.navigation.editCarRoute
import com.shirleen.gearup.viewmodel.CarViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import com.shirleen.gearup.navigation.ROUT_SELLERDASHBOARD
import com.shirleen.gearup.ui.theme.newBluu

@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarListScreen(navController: NavController, viewModel: CarViewModel) {
    val carList by viewModel.cars.collectAsState()
    var showMenu by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    val filteredCars = carList.filter {
        it.name.contains(searchQuery, ignoreCase = true) ||
                it.price.contains(searchQuery, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text("Cars", fontSize = 20.sp) },
                    colors = TopAppBarDefaults.mediumTopAppBarColors(
                        containerColor = newBluu,
                        titleContentColor = Color.White
                    ),
                    actions = {
                        IconButton(onClick = { showMenu = true }) {
                            Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Menu", tint = Color.White)
                        }
                        DropdownMenu(
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Dashboard") },
                                onClick = {
                                    navController.navigate(ROUT_SELLERDASHBOARD)
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

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 10.dp, end = 10.dp)
                        .shadow(8.dp, RoundedCornerShape(16.dp)),
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White,
                ) {
                    //Search Bar
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        placeholder = { Text("Search cars...") },
                        singleLine = true,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search",
                                tint = newBluu
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color.White,
                            focusedBorderColor = Color.White,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.DarkGray
                        )
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                //OOPS
                if (filteredCars.isEmpty()) {
                    Text(
                        text = "Oops! We don't have that.",
                        modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center),
                        textAlign = TextAlign.Center,
                        color = Color.Gray
                    )
                }
            }
        },
        bottomBar = { BottomNavigationBar1(navController) },
        containerColor = Color.LightGray
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            LazyColumn(
                contentPadding = PaddingValues(bottom = 80.dp) // ✅ ensures last item is fully visible
            ) {
                items(filteredCars) { car ->
                    CarItem(navController, car, viewModel)
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun CarItem(navController: NavController, car: Car, viewModel: CarViewModel) {
    val painter: Painter = rememberAsyncImagePainter(
        model = car.imageUri?.let { Uri.parse(it) } ?: Uri.EMPTY
    )
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                if (car.id != 0) {
                    navController.navigate(editCarRoute(car.id))
                }
            },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F0F0))
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Product Image
            Image(
                painter = painter,
                contentDescription = "Car Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )

            // Car Info and Buttons
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                // Car Details
                Text(
                    text = car.name,
                    fontSize = 17.sp,
                    color = Color.DarkGray
                )

                Text(
                    text = car.description,
                    fontSize = 16.sp,
                    color = Color.DarkGray
                )


                Text(
                    text = "Ksh ${car.price}",
                    fontSize = 16.sp,
                    color = Color.DarkGray
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Buttons (Message, Edit, Delete)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Message Seller
                    Button(
                        onClick = {
                            val smsIntent = Intent(Intent.ACTION_SENDTO)
                            smsIntent.data = "smsto:${car.phone}".toUri()
                            smsIntent.putExtra("sms_body", "Hello Seller, I'm interested in your ${car.name}")
                            context.startActivity(smsIntent)
                        },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = newBluu,
                            contentColor = Color.White
                        )
                    ) {
                        Row {
                            Icon(
                                imageVector = Icons.Default.Send,
                                contentDescription = "Message Seller"
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(text = "Message Seller")
                        }
                    }

                    // Edit Car
                    IconButton(
                        onClick = {
                            navController.navigate(editCarRoute(car.id))
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit",
                            tint = newBluu
                        )
                    }

                    // Delete Car
                    IconButton(
                        onClick = { viewModel.deleteCar(car) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = Color.Red
                        )
                    }
                }
            }
        }
    }
}

// Bottom Navigation Bar Component
@Composable
fun BottomNavigationBar1(navController: NavController) {
    NavigationBar(
        containerColor = newBluu,
        contentColor = Color.White
    ) {
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(ROUT_CAR_LIST) },
            icon = { Icon(Icons.Default.Home, contentDescription = "Car List") },
            label = { Text("Home") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                unselectedIconColor = Color.White,
                selectedTextColor = Color.White,
                unselectedTextColor = Color.White
            )
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(ROUT_ADD_CAR) },
            icon = { Icon(Icons.Default.AddCircle, contentDescription = "Add Car") },
            label = { Text("Add") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                unselectedIconColor = Color.White,
                selectedTextColor = Color.White,
                unselectedTextColor = Color.White
            )
        )
    }
}