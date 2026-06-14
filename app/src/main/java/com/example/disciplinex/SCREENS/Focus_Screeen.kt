
package com.example.disciplinex.SCREENS

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter // Added import for Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.disciplinex.R

@Preview(showSystemUi = true)
@Composable
fun FocusSessionScreen() {
    // State management for the clickable selections
    var selectedDuration by remember { mutableStateOf("30 min") }
    var selectedMode by remember { mutableStateOf("Monk Mode") }

    // Theme Colors based on the UI
    val darkBackground = Color(0xFF0F111A)
    val surfaceBackground = Color(0xFF1E2132)
    val primaryPurple = Color(0xFF5A44E8)
    val textPrimary = Color.White
    val textSecondary = Color(0xFFA0A3B5)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(darkBackground)
            .padding(16.dp)
            .statusBarsPadding()
    ) {
        // Top App Bar
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(id = R.drawable.back),
                contentDescription = "Back",
                tint = textPrimary,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(24.dp))
            Text(
                text = "Start Focus Session",
                color = textPrimary,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Icon(
                painter = painterResource(id = R.drawable.config),
                contentDescription = "Options",
                tint = textPrimary,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // --- Duration Section ---
        Text("Choose Duration", color = textPrimary, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(16.dp))

        val durations = listOf("15 min", "30 min", "45 min", "1 hour", "2 hours", "Custom")

        // 3x2 Grid for Durations
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                durations.take(3).forEach { duration ->
                    DurationButton(
                        text = duration,
                        isSelected = selectedDuration == duration,
                        onClick = { selectedDuration = duration },
                        modifier = Modifier.weight(1f),
                        primaryPurple = primaryPurple,
                        surfaceColor = surfaceBackground
                    )
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                durations.drop(3).forEach { duration ->
                    DurationButton(
                        text = duration,
                        isSelected = selectedDuration == duration,
                        onClick = { selectedDuration = duration },
                        modifier = Modifier.weight(1f),
                        primaryPurple = primaryPurple,
                        surfaceColor = surfaceBackground
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // --- Mode Section ---
        Text("Choose Mode", color = textPrimary, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
            ModeCard(
                title = "Normal",
                subtitle = "Pause allowed",
                icon = painterResource(id = R.drawable.lock), // Updated
                isSelected = selectedMode == "Normal",
                onClick = { selectedMode = "Normal" },
                modifier = Modifier.weight(1f),
                primaryPurple = primaryPurple,
                surfaceColor = surfaceBackground
            )
            ModeCard(
                title = "Strict",
                subtitle = "Limited interruptions",
                icon = painterResource(id = R.drawable.lock), // Updated
                isSelected = selectedMode == "Strict",
                onClick = { selectedMode = "Strict" },
                modifier = Modifier.weight(1f),
                primaryPurple = primaryPurple,
                surfaceColor = surfaceBackground
            )
            ModeCard(
                title = "Monk Mode",
                subtitle = "No exits allowed",
                icon = painterResource(id = R.drawable.lock), // Updated
                isSelected = selectedMode == "Monk Mode",
                onClick = { selectedMode = "Monk Mode" },
                modifier = Modifier.weight(1f),
                primaryPurple = primaryPurple,
                surfaceColor = surfaceBackground
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- About Info Box ---
        Surface(
            color = surfaceBackground,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(modifier = Modifier.padding(16.dp)) {
                Icon(
                    painter = painterResource(id = R.drawable.lock), // Updated
                    contentDescription = "Info",
                    tint = textSecondary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text("About Modes", color = textPrimary, fontWeight = FontWeight.Medium, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Monk Mode prevents you from leaving the app until the session ends.",
                        color = textSecondary,
                        fontSize = 12.sp,
                        lineHeight = 18.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // --- Start Session Button ---
        Button(
            onClick = { /* Handle Start Session */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(bottom = 8.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = primaryPurple)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.lock), // Updated
                contentDescription = "Start",
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Start Session", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
fun DurationButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    primaryPurple: Color,
    surfaceColor: Color
) {
    Surface(
        color = if (isSelected) primaryPurple else surfaceColor,
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .height(48.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = text,
                color = if (isSelected) Color.White else Color(0xFFA0A3B5),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun ModeCard(
    title: String,
    subtitle: String,
    icon: Painter, // Changed from ImageVector to Painter
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    primaryPurple: Color,
    surfaceColor: Color
) {
    Surface(
        color = surfaceColor,
        shape = RoundedCornerShape(16.dp),
        border = if (isSelected) BorderStroke(1.5.dp, primaryPurple) else null,
        modifier = modifier
            .height(130.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(12.dp)
        ) {
            Icon(
                painter = icon, // Changed from imageVector to painter
                contentDescription = title,
                tint = if (isSelected) Color.White else Color(0xFFA0A3B5),
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = title,
                color = if (isSelected) Color.White else Color(0xFFA0A3B5),
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = subtitle,
                color = Color(0xFFA0A3B5),
                fontSize = 11.sp,
                textAlign = TextAlign.Center,
                lineHeight = 14.sp
            )
        }
    }
}
