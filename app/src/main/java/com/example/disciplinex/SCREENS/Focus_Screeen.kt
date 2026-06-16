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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.disciplinex.R

// ── Data model for a blocked app entry ───────────────────────────────────────
data class BlockedAppItem(
    val iconRes: Int,        // e.g. R.drawable.ic_instagram
    val contentDesc: String
)

@Preview(showSystemUi = true)
@Composable
fun FocusSessionScreen() {
    var selectedDuration by remember { mutableStateOf("30 min") }

    val darkBackground    = Color(0xFF0F111A)
    val surfaceBackground = Color(0xFF1E2132)
    val primaryPurple     = Color(0xFF5A44E8)
    val textPrimary       = Color.White
    val textSecondary     = Color(0xFFA0A3B5)

    var selectedMode by remember { mutableStateOf("Monk Mode") }

    val modeDescriptions = mapOf(
        "Normal"    to "Normal Mode allows you to pause your session anytime. Flexible and relaxed.",
        "Strict"    to "Strict Mode limits interruptions. You can't pause but can exit if needed.",
        "Monk Mode" to "Monk Mode prevents you from leaving the app until the session ends."
    )

    // ── Replace these with your actual app icon drawables ────────────────────
    // e.g. R.drawable.ic_instagram, R.drawable.ic_youtube, R.drawable.ic_facebook
    val blockedApps = listOf(
        BlockedAppItem(iconRes = R.drawable.lock, contentDesc = "Instagram"),
        BlockedAppItem(iconRes = R.drawable.lock, contentDesc = "YouTube"),
        BlockedAppItem(iconRes = R.drawable.lock, contentDesc = "Facebook"),
    )
    val extraBlockedCount = 3   // apps blocked but not shown as icons

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(darkBackground)
            .padding(16.dp)
            .statusBarsPadding()
    ) {
        // ── Top App Bar ───────────────────────────────────────────────────────
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

        // ── Duration Section ──────────────────────────────────────────────────
        Text("Choose Duration", color = textPrimary, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(16.dp))

        val durations = listOf("15 min", "30 min", "45 min", "1 hour", "2 hours", "Custom")

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

        // ── Mode Section ──────────────────────────────────────────────────────
        Text("Choose Mode", color = textPrimary, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
            ModeCard(
                title = "Normal",
                subtitle = "Pause allowed",
                icon = painterResource(id = R.drawable.lock),
                isSelected = selectedMode == "Normal",
                onClick = { selectedMode = "Normal" },
                modifier = Modifier.weight(1f),
                primaryPurple = primaryPurple,
                surfaceColor = surfaceBackground
            )
            ModeCard(
                title = "Strict",
                subtitle = "Limited interruptions",
                icon = painterResource(id = R.drawable.lock),
                isSelected = selectedMode == "Strict",
                onClick = { selectedMode = "Strict" },
                modifier = Modifier.weight(1f),
                primaryPurple = primaryPurple,
                surfaceColor = surfaceBackground
            )
            ModeCard(
                title = "Monk Mode",
                subtitle = "No exits allowed",
                icon = painterResource(id = R.drawable.lock),
                isSelected = selectedMode == "Monk Mode",
                onClick = { selectedMode = "Monk Mode" },
                modifier = Modifier.weight(1f),
                primaryPurple = primaryPurple,
                surfaceColor = surfaceBackground
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // ── About Info Box ────────────────────────────────────────────────────
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

        // ── Blocked Apps Section ──────────────────────────────────────────────
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

        // ── Start Session Button ──────────────────────────────────────────────
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
}

// ── Blocked Apps Row ──────────────────────────────────────────────────────────
/**
 * Horizontal row of circular app icons + optional "+N" overflow badge.
 *
 * @param apps         Visible app entries (3–5 recommended)
 * @param extraCount   How many more apps are blocked but not shown as icons
 * @param surfaceColor Background for the "+N" badge
 */
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
/**
 * Circular container for one blocked app icon.
 * Set tint = Color.Unspecified to preserve your icon's original colors.
 */
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