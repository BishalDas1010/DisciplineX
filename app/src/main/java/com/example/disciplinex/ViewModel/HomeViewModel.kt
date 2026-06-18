package com.example.disciplinex.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.disciplinex.MVVM.Habit
import com.example.disciplinex.Repo.DisciplineRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: DisciplineRepository
) : ViewModel() {

    val todayMinutes = repository.getTodayFocusMinutes()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val streak = repository.getCurrentStreak()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val habits = repository.getAllHabits()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val blockedAppsCount = repository.getBlockedApps()
        .map { it.count { app -> app.isBlocked } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    fun addHabit(title: String) {
        viewModelScope.launch {
            repository.addHabit(Habit(title = title))
        }
    }

    // For daily goal progress (e.g., 75%)
    val dailyGoalProgress = todayMinutes.map { minutes ->
        val goal = 180 // 3 hours
        (minutes.toFloat() / goal).coerceIn(0f, 1f)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0f)
}