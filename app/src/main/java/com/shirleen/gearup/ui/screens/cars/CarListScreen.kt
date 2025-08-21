package com.shirleen.gearup.ui.screens.cars

import android.content.ContentValues
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.shirleen.gearup.R
import com.shirleen.gearup.model.Car
import com.shirleen.gearup.navigation.ROUT_ADD_CAR
import com.shirleen.gearup.navigation.ROUT_CAR_LIST
import com.shirleen.gearup.navigation.editCarRoute
import com.shirleen.gearup.viewmodel.CarViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import com.shirleen.gearup.ui.theme.newBluu

import java.io.IOException
import java.io.OutputStream

@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarListScreen(navController: NavController, viewModel: CarViewModel) {
    val carList by viewModel.cars.collectAsState()
    var showMenu by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    val filteredCars = carList.filter {
        it.brand.contains(searchQuery, ignoreCase = true) ||
                it.model.contains(searchQuery, ignoreCase = true)
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
                    text = "Brand: ${car.brand}",
                    fontSize = 20.sp,
                    color = Color.Black
                )

                Text(
                    text = "Model: ${car.model}",
                    fontSize = 16.sp,
                    color = Color.Black
                )

                Text(
                    text = "Year: ${car.yearOfManufacture}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )

                Text(
                    text = "Mileage: ${car.mileage}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )
                Text(
                    text = "Price: Ksh${car.price}",
                    fontSize = 16.sp,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Buttons (Message, Edit, Delete, Download PDF)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Message Seller
                    Button(
                        onClick = {
                            val smsIntent = Intent(Intent.ACTION_SENDTO)
                            smsIntent.data = "smsto:${car.phone}".toUri()
                            smsIntent.putExtra("sms_body", "Hello Seller,...?")
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

                    // Download PDF
                    IconButton(
                        onClick = { generateProductPDF(context, car) }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.download),
                            contentDescription = "",
                            tint = newBluu
                        )
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
fun generateProductPDF(context: Context, car: Car) {
    val pdfDocument = PdfDocument()
    val pageInfo = PdfDocument.PageInfo.Builder(300, 500, 1).create()
    val page = pdfDocument.startPage(pageInfo)
    val canvas = page.canvas
    val paint = android.graphics.Paint()

    val bitmap: Bitmap? = try {
        car.imageUri?.let {
            val uri = Uri.parse(it)
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                BitmapFactory.decodeStream(inputStream)
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }

    bitmap?.let {
        val scaledBitmap = Bitmap.createScaledBitmap(it, 250, 150, false)
        canvas.drawBitmap(scaledBitmap, 25f, 20f, paint)
    }

    paint.textSize = 16f
    paint.isFakeBoldText = true
    canvas.drawText("Car Details", 80f, 200f, paint)

    paint.textSize = 12f
    paint.isFakeBoldText = false
    canvas.drawText("Brand: ${car.brand}", 50f, 230f, paint)
    canvas.drawText("Model: ${car.model}", 50f, 250f, paint)
    canvas.drawText("Year of Manufacture: ${car.yearOfManufacture}", 50f, 270f, paint)
    canvas.drawText("Mileage: ${car.mileage}", 50f, 290f, paint)
    canvas.drawText("Price: Ksh${car.price}", 50f, 310f, paint)
    canvas.drawText("Seller Phone: ${car.phone}", 50f, 330f, paint)

    pdfDocument.finishPage(page)

    // Save PDF using MediaStore (Scoped Storage)
    val fileName = "${car.brand}_Details.pdf"
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
        put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
        put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
    }

    val contentResolver = context.contentResolver
    val uri = contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)

    if (uri != null) {
        try {
            val outputStream: OutputStream? = contentResolver.openOutputStream(uri)
            if (outputStream != null) {
                pdfDocument.writeTo(outputStream)
                Toast.makeText(context, "PDF saved to Downloads!", Toast.LENGTH_LONG).show()
            }
            outputStream?.close()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(context, "Failed to save PDF!", Toast.LENGTH_LONG).show()
        }
    } else {
        Toast.makeText(context, "Failed to create file!", Toast.LENGTH_LONG).show()
    }

    pdfDocument.close()
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
