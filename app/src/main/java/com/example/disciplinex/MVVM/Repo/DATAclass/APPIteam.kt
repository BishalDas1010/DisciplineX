package com.example.disciplinex.MVVM.Repo.DATAclass


import androidx.compose.ui.graphics.painter.Painter

data class AppItem(
    val id: Int,                // packageName.hashCode()
    val name: String,
    val iconPainter: Painter,   // real app icon
    val isBlocked: Boolean = false
)