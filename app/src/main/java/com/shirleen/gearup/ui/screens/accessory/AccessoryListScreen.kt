package com.shirleen.gearup.ui.screens.accessory

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.shirleen.gearup.model.Accessory
import com.shirleen.gearup.navigation.ROUT_ADD_ACCESSORY
import com.shirleen.gearup.navigation.ROUT_ACCESSORY_LIST
import com.shirleen.gearup.navigation.editAccessoryRoute
import com.shirleen.gearup.viewmodel.AccessoryViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import com.shirleen.gearup.ui.theme.newBluu
import java.io.IOException
import java.io.OutputStream

@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccessoryListScreen(
    navController: NavController,
    viewModel: AccessoryViewModel,
    isSeller: Boolean = true // 👈 flag to determine seller vs buyer
) {
    val allAccessories by viewModel.allAccessories.collectAsState(initial = emptyList())
    var showMenu by remember { mutableStateOf(false) }
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
                    actions = {

                        IconButton(onClick = { showMenu = true }) {
                                Icon(
                                    imageVector = Icons.Default.MoreVert,
                                    contentDescription = "Menu",
                                    tint = Color.White
                                )
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

                    }
                )

                //Search Bar
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

                if (filteredAccessories.isEmpty()) {
                    Text(
                        text = "Oops! We don't have that.",
                        modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center),
                        textAlign = TextAlign.Center,
                        color = Color.Gray
                    )
                } else {
                    LazyColumn(
                        contentPadding = PaddingValues(bottom = 80.dp)
                    ) {
                        items(filteredAccessories) { accessory ->
                            AccessoryItem(navController, accessory, viewModel, isSeller)
                        }
                    }
                }
            }
        },

        bottomBar = {
            NavigationBar(
                containerColor = newBluu,
                contentColor = Color.White
            ) {
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(ROUT_ACCESSORY_LIST) },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Accessory List") },
                    label = { Text("Home") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        unselectedIconColor = Color.White,
                        selectedTextColor = Color.White,
                        unselectedTextColor = Color.White
                    )
                )

                if (isSeller) {
                    NavigationBarItem(
                        selected = false,
                        onClick = { navController.navigate(ROUT_ADD_ACCESSORY) },
                        icon = { Icon(Icons.Default.AddCircle, contentDescription = "Add Accessory") },
                        label = { Text("Add") },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.White,
                            unselectedIconColor = Color.White,
                            selectedTextColor = Color.White,
                            unselectedTextColor = Color.White
                        )
                    )
                } else {
                    NavigationBarItem(
                        selected = false,
                        onClick = { /* Navigate to Cart */ },
                        icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Cart") },
                        label = { Text("Cart") },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.White,
                            unselectedIconColor = Color.White,
                            selectedTextColor = Color.White,
                            unselectedTextColor = Color.White
                        )
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
            if (filteredAccessories.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "No accessories found. Click '+' to add one.")
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(bottom = 80.dp)
                ) {
                    items(filteredAccessories) { accessory ->
                        AccessoryItem(navController, accessory, viewModel, isSeller)
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun AccessoryItem(
    navController: NavController,
    accessory: Accessory,
    viewModel: AccessoryViewModel,
    isSeller: Boolean
) {
    val painter: Painter = rememberAsyncImagePainter(
        model = accessory.imageUri?.let { Uri.parse(it) } ?: Uri.EMPTY
    )
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                if (accessory.id != 0) {
                    navController.navigate(editAccessoryRoute(accessory.id))
                }
            },
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
                    // Message Seller (visible to both)
                    Button(
                        onClick = {
                            val smsIntent = Intent(Intent.ACTION_SENDTO)
                            smsIntent.data = "smsto:${accessory.phone}".toUri()
                            smsIntent.putExtra("sms_body", "Hello, I'm inerested in ${accessory.name}, could you tell me more about it...")
                            context.startActivity(smsIntent)
                        },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = newBluu,
                            contentColor = Color.White
                        )
                    ) {
                        Row {
                            Icon(imageVector = Icons.Default.Send, contentDescription = "Message Seller")
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(text = "Message Seller")
                        }
                    }

                    if (isSeller) {
                        // Edit Accessory
                        IconButton(
                            onClick = { navController.navigate(editAccessoryRoute(accessory.id)) }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit",
                                tint = newBluu
                            )
                        }

                        // Delete Accessory
                        IconButton(
                            onClick = { viewModel.deleteAccessory(accessory) }
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
}
