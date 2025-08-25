package com.shirleen.gearup.ui.screens.buyitems

import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.shirleen.gearup.model.Accessory
import com.shirleen.gearup.model.CartItem
import com.shirleen.gearup.navigation.ROUT_CART
import com.shirleen.gearup.ui.theme.newBluu
import com.shirleen.gearup.viewmodel.AccessoryViewModel
import com.shirleen.gearup.viewmodel.CartViewModel // Import the CartViewModel

@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuyAccessoriesScreen(
    viewModel: AccessoryViewModel,
    navController: NavController,
    cartViewModel: CartViewModel // Add the cartViewModel as a parameter
) {
    val allAccessories by viewModel.allAccessories.collectAsState(initial = emptyList())
    var searchQuery by remember { mutableStateOf("") }

    val filteredAccessories = allAccessories.filter {
        it.name.contains(searchQuery, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text("Accessories", fontSize = 20.sp) },
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

                //Search Bar
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 10.dp, end = 10.dp),
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White,
                ) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        placeholder = { Text("Search accessories...") },
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

                if (filteredAccessories.isEmpty()) {
                    Text(
                        text = "Oops! We don't have that.",
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(Alignment.Center),
                        textAlign = TextAlign.Center,
                        color = Color.Gray
                    )
                } else {
                    LazyColumn(
                        contentPadding = PaddingValues(bottom = 80.dp)
                    ) {
                        items(filteredAccessories) { accessory ->
                            BuyerAccessoryItem(navController, accessory, cartViewModel) // Pass cartViewModel here
                        }
                    }
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
            if (filteredAccessories.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "No accessories found.")
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(bottom = 80.dp)
                ) {
                    items(filteredAccessories) { accessory ->
                        BuyerAccessoryItem(navController, accessory, cartViewModel) // Pass cartViewModel here
                    }
                }
            }
        }
    }
}

@Composable
fun BuyerAccessoryItem(
    navController: NavController,
    accessory: Accessory,
    cartViewModel: CartViewModel // Add cartViewModel as a parameter
) {
    val painter = rememberAsyncImagePainter(model = accessory.imageUri)
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
                contentDescription = "Accessory Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Text(text = accessory.name, fontSize = 20.sp, color = Color.DarkGray)
                Text(text = accessory.description, fontSize = 16.sp, color = Color.DarkGray)
                Text(text = "Ksh ${accessory.price}", fontSize = 16.sp, color = Color.DarkGray)
                Text(text = "Seller: ${accessory.phone}", fontSize = 16.sp, color = Color.DarkGray)

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Message Seller (buyers only)
                    Button(
                        onClick = {
                            val smsIntent = Intent(Intent.ACTION_SENDTO)
                            smsIntent.data = "smsto:${accessory.phone}".toUri()
                            smsIntent.putExtra(
                                "sms_body",
                                "Hello, I'm interested in ${accessory.name}, could you tell me more about it..."
                            )
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
                                id = accessory.id,
                                name = accessory.name,
                                description = accessory.description,
                                price = accessory.price,
                                phone = accessory.phone,
                                imageUri = accessory.imageUri,
                                quantity = 1
                            )
                            cartViewModel.insert(cartItem)
                            navController.navigate(ROUT_CART)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Shopping cart",
                            tint = newBluu
                        )
                    }
                }
            }
        }
    }
}