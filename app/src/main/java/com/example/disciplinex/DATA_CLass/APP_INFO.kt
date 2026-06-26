package com.example.disciplinex.DATA_CLass

// AppInfo.kt
import androidx.compose.ui.graphics.painter.Painter

data class AppInfo(
    val packageName: String,
    val appName: String,
    val icon: Painter? = null  // or Drawable
)