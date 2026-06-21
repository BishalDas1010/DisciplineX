package com.example.disciplinex.SCREENS

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.disciplinex.R
import com.example.disciplinex.MVVM.ViewModel.FocusViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Preview(showSystemUi = true)
@Composable
fun FocusingScreen(viewModel: FocusViewModel, onSessionComplete: () -> Unit) {
    // Theme Colors
    val darkBackground = Color(0xFF0F111A)
    val surfaceBackground = Color(0xFF1E2132)
    val textPrimary = Color.White
    val textSecondary = Color(0xFFA0A3B5)
    val accentBlue = Color(0xFF2FA2FF)
    val accentPurple = Color(0xFF5A44E8)
    val dangerRed = Color(0xFFFF4C4C)

    val isRunning by viewModel.isRunning.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(darkBackground)
            .padding(24.dp)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- Top App Bar ---
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Focusing",
                color = textPrimary,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.Center)
            )
            Icon(
                painter = painterResource(R.drawable.config),
                contentDescription = "Options",

                tint = textSecondary,
                modifier = Modifier.align(Alignment.CenterEnd).size(20.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // --- Circular Timer Display ---
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(300.dp)
        ) {
            // Background Track & Progress Arc
            CircularTimerView(
                progress = 0.60f, // 85% remaining
                modifier = Modifier.fillMaxSize()
            )

            // Inner Content
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    painter = painterResource(R.drawable.sandclock),
                    contentDescription = "Timer",
                    tint = accentBlue,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "${viewModel.remainingSeconds.collectAsState().value}",
                    color = textPrimary,
                    fontSize = 56.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "remaining",
                    color = textSecondary,
                    fontSize = 16.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        // --- Motivational Quote ---
        Text(
            text = "\"Small progress is\nstill progress.\"",
            color = textPrimary,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            lineHeight = 24.sp
        )

        Spacer(modifier = Modifier.weight(1f))

        // --- Bottom Controls ---
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            // Pause Button


            Surface(
                color = surfaceBackground,
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier
                    .weight(1f)
                    .height(64.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .clickable {
                        if (isRunning) viewModel.pauseTimer() else viewModel.resumeTimer()
                    }
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(
                            if (isRunning) R.drawable.pause else R.drawable.play  // or resume icon
                        ),
                        contentDescription = if (isRunning) "Pause" else "Resume",
                        tint = textPrimary,
                        modifier = Modifier.size(30.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isRunning) "Pause" else "Resume",
                        color = textPrimary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            // End Session Button
            Surface(
                color = surfaceBackground,
                shape = RoundedCornerShape(24.dp),
                border = BorderStroke(1.dp, dangerRed.copy(alpha = 0.5f)),
                modifier = Modifier
                    .weight(1f)
                    .height(64.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .clickable {viewModel.endSession() }
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(painter = painterResource(R.drawable.stop), contentDescription = "End", tint = dangerRed, modifier = Modifier.size(30.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("End Session", color = textPrimary, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun CircularTimerView(progress: Float, modifier: Modifier = Modifier) {
    val trackColor = Color(0xFF1E2132)
    val gradientColors = listOf(Color(0xFF8A2BE2), Color(0xFF4169E1)) // Purple to Blue gradient

    Canvas(modifier = modifier) {
        val strokeWidth = 14.dp.toPx()
        val diameter = size.minDimension - strokeWidth
        val topLeft = Offset(strokeWidth / 2, strokeWidth / 2)
        val size = Size(diameter, diameter)

        // Draw Background Track
        drawArc(
            color = trackColor,
            startAngle = 0f,
            sweepAngle = 360f,
            useCenter = false,
            topLeft = topLeft,
            size = size,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )

        // Draw Progress Arc with Gradient
        drawArc(
            brush = Brush.linearGradient(colors = gradientColors),
            startAngle = -90f, // Start from the top
            sweepAngle = 360f * progress,
            useCenter = false,
            topLeft = topLeft,
            size = size,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )
    }
}