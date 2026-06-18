package com.example.disciplinex.MVVM

data class FocusSession(
    val id: Int = 0,
    val durationMinutes: Int,        // planned
    val actualMinutes: Int,          // actual focused time
    val mode: String,                // "Normal", "Strict", "Monk"
    val startTime: Long,
    val endTime: Long = 0,
    val isCompleted: Boolean = false
)