package com.example.disciplinex.MVVM

data class BlockedApp(
    val id: Int = 0,
    val name: String,
    val iconRes: Int,
    val isBlocked: Boolean = true
)