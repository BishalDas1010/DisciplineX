package com.example.disciplinex.SCREENS
import androidx.compose.foundation.background
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.disciplinex.R


// Color tokens
private val BgDeep       = Color(0xFF0D0D1A)
private val BgCard       = Color(0xFF1A1A2E)
private val BgCardAlt    = Color(0xFF12121F)
private val AccentBlue   = Color(0xFF4F5FFF)
private val AccentPurple = Color(0xFF7C4DFF)
private val BarActive    = Color(0xFF3B4FFF)
private val BarDim       = Color(0xFF2A2A45)
private val TextPrimary  = Color(0xFFFFFFFF)
private val TextSecond   = Color(0xFFAAAAAA)
private val TextMuted    = Color(0xFF666688)
private val XpGold       = Color(0xFF7C4DFF)

@Preview(showSystemUi = true)
@Composable
fun home(){
    HomeScreen()
}
@Composable
fun HomeScreen() {
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

            Spacer(modifier = Modifier.height(48.dp))

            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column {
                    Text(
                        text = "Good Evening,",
                        color = TextSecond,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Vishal ",
                            color = TextPrimary,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(text = "👋", fontSize = 24.sp)
                    }
                }

                // Bell icon with red badge
                Box {
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
                    // Red notification dot
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE53935))
                            .align(Alignment.TopEnd)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Today's Focus Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(18.dp))
                    .background(BgCard)
                    .padding(20.dp)
            ) {
                Column {
                    Text(
                        text = "Today's Focus",
                        color = TextSecond,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "2h 15m",
                                color = TextPrimary,
                                fontSize = 36.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "of 3 hours goal",
                                color = TextMuted,
                                fontSize = 13.sp
                            )
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            // Circular blue icon
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(AccentBlue.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.play),
                                    contentDescription = null,
                                    tint = AccentBlue,
                                    modifier = Modifier.size(18.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            // Bar chart
                            BarChart()
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            //  Streak + Daily Goal Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Current Streak
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(18.dp))
                        .background(BgCard)
                        .padding(18.dp)
                ) {
                    Column {
                        Text(
                            text = "Current Streak",
                            color = TextSecond,
                            fontSize = 12.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "🔥", fontSize = 20.sp)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "7 Days",
                                color = TextPrimary,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Keep it up!",
                            color = TextMuted,
                            fontSize = 12.sp
                        )
                    }
                }

                // Daily Goal
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(18.dp))
                        .background(BgCard)
                        .padding(18.dp)
                ) {
                    Column {
                        Text(
                            text = "Daily Goal",
                            color = TextSecond,
                            fontSize = 12.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "75%",
                            color = TextPrimary,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
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

            Spacer(modifier = Modifier.height(14.dp))

            //  Level / XP Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(18.dp))
                    .background(BgCard)
                    .padding(18.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Hexagon-ish icon placeholder
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .shadow(
                                elevation = 12.dp,
                                shape = RoundedCornerShape(14.dp),
                                ambientColor = AccentPurple,
                                spotColor = AccentPurple
                            )
                            .clip(RoundedCornerShape(14.dp))
                            .background(
                                Brush.linearGradient(
                                    listOf(Color(0xFF5A49B6), Color(0xFF2D1F6E))
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.play),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Level 4",
                            color = TextSecond,
                            fontSize = 13.sp
                        )
                        Text(
                            text = "Focus Warrior",
                            color = TextPrimary,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "680 / 1000 XP",
                            color = TextMuted,
                            fontSize = 12.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        LinearProgressIndicator(
                            progress = { 0.68f },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp)),
                            color = XpGold,
                            trackColor = BarDim,
                            strokeCap = StrokeCap.Round
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // ── Start Focus Session Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        Brush.horizontalGradient(
                            listOf(AccentBlue, AccentPurple)
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    // Play triangle
                    Icon(
                        painter = painterResource(R.drawable.play),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Start Focus Session",
                        color = Color.White,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Bottom Nav
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                    .background(BgCard)
                    .padding(vertical = 14.dp, horizontal = 30.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BottomNavItem(iconRes = R.drawable.play, selected = true)
                BottomNavItem(iconRes = R.drawable.play, selected = false)
                BottomNavItem(iconRes = R.drawable.play, selected = false)
                BottomNavItem(iconRes = R.drawable.play, selected = false)
            }
        }
    }
}

//Bar Chart (mini)
@Composable
private fun BarChart() {
    val heights = listOf(0.4f, 0.6f, 0.5f, 0.8f, 1.0f)
    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        heights.forEachIndexed { i, h ->
            Box(
                modifier = Modifier
                    .width(6.dp)
                    .height((28 * h).dp)
                    .clip(RoundedCornerShape(topStart = 3.dp, topEnd = 3.dp))
                    .background(if (i == heights.lastIndex) AccentBlue else BarDim)
            )
        }
    }
}

//  Bottom Nav Item
@Composable
private fun BottomNavItem(iconRes: Int, selected: Boolean) {
    Box(
        modifier = Modifier
            .size(44.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(if (selected) AccentBlue.copy(alpha = 0.15f) else Color.Transparent),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = null,
            tint = if (selected) AccentBlue else TextMuted,
            modifier = Modifier.size(22.dp)
        )
    }
}