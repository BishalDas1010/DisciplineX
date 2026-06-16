
package com.example.disciplinex.SCREENS
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.disciplinex.R

// ── Color Palette ─────────────────────────────────────────────────────────────
private val BgDark        = Color(0xFF0D0D1A)
private val SurfaceCard   = Color(0xFF1A1A2E)
private val CardBorder    = Color(0xFF252540)
private val PurpleAccent  = Color(0xFF6C63FF)
private val PurpleLight   = Color(0xFF8B85FF)
private val TextPrimary   = Color(0xFFFFFFFF)
private val TextSecondary = Color(0xFF7A7A9A)
private val TextMuted     = Color(0xFF4A4A6A)
private val BarSelected   = Color(0xFF6C63FF)
private val BarDefault    = Color(0xFF2A2A50)
private val StreakOrange  = Color(0xFFFF6B35)
private val ToggleBg      = Color(0xFF1E1E35)
private val BlueAccent    = Color(0xFF4DA6FF)
private val OrangeAccent  = Color(0xFFFF9500)

// ── Data Models ───────────────────────────────────────────────────────────────
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
    val longestStreak: String  = "15 Days",
    val sessionsCompleted: Int = 42,
    val dailyGoalAvg: String   = "75%"
)

data class MonthPoint(val week: String, val hours: Float)

data class FocusCategory(val name: String, val percent: Int, val time: String, val color: Color)

data class MonthStats(
    val month: String = "May 2025",
    val points: List<MonthPoint> = listOf(
        MonthPoint("1–7\nMay",   12f),
        MonthPoint("8–14\nMay",  18f),
        MonthPoint("15–21\nMay", 26f),
        MonthPoint("22–28\nMay", 16f),
        MonthPoint("29–31\nMay", 19f),
    ),
    val totalFocusTime: String = "91h 30m",
    val longestStreak: String  = "15 Days",
    val sessionsCompleted: Int = 42,
    val dailyGoalAvg: String   = "75%",
    val categories: List<FocusCategory> = listOf(
        FocusCategory("Work",    60, "54h 54m", PurpleAccent),
        FocusCategory("Study",   30, "27h 27m", BlueAccent),
        FocusCategory("Reading", 10, "9h 9m",   OrangeAccent),
    )
)

// ── Main Screen ───────────────────────────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticsScreen(
    weekStats:  WeekStats  = WeekStats(),
    monthStats: MonthStats = MonthStats(),
    onBack: () -> Unit = {}
) {
    // 0 = Weekly, 1 = Monthly
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        containerColor = BgDark,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (selectedTab == 0) "Statistics (Weekly)" else "Statistics (Monthly)",
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
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BgDark)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            Spacer(Modifier.height(8.dp))

            // ── Toggle ────────────────────────────────────────────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(ToggleBg)
                    .padding(4.dp)
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

            // ── Content switches on tab ───────────────────────────────────────
            if (selectedTab == 0) {
                WeeklyContent(stats = weekStats)
            } else {
                MonthlyContent(stats = monthStats)
            }
        }
    }
}

// ── Weekly Content ────────────────────────────────────────────────────────────
@Composable
private fun WeeklyContent(stats: WeekStats) {
    val maxHours = stats.bars.maxOf { it.hours }

    // Date range row
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(painter = painterResource(R.drawable.play), contentDescription = "Prev",
            tint = TextSecondary, modifier = Modifier.size(22.dp))
        Text(stats.dateRange, color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Medium)
        Icon(painter = painterResource(R.drawable.play), contentDescription = "Next",
            tint = TextSecondary, modifier = Modifier.size(22.dp))
    }

    Spacer(Modifier.height(20.dp))

    // Bar chart card
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(SurfaceCard)
            .border(1.dp, CardBorder, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Text("Focus Time (Hours)", color = TextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Medium)
        Spacer(Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            // Y-axis
            Column(
                modifier = Modifier.width(28.dp).fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                listOf("4h", "3h", "2h", "1h", "0h").forEach { label ->
                    Text(label, color = TextMuted, fontSize = 10.sp,
                        textAlign = TextAlign.End, modifier = Modifier.fillMaxWidth())
                }
            }
            Spacer(Modifier.width(8.dp))
            // Bars
            Row(
                modifier = Modifier.weight(1f).fillMaxHeight(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom
            ) {
                stats.bars.forEach { bar -> BarColumn(bar = bar, maxHours = maxHours) }
            }
        }

        Spacer(Modifier.height(8.dp))

        // X-axis labels
        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 36.dp),
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
    StatsGrid(
        totalFocusTime     = stats.totalFocusTime,
        longestStreak      = stats.longestStreak,
        sessionsCompleted  = stats.sessionsCompleted.toString(),
        dailyGoalAvg       = stats.dailyGoalAvg
    )
    Spacer(Modifier.height(16.dp))
}

// ── Monthly Content ───────────────────────────────────────────────────────────
@Composable
private fun MonthlyContent(stats: MonthStats) {
    val animProgress = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        animProgress.animateTo(1f, tween(900, easing = FastOutSlowInEasing))
    }

    // Date range row
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(painter = painterResource(R.drawable.play), contentDescription = "Prev",
            tint = TextSecondary, modifier = Modifier.size(22.dp))
        Text(stats.month, color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Medium)
        Icon(painter = painterResource(R.drawable.play), contentDescription = "Next",
            tint = TextSecondary, modifier = Modifier.size(22.dp))
    }

    Spacer(Modifier.height(20.dp))

    // Line chart card
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(SurfaceCard)
            .border(1.dp, CardBorder, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Text("Focus Time (Hours)", color = TextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Medium)
        Spacer(Modifier.height(12.dp))

        val maxH = stats.points.maxOf { it.hours }
        val yLabels = listOf("30h", "25h", "20h", "15h", "10h", "5h", "0h")

        Row(
            modifier = Modifier.fillMaxWidth().height(220.dp),
        ) {
            // Y-axis
            Column(
                modifier = Modifier.width(32.dp).fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                yLabels.forEach { label ->
                    Text(label, color = TextMuted, fontSize = 10.sp,
                        textAlign = TextAlign.End, modifier = Modifier.fillMaxWidth())
                }
            }

            Spacer(Modifier.width(4.dp))

            // Canvas for line chart
            Canvas(modifier = Modifier.weight(1f).fillMaxHeight()) {
                val w = size.width
                val h = size.height
                val maxVal = 30f
                val prog = animProgress.value

                // Dashed grid lines
                val gridCount = 6
                for (i in 0..gridCount) {
                    val y = h * i / gridCount
                    drawLine(
                        color = Color(0xFF252540),
                        start = Offset(0f, y),
                        end = Offset(w, y),
                        strokeWidth = 1f,
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(8f, 6f))
                    )
                }

                val pts = stats.points.mapIndexed { idx, pt ->
                    val x = if (stats.points.size == 1) w / 2f
                    else w * idx / (stats.points.size - 1)
                    val y = h - (pt.hours / maxVal) * h
                    Offset(x, y)
                }

                // Animated draw up to progress
                val totalPts = pts.size
                val animPts = if (prog >= 1f) pts else {
                    val floatIdx = prog * (totalPts - 1)
                    val lo = floatIdx.toInt().coerceIn(0, totalPts - 2)
                    val frac = floatIdx - lo
                    pts.take(lo + 1) + listOf(
                        Offset(
                            pts[lo].x + (pts[lo + 1].x - pts[lo].x) * frac,
                            pts[lo].y + (pts[lo + 1].y - pts[lo].y) * frac
                        )
                    )
                }

                // Fill area underline
                if (animPts.size >= 2) {
                    val fillPath = Path().apply {
                        moveTo(animPts.first().x, h)
                        animPts.forEach { lineTo(it.x, it.y) }
                        lineTo(animPts.last().x, h)
                        close()
                    }
                    drawPath(
                        path = fillPath,
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF6C63FF).copy(alpha = 0.45f),
                                Color(0xFF6C63FF).copy(alpha = 0.0f)
                            ),
                            startY = 0f,
                            endY = h
                        )
                    )

                    // Line
                    val linePath = Path().apply {
                        moveTo(animPts.first().x, animPts.first().y)
                        animPts.drop(1).forEach { lineTo(it.x, it.y) }
                    }
                    drawPath(
                        path = linePath,
                        color = PurpleLight,
                        style = Stroke(width = 2.5f, cap = StrokeCap.Round)
                    )
                }

                // Dots + value labels (only on fully reached points)
                pts.take(animPts.size).forEachIndexed { i, pt ->
                    // white dot
                    drawCircle(color = Color.White, radius = 5f, center = pt)
                    drawCircle(color = PurpleLight, radius = 3f, center = pt)
                }
            }
        }

        Spacer(Modifier.height(8.dp))

        // X-axis week labels
        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 36.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            stats.points.forEach { pt ->
                Text(
                    pt.week,
                    color = TextSecondary,
                    fontSize = 10.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Value labels row (hours above each point)
        Spacer(Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 36.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            stats.points.forEach { pt ->
                Text(
                    "${pt.hours.toInt()}h",
                    color = TextPrimary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }

    Spacer(Modifier.height(16.dp))

    StatsGrid(
        totalFocusTime    = stats.totalFocusTime,
        longestStreak     = stats.longestStreak,
        sessionsCompleted = stats.sessionsCompleted.toString(),
        dailyGoalAvg      = stats.dailyGoalAvg
    )

    Spacer(Modifier.height(16.dp))

    // Focus Breakdown card
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(SurfaceCard)
            .border(1.dp, CardBorder, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Row {
            Text("Focus Breakdown", color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
            Text(" (Hours)", color = TextSecondary, fontSize = 13.sp)
        }
        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Donut chart
            DonutChart(
                categories = stats.categories,
                modifier = Modifier.size(110.dp)
            )

            Spacer(Modifier.width(20.dp))

            // Legend
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                stats.categories.forEach { cat ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(RoundedCornerShape(50))
                                .background(cat.color)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            cat.name,
                            color = TextSecondary,
                            fontSize = 13.sp,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            "${cat.percent}% (${cat.time})",
                            color = TextPrimary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }

    Spacer(Modifier.height(24.dp))
}

// ── Donut Chart ───────────────────────────────────────────────────────────────
@Composable
private fun DonutChart(categories: List<FocusCategory>, modifier: Modifier = Modifier) {
    val animProgress = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        animProgress.animateTo(1f, tween(900, easing = FastOutSlowInEasing))
    }

    Canvas(modifier = modifier) {
        val stroke = size.minDimension * 0.18f
        val inset  = stroke / 2f
        val arcSize = Size(size.width - stroke, size.height - stroke)
        val topLeft = Offset(inset / 2f, inset / 2f)

        var startAngle = -90f
        categories.forEach { cat ->
            val sweep = (cat.percent / 100f) * 360f * animProgress.value
            drawArc(
                color     = cat.color,
                startAngle = startAngle,
                sweepAngle = sweep,
                useCenter  = false,
                topLeft    = topLeft,
                size       = arcSize,
                style      = Stroke(width = stroke, cap = StrokeCap.Butt)
            )
            startAngle += (cat.percent / 100f) * 360f
        }
    }
}

// ── Shared Stats Grid ─────────────────────────────────────────────────────────
@Composable
private fun StatsGrid(
    totalFocusTime:   String,
    longestStreak:    String,
    sessionsCompleted: String,
    dailyGoalAvg:     String
) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        StatCard("Total Focus Time",    totalFocusTime,    modifier = Modifier.weight(1f))
        StatCard("Longest Streak",      longestStreak,     icon = "🔥", valueColor = StreakOrange, modifier = Modifier.weight(1f))
    }
    Spacer(Modifier.height(12.dp))
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        StatCard("Sessions Completed",  sessionsCompleted, modifier = Modifier.weight(1f))
        StatCard("Daily Goal Avg.",     dailyGoalAvg,      valueColor = PurpleLight, modifier = Modifier.weight(1f))
    }
}

// ── Bar Column ────────────────────────────────────────────────────────────────
@Composable
private fun BarColumn(bar: DayBar, maxHours: Float) {
    val animProgress = remember { Animatable(0f) }
    LaunchedEffect(bar) {
        animProgress.animateTo(1f, tween(700, easing = FastOutSlowInEasing))
    }

    val fraction     = (bar.hours / maxHours) * animProgress.value
    val maxBarHeight = 140.dp
    val barHeight    = maxBarHeight * fraction

    Column(
        modifier = Modifier.width(28.dp).height(maxBarHeight),
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
    label:      String,
    value:      String,
    icon:       String? = null,
    valueColor: Color   = TextPrimary,
    modifier:   Modifier = Modifier
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
            Text(value, color = valueColor, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }
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