package com.shirleen.gearup.ui.screens.bookappointment

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.shirleen.gearup.ui.theme.newBluu
import com.shirleen.gearup.ui.theme.newBlue
import java.util.Calendar
import android.content.Context
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookAppointmentScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Book a Service", fontWeight = FontWeight.Bold, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = newBluu)
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            item {
                Text(
                    text = "1. Select a Service Provider",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = newBlue,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            // A list of providers
            item {
                LazyRow(modifier = Modifier.fillMaxWidth()) {
                    val providers = listOf("Garage A", "Mechanic B", "Workshop C") // Dummy data
                    items(providers) { provider ->
                        Card(
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .clickable { /* Logic to select provider */ }
                                .border(1.dp, newBlue, RoundedCornerShape(12.dp)), // Outlined border
                            shape = RoundedCornerShape(12.dp),
                            elevation = CardDefaults.cardElevation(2.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White) // Ensure card background is white
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp)
                            ) {
                                Text(provider, fontWeight = FontWeight.SemiBold, color = newBluu)
                                Text("Available Services: Oil Change, Diagnostics", color = Color.Gray)
                            }
                        }
                    }
                }
            }
            // New sections for service, date, and time
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "2. Choose a Service",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = newBlue,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                SelectServiceSection()
            }
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "3. Select Date and Time",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = newBlue,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                SelectDateTimeSection()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectServiceSection() {
    var expanded by remember { mutableStateOf(false) }
    var selectedService by remember { mutableStateOf("Choose a Service") }
    val services = listOf("Full Car Service", "Oil Change", "Brakes, Battery, Tires Check", "Diagnostics", "AC Service")

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = selectedService,
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = newBlue,
                unfocusedBorderColor = newBluu,
                cursorColor = newBlue,
                focusedTextColor = newBluu
            )
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            services.forEach { service ->
                DropdownMenuItem(
                    text = { Text(text = service, color = newBluu) },
                    onClick = {
                        selectedService = service
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun SelectDateTimeSection() {
    val context = LocalContext.current
    var selectedDate by remember { mutableStateOf("Select Date") }

    val year: Int
    val month: Int
    val day: Int
    val calendar = Calendar.getInstance()
    year = calendar.get(Calendar.YEAR)
    month = calendar.get(Calendar.MONTH)
    day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
            selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
        }, year, month, day
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = selectedDate,
            onValueChange = {},
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { datePickerDialog.show() },
            label = { Text("Appointment Date", color = newBluu) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = newBlue,
                unfocusedBorderColor = newBluu,
                cursorColor = newBlue,
                focusedTextColor = newBluu
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BookAppointmentScreenPreview() {
    BookAppointmentScreen(rememberNavController())
}