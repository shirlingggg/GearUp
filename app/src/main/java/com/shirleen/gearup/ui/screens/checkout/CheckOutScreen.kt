package com.shirleen.gearup.ui.screens.checkout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.shirleen.gearup.navigation.ROUT_CAR_LIST
import com.shirleen.gearup.navigation.ROUT_PAYMENT
import com.shirleen.gearup.ui.theme.newBlue
import com.shirleen.gearup.ui.theme.newBluu
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckOutScreen(navController: NavController) {
    val mContext = LocalContext.current
    var showCashMessage by remember { mutableStateOf(false) }
    var isMpesaProcessing by remember { mutableStateOf(false) }

    // Use a LaunchedEffect to handle the mock payment process
    if (isMpesaProcessing) {
        LaunchedEffect(Unit) {
            // Simulate API call delay
            delay(3000)
            isMpesaProcessing = false
            // Navigate to payment success screen
            navController.navigate(ROUT_PAYMENT) {
                // Clear the back stack so user can't go back to checkout
                popUpTo(navController.currentBackStackEntry?.destination?.route ?: "checkout") {
                    inclusive = true
                }
            }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Checkout",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = newBluu
                ),
                navigationIcon = {
                    IconButton(onClick = {
                        if (!isMpesaProcessing) {
                            navController.popBackStack()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                }
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF8F9FA))
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                if (isMpesaProcessing) {
                    // Show processing UI
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(24.dp)
                    ) {
                        CircularProgressIndicator(
                            color = newBlue,
                            modifier = Modifier.size(64.dp)
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            text = "Processing M-Pesa Payment",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = newBluu
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Please wait while we process your payment...",
                            fontSize = 16.sp,
                            color = Color.DarkGray,
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    // Show payment options
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Header
                        Text(
                            text = "Complete Your Purchase",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = newBluu,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        Text(
                            text = "Choose your preferred payment method",
                            fontSize = 16.sp,
                            color = Color.DarkGray,
                            modifier = Modifier.padding(bottom = 32.dp)
                        )

                        // Payment method cards
                        PaymentMethodCard(
                            title = "M-Pesa",
                            description = "Pay securely with M-Pesa",
                            onClick = { isMpesaProcessing = true },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        )

                        PaymentMethodCard(
                            title = "Cash on Delivery",
                            description = "Pay when you receive your items",
                            onClick = { showCashMessage = true },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        )
                    }
                }

                // Cash payment dialog
                if (showCashMessage) {
                    AlertDialog(
                        onDismissRequest = {
                            showCashMessage = false
                        },
                        title = {
                            Text(
                                text = "Cash Payment",
                                fontWeight = FontWeight.Bold,
                                color = newBluu
                            )
                        },
                        text = {
                            Text("Please contact the seller to make arrangements for cash payment. You'll pay when your order is delivered.")
                        },
                        confirmButton = {
                            Button(
                                onClick = {
                                    showCashMessage = false
                                    // Navigate to order confirmation
                                    navController.navigate(ROUT_CAR_LIST) {
                                        popUpTo(navController.currentBackStackEntry?.destination?.route ?: "checkout") {
                                            inclusive = true
                                        }
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = newBlue)
                            ) {
                                Text("Continue shopping")
                            }
                        },
                        dismissButton = {
                            TextButton(
                                onClick = { showCashMessage = false }
                            ) {
                                Text("Cancel")
                            }
                        }
                    )
                }
            }
        }
    )
}

@Composable
fun PaymentMethodCard(
    title: String,
    description: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
            }

            Icon(
                imageVector = Icons.Default.ArrowBack, // You might want to use a different icon
                contentDescription = "Select",
                tint = newBlue,
                modifier = Modifier.rotate(180f) // Rotate to make it point right
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CheckOutScreenPreview() {
    CheckOutScreen(rememberNavController())
}