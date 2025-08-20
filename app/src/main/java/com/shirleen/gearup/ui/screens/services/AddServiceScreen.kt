package com.shirleen.gearup.ui.screens.services



import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.shirleen.gearup.data.ServiceDao
import com.shirleen.gearup.model.Service
import com.shirleen.gearup.viewmodel.ServiceViewModel

@Composable
fun AddServiceScreen(
    viewModel: ServiceViewModel = viewModel(),
    onServiceAdded: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Add Service", style = MaterialTheme.typography.headlineSmall)

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Service Name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = price,
            onValueChange = { price = it },
            label = { Text("Price (KSh)") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = duration,
            onValueChange = { duration = it },
            label = { Text("Duration (e.g. 1 hr)") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (name.isNotBlank() && price.isNotBlank() && duration.isNotBlank()) {
                    val service = Service(
                        name = name,
                        price = price,
                        duration = duration
                    )
                    viewModel.addService(service)
                    onServiceAdded()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Service")
        }
    }
}
