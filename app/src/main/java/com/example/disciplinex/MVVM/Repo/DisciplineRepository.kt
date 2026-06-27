package com.example.disciplinex.MVVM.Repo

import com.example.disciplinex.MVVM.FocusSession
import com.example.disciplinex.MVVM.Habit
import com.example.disciplinex.SCREENS.MonthStats
import com.example.disciplinex.SCREENS.WeekStats
import kotlinx.coroutines.flow.Flow

interface DisciplineRepository {
    // Habits
    fun getAllHabits(): Flow<List<Habit>>
    suspend fun addHabit(habit: Habit)

    // Sessions
    fun getTodayFocusMinutes(): Flow<Int>          // total minutes today
    fun getCurrentStreak(): Flow<Int>              // consecutive days with focus
    fun getWeeklyStats(): Flow<WeekStats>          // for Stats screen
    fun getMonthlyStats(): Flow<MonthStats>
    suspend fun startSession(durationMinutes: Int, mode: String): FocusSession
    suspend fun endSession(sessionId: Int, actualMinutes: Int)

    // Blocked Apps
    fun getBlockedApps(): Flow<List<BlockedApp>>
    suspend fun toggleBlockedApp(appId: Int, blocked: Boolean)
}