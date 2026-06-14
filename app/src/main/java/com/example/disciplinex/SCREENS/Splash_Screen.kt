package com.example.disciplinex.SCREENS

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.disciplinex.R

@Preview(showSystemUi = true)
@Composable
fun SplashScreen() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF020A24),
                        Color(0xFF000814)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo card
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .shadow(
                        elevation = 25.dp,
                        shape = RoundedCornerShape(30.dp),
                        clip = false,
                        ambientColor = Color(0xFF00BFFF),
                        spotColor = Color(0xFF00FFFF)
                    )
                    .clip(RoundedCornerShape(30.dp))
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF5A49B6),
                                Color(0xFF2D246B)
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.lock),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(60.dp)
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            // App name
            Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(color = Color.White)) {
                        append("Focus")
                    }
                    withStyle(SpanStyle(color = Color(0xFF9D8DFF))) {
                        append(" Lock")
                    }
                },
                fontSize = 42.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Stay focused. Build discipline.",
                color = Color.LightGray,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(40.dp))
            // Width is set via Modifier, not a parameter
            HorizontalDivider(
                modifier = Modifier.width(80.dp),
                thickness = 2.dp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Your productivity partner\nfor deep focus and growth.",
                textAlign = TextAlign.Center,
                color = Color.Gray,
                fontSize = 18.sp,
                lineHeight = 28.sp
            )
        }
    }
}