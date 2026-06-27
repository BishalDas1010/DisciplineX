package com.example.disciplinex.MVVM.Repo

import androidx.compose.ui.graphics.Color
import com.example.disciplinex.MVVM.FocusSession
import com.example.disciplinex.MVVM.Habit
import com.example.disciplinex.MVVM.Repo.DATAclass.BlockedApp
import com.example.disciplinex.R
import com.example.disciplinex.SCREENS.DayBar
import com.example.disciplinex.SCREENS.FocusCategory
import com.example.disciplinex.SCREENS.MonthPoint
import com.example.disciplinex.SCREENS.MonthStats
import com.example.disciplinex.SCREENS.WeekStats
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update

class FakeDisciplineRepository : DisciplineRepository {
    // Use MutableStateFlow to simulate changes
    private val _habits = MutableStateFlow(
        listOf(
            Habit(title = "Morning Exercise"),
            Habit(title = "Read 10 Pages", isCompleted = true)
        )
    )
    override fun getAllHabits(): Flow<List<Habit>> = _habits.asStateFlow()

    override suspend fun addHabit(habit: Habit) {
        delay(200) // simulate network/db
        _habits.update { it + habit }
    }

    override fun getTodayFocusMinutes(): Flow<Int> = flowOf(135) // 2h 15m

    override fun getCurrentStreak(): Flow<Int> = flowOf(10) // 10 days

    override fun getWeeklyStats(): Flow<WeekStats> = flowOf(
        WeekStats(
            dateRange = "12 - 18 May 2025",
            bars = listOf(
                DayBar("Mon", 1.0f),
                DayBar("Tue", 2.5f),
                DayBar("Wed", 3.8f, isSelected = true),
                DayBar("Thu", 2.0f),
                DayBar("Fri", 1.5f),
                DayBar("Sat", 3.2f),
                DayBar("Sun", 0.8f),
            ),
            totalFocusTime = "15h 30m",
            longestStreak = "15 Days",
            sessionsCompleted = 42,
            dailyGoalAvg = "75%"
        )
    )

    override fun getMonthlyStats(): Flow<MonthStats> = flowOf(
        MonthStats(
            month = "May 2025",
            points = listOf(
                MonthPoint("1–7\nMay", 12f),
                MonthPoint("8–14\nMay", 18f),
                MonthPoint("15–21\nMay", 26f),
                MonthPoint("22–28\nMay", 16f),
                MonthPoint("29–31\nMay", 19f),
            ),
            totalFocusTime = "91h 30m",
            longestStreak = "15 Days",
            sessionsCompleted = 42,
            dailyGoalAvg = "75%",
            categories = listOf(
                FocusCategory("Work", 60, "54h 54m", Color(0xFF6C63FF)),
                FocusCategory("Study", 30, "27h 27m", Color(0xFF4DA6FF)),
                FocusCategory("Reading", 10, "9h 9m", Color(0xFFFF9500)),
            )
        )
    )

    override suspend fun startSession(durationMinutes: Int, mode: String): FocusSession {
        return FocusSession(
            durationMinutes = durationMinutes,
            actualMinutes = 0,
            mode = mode,
            startTime = System.currentTimeMillis()
        )
    }

    override suspend fun endSession(sessionId: Int, actualMinutes: Int) {
        // do nothing in fake
    }

    private val _blockedApps = MutableStateFlow(
        listOf(
            BlockedApp(id = 1, name = "Instagram", iconRes = R.drawable.lock, isBlocked = true),
            BlockedApp(id = 2, name = "YouTube", iconRes = R.drawable.lock, isBlocked = true),
            BlockedApp(id = 3, name = "Facebook", iconRes = R.drawable.lock, isBlocked = true),
        )
    )
    override fun getBlockedApps(): Flow<List<BlockedApp>> = _blockedApps.asStateFlow()

    override suspend fun toggleBlockedApp(appId: Int, blocked: Boolean) {
        _blockedApps.update { list ->
            list.map { if (it.id == appId) it.copy(isBlocked = blocked) else it }
        }
    }
}