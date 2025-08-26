package com.shirleen.gearup.ui.screens.about

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.shirleen.gearup.ui.theme.newBlue
import com.shirleen.gearup.ui.theme.newBluu

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(navController: NavController) {
    // Gradient blur-like background
    val backgroundBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFFFFFFF),
            Color(0xFFEFEFEF)
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("About GearUp", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = newBluu
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundBrush)
                .padding(padding)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // Description
            Text(
                text = "GearUp is a platform that connects buyers and sellers, making it easy to shop, sell, and discover amazing cars and accessories all in one place.",
                fontSize = 20.sp,
                color = Color.Black,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Contact Section
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "Need any help?",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = newBluu
                    )
                    Text(
                        text = "Contact Us",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = newBluu
                    )

                    Text(
                        text = "Email: shirleenkabunyi@gmail.com",
                        fontSize = 16.sp,
                        color = newBluu
                    )

                    Text(
                        text = "Phone: 0755301153",
                        fontSize = 16.sp,
                        color = newBluu
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AboutScreenPreview() {
    AboutScreen(rememberNavController())
}
