package com.shirleen.gearup.ui.screens.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.shirleen.gearup.model.CartItem
import com.shirleen.gearup.navigation.ROUT_CHECKOUT
import com.shirleen.gearup.ui.theme.newBluu
import com.shirleen.gearup.viewmodel.CartViewModel
import androidx.navigation.NavController
import com.shirleen.gearup.navigation.ROUT_CART

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    navController: NavController,
    cartViewModel: CartViewModel = viewModel()
) {
    val cartItems by cartViewModel.allCartItems.observeAsState(emptyList())
    val totalPrice by cartViewModel.totalPrice.observeAsState(0.0)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("My Cart", color = Color.White)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = newBluu
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .background(color = Color(0xFFF8F9FA))
        ) {
            if (cartItems.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Your cart is empty")
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    items(cartItems) { item ->
                        CartItemRow(
                            cartItem = item,
                            onRemove = { cartViewModel.delete(item) },
                            onIncrease = {
                                val updated = item.copy(quantity = item.quantity + 1)
                                cartViewModel.update(updated)
                            },
                            onDecrease = {
                                if (item.quantity > 1) {
                                    val updated = item.copy(quantity = item.quantity - 1)
                                    cartViewModel.update(updated)
                                }
                            }
                        )
                    }
                }

                // Total section styled
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = newBluu.copy(alpha = 0.1f)
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Total", style = MaterialTheme.typography.titleMedium, color = newBluu)
                        Text(
                            "KSh ${"%.2f".format(totalPrice)}",
                            style = MaterialTheme.typography.titleMedium,
                            color = newBluu
                        )
                    }
                }

                // Checkout button - lifted up a bit
                Button(
                    onClick = { navController.navigate(ROUT_CHECKOUT) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = newBluu,
                        contentColor = Color.White
                    )
                ) {
                    Text("Proceed to Checkout")
                }
            }
        }
    }
}

@Composable
fun CartItemRow(
    cartItem: CartItem,
    onRemove: () -> Unit,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Product image
            Image(
                painter = rememberAsyncImagePainter(cartItem.imageUri),
                contentDescription = cartItem.name,
                modifier = Modifier
                    .size(70.dp)
                    .padding(end = 12.dp),
                contentScale = ContentScale.Crop
            )

            // Name + price
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    cartItem.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    "KSh ${cartItem.price}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }

            // Quantity controls
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onDecrease) {
                    Icon(Icons.Default.Remove, contentDescription = "Decrease")
                }
                Text(
                    "${cartItem.quantity}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(horizontal = 6.dp)
                )
                IconButton(onClick = onIncrease) {
                    Icon(Icons.Default.Add, contentDescription = "Increase")
                }
            }

            // Remove button
            IconButton(onClick = onRemove) {
                Icon(Icons.Default.Delete, contentDescription = "Remove", tint = Color.Red)
            }
        }
    }
}
