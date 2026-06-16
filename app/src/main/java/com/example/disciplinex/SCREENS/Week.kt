package com.example.disciplinex.SCREENS

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Canvas
import androidx.compose.foundation.Canvas
import androidx.compose.ui.res.painterResource
import com.example.disciplinex.R

// ── Color Palette ─────────────────────────────────────────────────────────────
private val BgDark         = Color(0xFF0D0D1A)
private val SurfaceCard    = Color(0xFF1A1A2E)
private val CardBorder     = Color(0xFF252540)
private val PurpleAccent   = Color(0xFF6C63FF)
private val PurpleDim      = Color(0xFF3D3870)
private val PurpleLight    = Color(0xFF8B85FF)
private val TextPrimary    = Color(0xFFFFFFFF)
private val TextSecondary  = Color(0xFF7A7A9A)
private val TextMuted      = Color(0xFF4A4A6A)
private val BarSelected    = Color(0xFF6C63FF)
private val BarDefault     = Color(0xFF2A2A50)
private val StreakOrange   = Color(0xFFFF6B35)
private val ToggleBg       = Color(0xFF1E1E35)

// ── Data ──────────────────────────────────────────────────────────────────────
data class DayBar(val day: String, val hours: Float, val isSelected: Boolean = false)

data class WeekStats(
    val dateRange: String = "12 - 18 May 2025",
    val bars: List<DayBar> = listOf(
        DayBar("Mon", 1.0f),
        DayBar("Tue", 2.5f),
        DayBar("Wed", 3.8f, isSelected = true),
        DayBar("Thu", 2.0f),
        DayBar("Fri", 1.5f),
        DayBar("Sat", 3.2f),
        DayBar("Sun", 0.8f),
    ),
    val totalFocusTime: String = "15h 30m",
    val longestStreak: String = "15 Days",
    val sessionsCompleted: Int = 42,
    val dailyGoalAvg: String = "75%"
)

// ── Main Screen ───────────────────────────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticsScreen(
    stats: WeekStats = WeekStats(),
    onBack: () -> Unit = {},
    onNavHome: () -> Unit = {},
    onNavStats: () -> Unit = {},
    onNavAchievements: () -> Unit = {},
    onNavSettings: () -> Unit = {}
) {
    var selectedTab by remember { mutableStateOf(0) } // 0=Weekly, 1=Monthly
    val maxHours = stats.bars.maxOf { it.hours }

    Scaffold(
        containerColor = BgDark,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Statistics (Weekly)",
                        color = TextPrimary,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 17.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(painter = painterResource(R.drawable.play), contentDescription = "Back", tint = TextPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BgDark)
            )
        },
        bottomBar = {

        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
        ) {
            Spacer(Modifier.height(8.dp))

            // ── Weekly / Monthly Toggle ───────────────────────────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(ToggleBg)
                    .padding(4.dp),
            ) {
                listOf("Weekly", "Monthly").forEachIndexed { i, label ->
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (selectedTab == i) PurpleAccent else Color.Transparent)
                            .clickable { selectedTab = i }
                            .padding(vertical = 10.dp)
                    ) {
                        Text(
                            label,
                            color = if (selectedTab == i) TextPrimary else TextSecondary,
                            fontWeight = if (selectedTab == i) FontWeight.SemiBold else FontWeight.Normal,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // ── Date Range Row ────────────────────────────────────────────────
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(painter = painterResource(R.drawable.play), contentDescription = "Prev", tint = TextSecondary, modifier = Modifier.size(22.dp))
                Text(
                    stats.dateRange,
                    color = TextPrimary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
                Icon(painter = painterResource(R.drawable.play), contentDescription = "Next", tint = TextSecondary, modifier = Modifier.size(22.dp))
            }

            Spacer(Modifier.height(20.dp))

            // ── Bar Chart Card ────────────────────────────────────────────────
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(SurfaceCard)
                    .border(1.dp, CardBorder, RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Text(
                    "Focus Time (Hours)",
                    color = TextSecondary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(Modifier.height(12.dp))

                // Y-axis + bars
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    verticalAlignment = Alignment.Bottom
                ) {
                    // Y-axis labels
                    Column(
                        modifier = Modifier
                            .width(28.dp)
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        listOf("4h", "3h", "2h", "1h", "0h").forEach { label ->
                            Text(label, color = TextMuted, fontSize = 10.sp, textAlign = TextAlign.End,
                                modifier = Modifier.fillMaxWidth())
                        }
                    }

                    Spacer(Modifier.width(8.dp))

                    // Bars
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        stats.bars.forEach { bar ->
                            BarColumn(bar = bar, maxHours = maxHours)
                        }
                    }
                }

                Spacer(Modifier.height(8.dp))

                // X-axis day labels (aligned with bars)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 36.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    stats.bars.forEach { bar ->
                        Text(
                            bar.day,
                            color = if (bar.isSelected) PurpleLight else TextSecondary,
                            fontSize = 11.sp,
                            fontWeight = if (bar.isSelected) FontWeight.SemiBold else FontWeight.Normal,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // ── Stats Grid ────────────────────────────────────────────────────
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard(
                    label = "Total Focus Time",
                    value = stats.totalFocusTime,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    label = "Longest Streak",
                    value = stats.longestStreak,
                    icon = "🔥",
                    valueColor = StreakOrange,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard(
                    label = "Sessions Completed",
                    value = stats.sessionsCompleted.toString(),
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    label = "Daily Goal Avg.",
                    value = stats.dailyGoalAvg,
                    valueColor = PurpleLight,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}

// ── Bar Column ────────────────────────────────────────────────────────────────
@Composable
private fun BarColumn(bar: DayBar, maxHours: Float) {
    val animProgress = remember { Animatable(0f) }
    LaunchedEffect(bar) {
        animProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 700, easing = FastOutSlowInEasing)
        )
    }

    val fraction = (bar.hours / maxHours) * animProgress.value
    val maxBarHeight = 140.dp
    val barHeight = maxBarHeight * fraction

    Column(
        modifier = Modifier
            .width(28.dp)
            .height(maxBarHeight),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .width(18.dp)
                .height(barHeight.coerceAtLeast(4.dp))
                .clip(RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp))
                .background(
                    if (bar.isSelected)
                        Brush.verticalGradient(listOf(PurpleLight, BarSelected))
                    else
                        Brush.verticalGradient(listOf(BarDefault, BarDefault))
                )
        )
    }
}

// ── Stat Card ─────────────────────────────────────────────────────────────────
@Composable
private fun StatCard(
    label: String,
    value: String,
    icon: String? = null,
    valueColor: Color = TextPrimary,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(SurfaceCard)
            .border(1.dp, CardBorder, RoundedCornerShape(14.dp))
            .padding(16.dp)
    ) {
        Text(label, color = TextSecondary, fontSize = 11.sp, fontWeight = FontWeight.Medium)
        Spacer(Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (icon != null) {
                Text(icon, fontSize = 18.sp)
                Spacer(Modifier.width(4.dp))
            }
            Text(
                value,
                color = valueColor,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


@Composable
private fun NavItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Icon(
            icon,
            contentDescription = label,
            tint = if (isSelected) PurpleAccent else TextSecondary,
            modifier = Modifier.size(22.dp)
        )
        Spacer(Modifier.height(3.dp))
        Text(
            label,
            color = if (isSelected) PurpleAccent else TextSecondary,
            fontSize = 10.sp,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
        )
    }
}

// ── Preview ───────────────────────────────────────────────────────────────────
@Preview(showBackground = true, backgroundColor = 0xFF0D0D1A, showSystemUi = true)
@Composable
fun StatisticsScreenPreview() {
    MaterialTheme {
        StatisticsScreen()
    }
}