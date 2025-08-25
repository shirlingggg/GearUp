package com.shirleen.gearup.ui.screens.buyitems

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
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
import com.shirleen.gearup.model.CartItem
import com.shirleen.gearup.viewmodel.CarViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import com.shirleen.gearup.navigation.ROUT_CART
import com.shirleen.gearup.ui.theme.newBluu
import com.shirleen.gearup.viewmodel.CartViewModel // Import the CartViewModel

@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuyCarScreen(
    navController: NavController,
    viewModel: CarViewModel,
    cartViewModel: CartViewModel // Add cartViewModel as a parameter
) {
    val carList by viewModel.cars.collectAsState()
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
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White
                            )
                        }
                    },
                    actions = {
                        // Cart Button
                        IconButton(onClick = { navController.navigate(ROUT_CART) }) {
                            Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "Cart", tint = Color.White)
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
        containerColor = Color.LightGray
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            LazyColumn(
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                items(filteredCars) { car ->
                    BuyCarItem(navController, car, viewModel, cartViewModel) // Pass the cartViewModel
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun BuyCarItem(
    navController: NavController,
    car: Car,
    viewModel: CarViewModel,
    cartViewModel: CartViewModel // Add cartViewModel as a parameter
) {
    val painter: Painter = rememberAsyncImagePainter(
        model = car.imageUri?.let { Uri.parse(it) } ?: Uri.EMPTY
    )
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F0F0))
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painter,
                contentDescription = "Car Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
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

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
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
                    Spacer(modifier = Modifier.height(10.dp))

                    // Add to Cart
                    Text(text = "Add To cart", modifier = Modifier.padding(top = 17.dp))
                    IconButton(
                        onClick = {
                            val cartItem = CartItem(
                                id = car.id,
                                name = car.name,
                                description = car.description,
                                price = car.price,
                                phone = car.phone,
                                imageUri = car.imageUri,
                                quantity = 1
                            )
                            cartViewModel.insert(cartItem)
                            navController.navigate(ROUT_CART)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Add to Cart",
                            tint = newBluu
                        )
                    }
                }
            }
        }
    }
}