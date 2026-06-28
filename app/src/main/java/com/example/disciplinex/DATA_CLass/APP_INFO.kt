package com.example.disciplinex.DATA_CLass

import androidx.compose.ui.graphics.painter.Painter

data class AppInfo(
    val packageName: String,
    val appName: String,
    val icon: Painter?
)