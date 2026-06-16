package com.example.disciplinex.SCREENS



import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.disciplinex.R


// ── Colors ──────────────────────────────────────────────────────────────────
private val BackgroundDark   = Color(0xFF0D0D1A)
private val SurfaceCard      = Color(0xFF1A1A2E)
private val PurpleAccent     = Color(0xFF6C63FF)
private val PurpleLight      = Color(0xFF8B85FF)
private val GreenSuccess     = Color(0xFF4CAF50)
private val GreenLight       = Color(0xFF69D96F)
private val TextPrimary      = Color(0xFFFFFFFF)
private val TextSecondary    = Color(0xFFAAAAAA)
private val CardBorder       = Color(0xFF2A2A40)
private val StreakOrange     = Color(0xFFFF6B35)
private val BadgeGreen       = Color(0xFF2ECC71)

// ── Data model ───────────────────────────────────────────────────────────────
data class SessionResult(
    val userName: String    = "Vishal",
    val duration: String    = "1 Hour",
    val xpEarned: String    = "+100 XP",
    val streakDays: String  = "8 Days",
    val newBadge: String    = "Focus Consistent"
)

// ── Screen ───────────────────────────────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SessionCompleteScreen(
    result: SessionResult = SessionResult(),
    onContinue: () -> Unit = {},
    onShare: () -> Unit = {},
    onBack: () -> Unit = {}
) {
    // Animate card content in
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    // Pulsing scale for the check circle
    val pulse = rememberInfiniteTransition(label = "pulse")
    val checkScale by pulse.animateFloat(
        initialValue = 1f, targetValue = 1.06f,
        animationSpec = infiniteRepeatable(
            tween(900, easing = FastOutSlowInEasing),
            RepeatMode.Reverse
        ),
        label = "checkScale"
    )

    Scaffold(
        containerColor = BackgroundDark,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Session Complete",
                        color = TextPrimary,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 17.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            painter = painterResource(R.drawable.play),
                            contentDescription = "Back",
                            tint = TextPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BackgroundDark
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(24.dp))

            // ── Confetti / glow area + check icon ────────────────────────────
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(140.dp)
                    .scale(checkScale)
            ) {
                // Outer glow ring
                Box(
                    modifier = Modifier
                        .size(130.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.radialGradient(
                                colors = listOf(
                                    GreenSuccess.copy(alpha = 0.25f),
                                    Color.Transparent
                                )
                            )
                        )
                )
                // Green circle with check
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(GreenSuccess)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.play),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
                    )
                }
                // Decorative dot ring (replaces confetti)
                ConfettiDots()
            }

            Spacer(Modifier.height(20.dp))

            // ── Headline ─────────────────────────────────────────────────────
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(tween(600)) + slideInVertically(tween(600)) { it / 4 }
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "Focus Session Complete!",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Spacer(Modifier.height(6.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            "Great work, ${result.userName}!",
                            fontSize = 15.sp,
                            color = TextSecondary
                        )
                        Spacer(Modifier.width(4.dp))
                        Text("🎉", fontSize = 16.sp)
                    }
                }
            }

            Spacer(Modifier.height(28.dp))

            // ── Stats card ───────────────────────────────────────────────────
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(tween(700, 150)) + slideInVertically(tween(700, 150)) { it / 3 }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(SurfaceCard)
                        .border(1.dp, CardBorder, RoundedCornerShape(16.dp))
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Row 1: Duration | XP
                    Row(modifier = Modifier.fillMaxWidth()) {
                        StatCell(
                            icon = "⏱",
                            label = "Duration",
                            value = result.duration,
                            modifier = Modifier.weight(1f)
                        )
                        VerticalDivider()
                        StatCell(
                            icon = "✦",
                            label = "XP Earned",
                            value = result.xpEarned,
                            valueColor = PurpleLight,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    HorizontalDivider(color = CardBorder)

                    // Row 2: Streak | Badge
                    Row(modifier = Modifier.fillMaxWidth()) {
                        StatCell(
                            icon = "🔥",
                            label = "Updated Streak",
                            value = result.streakDays,
                            valueColor = StreakOrange,
                            modifier = Modifier.weight(1f)
                        )
                        VerticalDivider()
                        StatCell(
                            icon = "🏅",
                            label = "New Badge",
                            value = result.newBadge,
                            valueColor = BadgeGreen,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            Spacer(Modifier.weight(1f))

            // ── CTA buttons ──────────────────────────────────────────────────
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(tween(800, 300)) + slideInVertically(tween(800, 300)) { it / 2 }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = onContinue,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PurpleAccent
                        )
                    ) {
                        Text(
                            "Continue",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                    }

                    TextButton(onClick = onShare) {
                        Text(
                            "Share Result",
                            fontSize = 15.sp,
                            color = PurpleLight,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

// ── Sub-components ────────────────────────────────────────────────────────────

@Composable
private fun StatCell(
    icon: String,
    label: String,
    value: String,
    valueColor: Color = TextPrimary,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(icon, fontSize = 14.sp)
            Spacer(Modifier.width(4.dp))
            Text(
                label,
                fontSize = 11.sp,
                color = TextSecondary,
                textAlign = TextAlign.Center
            )
        }
        Text(
            value,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = valueColor,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun VerticalDivider() {
    Box(
        modifier = Modifier
            .width(1.dp)
            .height(52.dp)
            .background(CardBorder)
    )
}

/** Eight small colored dots arranged in a ring around the check circle */
@Composable
private fun ConfettiDots() {
    val colors = listOf(
        Color(0xFFFF6B6B), Color(0xFFFFD93D), Color(0xFF6BCB77),
        Color(0xFF4D96FF), Color(0xFFFF6BFF), Color(0xFFFFB347),
        Color(0xFF00CEC9), Color(0xFFA29BFE)
    )
    val radius = 64.dp
    Box(modifier = Modifier.size(140.dp)) {
        colors.forEachIndexed { i, color ->
            val angle = (360.0 / colors.size * i).toFloat()
            val rad = Math.toRadians(angle.toDouble())
            val x = (70 + radius.value * Math.cos(rad)).dp
            val y = (70 + radius.value * Math.sin(rad)).dp
            Box(
                modifier = Modifier
                    .offset(x = x - 5.dp, y = y - 5.dp)
                    .size(7.dp)
                    .clip(CircleShape)
                    .background(color)
            )
        }
    }
}

// ── Preview ───────────────────────────────────────────────────────────────────
@Preview(showSystemUi = true)
@Composable
fun SessionCompleteScreenPreview() {
    MaterialTheme {
        SessionCompleteScreen()
    }
}