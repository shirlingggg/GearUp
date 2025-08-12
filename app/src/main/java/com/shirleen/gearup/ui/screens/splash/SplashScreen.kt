package com.shirleen.gearup.ui.screens.splash

import android.annotation.SuppressLint
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.shirleen.gearup.R
import com.shirleen.gearup.navigation.ROUT_LOGIN
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

val newBlue = Color(0xFF0456A0)
val navy = Color(0xFF003366)

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SplashScreen(navController: NavController) {
    val coroutine = rememberCoroutineScope()

    coroutine.launch {
        delay(3000)
        navController.navigate(ROUT_LOGIN)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // TOP SECTION
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.6f)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(navy, newBlue)
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.car))
            val progress by animateLottieCompositionAsState(composition)
            LottieAnimation(
                composition = composition,
                progress = progress,
                modifier = Modifier.size(260.dp)
            )
        }

        // BOTTOM SECTION with wave + content
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.4f),
            contentAlignment = Alignment.TopCenter
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val waveHeight = 80f
                val path = Path().apply {
                    moveTo(0f, waveHeight)
                    quadraticBezierTo(size.width / 2, 0f, size.width, waveHeight)
                    lineTo(size.width, size.height)
                    lineTo(0f, size.height)
                    close()
                }
                drawPath(
                    path = path,
                    color = Color.White,
                    style = Fill
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 40.dp)
            ) {
                Text(
                    text = "GearUp",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = newBlue
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Ready to Gear Up?",
                    fontSize = 18.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Connecting you to your ride...",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))
                AnimatedLoadingDots(color = newBlue)
            }
        }
    }
}

@Composable
fun AnimatedLoadingDots(color: Color) {
    val dotCount = 3
    val infiniteTransition = rememberInfiniteTransition()
    val animations = List(dotCount) { index ->
        infiniteTransition.animateFloat(
            initialValue = 0.3f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(600, easing = LinearEasing, delayMillis = index * 150),
                repeatMode = RepeatMode.Reverse
            )
        )
    }

    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        animations.forEach { alpha ->
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .background(color.copy(alpha = alpha.value), shape = MaterialTheme.shapes.small)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashPreview() {
    SplashScreen(rememberNavController())
}
