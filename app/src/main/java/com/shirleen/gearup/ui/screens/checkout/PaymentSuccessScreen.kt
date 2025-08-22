package com.shirleen.gearup.ui.screens.checkout


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.shirleen.gearup.navigation.ROUT_ACCESSORY_LIST
import com.shirleen.gearup.ui.theme.newBlue

@Composable
fun PaymentSuccessScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = "Success",
                tint = newBlue,
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Payment Successful!",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = newBlue
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Your order has been placed successfully. You will receive a confirmation shortly.",
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = { navController.navigate(ROUT_ACCESSORY_LIST) },
                colors = ButtonDefaults.buttonColors(containerColor = newBlue),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Continue Shopping", color = Color.White)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PaymentSuccessScreenPreview() {
    PaymentSuccessScreen(rememberNavController())
}
