package com.example.disciplinex.SCREENS

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.disciplinex.R
import com.example.disciplinex.MVVM.ViewModel.FocusViewModel

// ── Data model for a blocked app entry ───────────────────────────────────────
data class BlockedAppItem(
    val iconRes: Int,        // e.g. R.drawable.ic_instagram
    val contentDesc: String
)

@Composable
fun FocusSessionScreen(
    viewModel: FocusViewModel,
    on_startSession: () -> Unit,
    onNavigateToFocusing: () -> Unit
) {
    // ── UI colours ───────────────────────────────────────────────────────────
    val darkBackground    = Color(0xFF0F111A)
    val surfaceBackground = Color(0xFF1E2132)
    val primaryPurple     = Color(0xFF5A44E8)
    val textPrimary       = Color.White
    val textSecondary     = Color(0xFFA0A3B5)

    // ── State for duration & custom picker
    var selectedDuration by remember { mutableStateOf("30 min") }
    var isCustomSelected by remember { mutableStateOf(false) }
    var customDurationText by remember { mutableStateOf("") }
    var showCustomPicker by remember { mutableStateOf(false) }

    // Temporary values for the picker
    var tempHours by remember { mutableStateOf(0) }
    var tempMinutes by remember { mutableStateOf(30) }

    // ── Mode state ──────────────────────────────────────────────────────────
    var selectedMode by remember { mutableStateOf("Monk Mode") }

    val modeDescriptions = mapOf(
        "Normal"    to "Normal Mode allows you to pause your session anytime. Flexible and relaxed.",
        "Strict"    to "Strict Mode limits interruptions. You can't pause but can exit if needed.",
        "Monk Mode" to "Monk Mode prevents you from leaving the app until the session ends."
    )

    // ── Blocked apps (replace with your own drawables) ─────────────────────
    val blockedApps = listOf(
        BlockedAppItem(iconRes = R.drawable.lock, contentDesc = "Instagram"),
        BlockedAppItem(iconRes = R.drawable.lock, contentDesc = "YouTube"),
        BlockedAppItem(iconRes = R.drawable.lock, contentDesc = "Facebook"),
    )
    val extraBlockedCount = 3   // apps blocked but not shown as icons

    // ── Main UI ─────────────────────────────────────────────────────────────
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(darkBackground)
            .padding(16.dp)
            .statusBarsPadding()
    ) {
        // ── Top App Bar ─────────────────────────────────────────────────────
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = on_startSession) {
                Icon(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = "Back",
                    tint = textPrimary,
                    modifier = Modifier.size(20.dp)
                )
            }
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

        // ── Duration Section ──────────────────────────────────────────────
        Text("Choose Duration", color = textPrimary, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(16.dp))

        val durations = listOf("15 min", "30 min", "45 min", "1 hour", "2 hours", "Custom")

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                durations.take(3).forEach { duration ->
                    DurationButton(
                        text = duration,
                        isSelected = if (duration == "Custom") isCustomSelected else selectedDuration == duration,
                        onClick = {
                            if (duration == "Custom") {
                                // Open the custom picker
                                tempHours = 0
                                tempMinutes = 30
                                showCustomPicker = true
                            } else {
                                isCustomSelected = false
                                selectedDuration = duration
                            }
                        },
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
                        isSelected = if (duration == "Custom") isCustomSelected else selectedDuration == duration,
                        onClick = {
                            if (duration == "Custom") {
                                tempHours = 0
                                tempMinutes = 30
                                showCustomPicker = true
                            } else {
                                isCustomSelected = false
                                selectedDuration = duration
                            }
                        },
                        modifier = Modifier.weight(1f),
                        primaryPurple = primaryPurple,
                        surfaceColor = surfaceBackground
                    )
                }
            }
        }

        // Show selected custom duration if any
        if (isCustomSelected && customDurationText.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Selected: $customDurationText",
                color = primaryPurple,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // ── Mode Section
        Text("Choose Mode", color = textPrimary, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
            ModeCard(
                title = "Normal",
                subtitle = "Pause allowed",
                icon = painterResource(id = R.drawable.meditation),
                isSelected = selectedMode == "Normal",
                onClick = { selectedMode = "Normal" },
                modifier = Modifier.weight(1f),
                primaryPurple = primaryPurple,
                surfaceColor = surfaceBackground
            )
            ModeCard(
                title = "Strict",
                subtitle = "Limited interruptions",
                icon = painterResource(id = R.drawable.trafficsignal),
                isSelected = selectedMode == "Strict",
                onClick = { selectedMode = "Strict" },
                modifier = Modifier.weight(1f),
                primaryPurple = primaryPurple,
                surfaceColor = surfaceBackground
            )
            ModeCard(
                title = "Monk Mode",
                subtitle = "No exits allowed",
                icon = painterResource(id = R.drawable.spy),
                isSelected = selectedMode == "Monk Mode",
                onClick = { selectedMode = "Monk Mode" },
                modifier = Modifier.weight(1f),
                primaryPurple = primaryPurple,
                surfaceColor = surfaceBackground
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // ── About Info Box
        Surface(
            color = surfaceBackground,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(modifier = Modifier.padding(16.dp)) {
                Icon(
                    painter = painterResource(id = R.drawable.lock),
                    contentDescription = "Info",
                    tint = textSecondary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        "About $selectedMode",
                        color = textPrimary,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = modeDescriptions[selectedMode] ?: "",
                        color = textSecondary,
                        fontSize = 12.sp,
                        lineHeight = 18.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // ── Blocked Apps Section ──────────────────────────────────────────
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Blocked Apps",
                color = textPrimary,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
            Text(
                text = "Manage",
                color = primaryPurple,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.clickable { /* navigate to manage screen */ }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        BlockedAppsRow(
            apps = blockedApps,
            extraCount = extraBlockedCount,
            surfaceColor = surfaceBackground
        )

        Spacer(modifier = Modifier.weight(1f))

        // ── Start Session Button ──────────────────────────────────────────
        Button(
            onClick = { onNavigateToFocusing() },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(bottom = 8.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = primaryPurple)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.lock),
                contentDescription = "Start",
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                "Start Session",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }

    // ── Custom Duration Picker Dialog ─────────────────────────────────────
    if (showCustomPicker) {
        AlertDialog(
            onDismissRequest = { showCustomPicker = false },
            title = { Text("Set Custom Duration", color = Color.White) },
            text = {
                Column {
                    // Hours row
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Hours", color = Color.White, modifier = Modifier.weight(1f))
                        IconButton(onClick = { if (tempHours > 0) tempHours-- }) {
                            Icon(painter = painterResource(R.drawable.negtive), contentDescription = "Decrease hours", tint = Color.White)
                        }
                        Text(
                            text = "$tempHours",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.width(48.dp),
                            textAlign = TextAlign.Center
                        )
                        IconButton(onClick = { tempHours++ }) {
                            Icon(painter = painterResource(R.drawable.positive), contentDescription = "Increase hours", tint = Color.White)
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    // Minutes row (step 5)
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Minutes", color = Color.White, modifier = Modifier.weight(1f))
                        IconButton(onClick = { if (tempMinutes >= 5) tempMinutes -= 5 }) {
                            Icon(painter = painterResource(R.drawable.negtive), contentDescription = "Decrease minutes", tint = Color.White)
                        }
                        Text(
                            text = "$tempMinutes",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.width(48.dp),
                            textAlign = TextAlign.Center
                        )
                        IconButton(onClick = { if (tempMinutes < 55) tempMinutes += 5 }) {
                            Icon(painter = painterResource(R.drawable.positive), contentDescription = "Increase minutes", tint = Color.White)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val hours = tempHours
                        val minutes = tempMinutes
                        val formatted = when {
                            hours == 0 -> "${minutes} min"
                            minutes == 0 -> "${hours}h"
                            else -> "${hours}h ${minutes}min"
                        }
                        selectedDuration = formatted
                        customDurationText = formatted
                        isCustomSelected = true
                        showCustomPicker = false
                    }
                ) {
                    Text("OK", color = primaryPurple)
                }
            },
            dismissButton = {
                TextButton(onClick = { showCustomPicker = false }) {
                    Text("Cancel", color = Color.Gray)
                }
            },
            containerColor = surfaceBackground,
            shape = RoundedCornerShape(16.dp)
        )
    }
}

// ── Blocked Apps Row ──────────────────────────────────────────────────────────
@Composable
fun BlockedAppsRow(
    apps: List<BlockedAppItem>,
    extraCount: Int = 0,
    surfaceColor: Color
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        apps.forEach { app ->
            AppIconCircle(app = app)
        }

        if (extraCount > 0) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(surfaceColor)
                    .border(1.dp, Color(0xFF2E2A45), CircleShape)
            ) {
                Text(
                    text = "+$extraCount",
                    color = Color(0xFFA0A3B5),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

// ── Single App Icon Circle ────────────────────────────────────────────────────
@Composable
fun AppIconCircle(app: BlockedAppItem) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(44.dp)
            .clip(CircleShape)
            .background(Color(0xFF1E2132))
            .border(1.dp, Color(0xFF2E2A45), CircleShape)
    ) {
        Icon(
            painter = painterResource(id = app.iconRes),
            contentDescription = app.contentDesc,
            tint = Color.Unspecified,   // preserves original icon colors
            modifier = Modifier.size(28.dp)
        )
    }
}

// ── Duration Button ───────────────────────────────────────────────────────────
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


// ── Mode Card ─────────────────────────────────────────────────────────────────
@Composable
fun ModeCard(
    title: String,
    subtitle: String,
    icon: Painter,
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
                painter = icon,
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