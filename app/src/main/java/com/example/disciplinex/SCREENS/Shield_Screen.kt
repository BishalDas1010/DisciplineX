package com.example.disciplinex.SCREENS

import androidx.compose.foundation.background
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.disciplinex.R
import com.example.disciplinex.MVVM.ViewModel.ShieldViewModel

// ── Color tokens (shared with HomeScreen) ─────────────────────────────────────
private val BgDeep       = Color(0xFF0B0B18)
private val BgCard       = Color(0xFF14142A)
private val AccentBlue   = Color(0xFF5B5FFF)
private val AccentGreen  = Color(0xFF22C55E)
private val TextPrimary  = Color(0xFFFFFFFF)
private val TextSecond   = Color(0xFFAAAAAA)
private val TextMuted    = Color(0xFF55556A)
private val NavBg        = Color(0xFF10101E)

// ── Data model ────────────────────────────────────────────────────────────────
data class AppBlockItem(
    val name: String,
    val iconRes: Int,
    val iconBg: Color,
    val initiallyBlocked: Boolean = true
)

// ── Preview
@Preview(showSystemUi = true)
@Composable
fun ShieldPreview() {
   // Shield_Screen()
}

// ── Main Composable
@Composable
fun Shield_Screen(
    onBack: () -> Unit = {},
    onNavHome: () -> Unit = {},
    onNavStats: () -> Unit = {},
    onNavShield: () -> Unit = {},
    onNavSettings: () -> Unit = {},
    onAddMoreApps: () -> Unit = {},
    viewModel: ShieldViewModel
) {
    // Sample blocked apps — swap iconRes with real app icon drawables
    val apps = remember {
        mutableStateListOf(
            AppBlockItem("Instagram", R.drawable.lock, Color(0xFFE1306C)),
            AppBlockItem("YouTube",   R.drawable.lock, Color(0xFFFF0000)),
            AppBlockItem("Facebook",  R.drawable.lock, Color(0xFF1877F2)),
        )
    }
    // Per-app toggle state
    val toggleStates = remember { mutableStateListOf(*Array(apps.size) { true }) }

    val activeCount = toggleStates.count { it }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BgDeep)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {
            Spacer(Modifier.height(48.dp))

            // ── Top Bar ───────────────────────────────────────────────────────
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(BgCard)
                        .clickable { onBack() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.back),
                        contentDescription = "Back",
                        tint = TextPrimary,
                        modifier = Modifier.size(18.dp)
                    )
                }
                Spacer(Modifier.width(14.dp))
                Text(
                    text = "Shield (App Blocker)",
                    color = TextPrimary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.height(24.dp))

            // ── Shield Status Card ────────────────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(BgCard)
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Shield Status",
                            color = TextSecond,
                            fontSize = 14.sp
                        )
                        Spacer(Modifier.height(6.dp))
                        Text(
                            text = "Active",
                            color = AccentGreen,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = "$activeCount apps are blocked",
                            color = TextSecond,
                            fontSize = 13.sp
                        )
                    }
                    // Shield icon
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(AccentGreen.copy(alpha = 0.12f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.shield),
                            contentDescription = "Shield Active",
                            tint = AccentGreen,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            // ── Blocked Apps Header ───────────────────────────────────────────
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Blocked Apps",
                    color = TextPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "Manage",
                    color = AccentBlue,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable { /* open manage */ }
                )
            }

            Spacer(Modifier.height(14.dp))

            // ── App List ──────────────────────────────────────────────────────
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                apps.forEachIndexed { index, app ->
                    BlockedAppRow(
                        app = app,
                        isBlocked = toggleStates[index],
                        onToggle = { toggleStates[index] = it }
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            // ── Add More Apps button ──────────────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(BgCard)
                    .clickable { onAddMoreApps() }
                    .padding(vertical = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "+", color = AccentBlue, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "Add More Apps",
                        color = AccentBlue,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(Modifier.weight(1f))


        }
    }
}

// ── Blocked App Row ───────────────────────────────────────────────────────────
@Composable
private fun BlockedAppRow(
    app: AppBlockItem,
    isBlocked: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(BgCard)
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // App icon circle
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(app.iconBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(app.iconRes),
                    contentDescription = app.name,
                    tint = Color.White,
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(Modifier.width(16.dp))

            Text(
                text = app.name,
                color = TextPrimary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )

            // Toggle switch
            Switch(
                checked = isBlocked,
                onCheckedChange = onToggle,
                colors = SwitchDefaults.colors(
                    checkedThumbColor   = Color.White,
                    checkedTrackColor   = AccentBlue,
                    uncheckedThumbColor = Color.White,
                    uncheckedTrackColor = TextMuted
                )
            )
        }
    }
}