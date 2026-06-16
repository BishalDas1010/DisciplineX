package com.example.disciplinex.SCREENS

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.disciplinex.R

// ── Color tokens ──────────────────────────────────────────────────────────────
private val BgDeep       = Color(0xFF0B0B18)
private val BgCard       = Color(0xFF14142A)
private val AccentBlue   = Color(0xFF5B5FFF)
private val AccentPurple = Color(0xFF7C4DFF)
private val BarDim       = Color(0xFF2A2A45)
private val TextPrimary  = Color(0xFFFFFFFF)
private val TextSecond   = Color(0xFFAAAAAA)
private val TextMuted    = Color(0xFF55556A)
private val NavBg        = Color(0xFF10101E)

// ── Preview ───────────────────────────────────────────────────────────────────
@Preview(showSystemUi = true)
@Composable
fun HomePreview() {
    HomeScreen()
}

// ── Main Screen ───────────────────────────────────────────────────────────────
@Composable
fun HomeScreen(
    onNavHome: () -> Unit = {},
    onNavStats: () -> Unit = {},
    onNavShield: () -> Unit = {},
    onNavSettings: () -> Unit = {},
    onStartSession: () -> Unit = {},
    onShieldTap: () -> Unit = {}
) {
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

            // ── Header ────────────────────────────────────────────────────────
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column {
                    Text(
                        text = "Good Evening",
                        color = TextSecond,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Vishal",
                            color = TextPrimary,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(text = "👋", fontSize = 24.sp)
                    }
                }

                // Bell icon
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(BgCard),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.notification),
                        contentDescription = "Notifications",
                        tint = TextSecond,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }

            Spacer(Modifier.height(22.dp))

            // ── Today's Focus Card ────────────────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(30.dp))
                    .background(BgCard)
                    .padding(20.dp)
            ) {
                Column {
                    Text(text = "Today's Focus", color = TextSecond, fontSize = 18.sp)
                    Spacer(Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Column {
                            Text(
                                text = "2h 15m",
                                color = TextPrimary,
                                fontSize = 40.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "of 3 hours goal",
                                color = TextMuted,
                                fontSize = 13.sp
                            )
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            // Pause circle
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(AccentBlue.copy(alpha = 0.18f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.play),
                                    contentDescription = "Pause",
                                    tint = AccentBlue,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                            Spacer(Modifier.height(10.dp))
                            MiniBarChart()
                        }
                    }
                }
            }

            Spacer(Modifier.height(14.dp))

            // ── Streak + Daily Goal ───────────────────────────────────────────
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Current Streak card
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(18.dp))
                        .background(BgCard)
                        .padding(18.dp)
                ) {
                    Column {
                        Text(text = "Current Streak", color = TextSecond, fontSize = 13.sp)
                        Spacer(Modifier.height(8.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "", fontSize = 23.sp)
                            Spacer(Modifier.width(6.dp))
                            Text(
                                text = "10 Days",
                                color = TextPrimary,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(Modifier.height(4.dp))
                        Text(text = "Keep it up!", color = TextMuted, fontSize = 12.sp)
                    }
                }

                // Daily Goal card
                Box(
                    modifier = Modifier
                        .weight(1.5f)
                        .clip(RoundedCornerShape(18.dp))
                        .background(BgCard)
                        .padding(18.dp)
                ) {
                    Column {
                        Text(text = "Daily Goal", color = TextSecond, fontSize = 12.sp)
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = "75%",
                            color = TextPrimary,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(Modifier.height(10.dp))
                        LinearProgressIndicator(
                            progress = { 0.75f },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp)),
                            color = AccentBlue,
                            trackColor = BarDim,
                            strokeCap = StrokeCap.Round
                        )
                    }
                }
            }

            Spacer(Modifier.height(14.dp))

            // ── Start Focus Session Button ────────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(
                        Brush.horizontalGradient(listOf(AccentBlue, AccentPurple))
                    )
                    .clickable { onStartSession() },
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.play),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(Modifier.width(10.dp))
                    Text(
                        text = "Start Focus Session",
                        color = Color.White,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(Modifier.height(14.dp))

            // ── Focus Shield Card ─────────────────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(18.dp))
                    .background(BgCard)
                    .clickable { onShieldTap() }
                    .padding(horizontal = 18.dp, vertical = 14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Shield icon box
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(AccentBlue.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            // swap R.drawable.play → your shield drawable when available
                            painter = painterResource(R.drawable.play),
                            contentDescription = "Shield",
                            tint = AccentBlue,
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    Spacer(Modifier.width(14.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Focus Shield",
                            color = TextPrimary,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "3 Apps Blocked",
                            color = TextSecond,
                            fontSize = 12.sp
                        )
                        Text(
                            text = "Tap to manage",
                            color = TextMuted,
                            fontSize = 11.sp
                        )
                    }

                    // Chevron right
                    // swap R.drawable.play → chevron_right drawable when available
                    Icon(
                        painter = painterResource(R.drawable.play),
                        contentDescription = "Manage",
                        tint = TextMuted,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(Modifier.weight(1f))

            // ── Bottom Nav Bar ────────────────────────────────────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                    .background(NavBg)
                    .padding(vertical = 10.dp, horizontal = 10.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BottomNavItem(
                    iconRes  = R.drawable.play,  // swap: home icon
                    label    = "Home",
                    selected = true,
                    onClick  = onNavHome
                )
                BottomNavItem(
                    iconRes  = R.drawable.play,  // swap: bar chart icon
                    label    = "Stats",
                    selected = false,
                    onClick  = onNavStats
                )
                BottomNavItem(
                    iconRes  = R.drawable.play,  // swap: shield icon
                    label    = "Shield",
                    selected = false,
                    onClick  = onNavShield
                )
                BottomNavItem(
                    iconRes  = R.drawable.play,  // swap: settings/gear icon
                    label    = "Settings",
                    selected = false,
                    onClick  = onNavSettings
                )
            }
        }
    }
}

// ── Mini Bar Chart ────────────────────────────────────────────────────────────
@Composable
private fun MiniBarChart() {
    val heights = listOf(0.35f, 0.55f, 0.45f, 0.75f, 1.0f)
    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        heights.forEachIndexed { i, h ->
            Box(
                modifier = Modifier
                    .width(7.dp)
                    .height((32 * h).dp)
                    .clip(RoundedCornerShape(topStart = 5.dp, topEnd = 4.dp))
                    .background(if (i == heights.lastIndex) AccentBlue else BarDim)
            )
        }
    }
}

// ── Bottom Nav Item ───────────────────────────────────────────────────────────
@Composable
private fun BottomNavItem(
    iconRes: Int,
    label: String,
    selected: Boolean,
    onClick: () -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(horizontal = 14.dp, vertical = 6.dp)
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = label,
            tint = if (selected) AccentBlue else TextMuted,
            modifier = Modifier.size(22.dp)
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = label,
            color = if (selected) AccentBlue else TextMuted,
            fontSize = 10.sp,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
        )
    }
}