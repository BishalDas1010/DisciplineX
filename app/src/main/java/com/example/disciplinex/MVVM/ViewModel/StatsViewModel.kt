package com.example.disciplinex.MVVM.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.disciplinex.MVVM.Repo.DisciplineRepository
import com.example.disciplinex.SCREENS.MonthStats
import com.example.disciplinex.SCREENS.WeekStats
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class StatsViewModel(
    private val repository: DisciplineRepository
) : ViewModel() {

    val weeklyStats = repository.getWeeklyStats()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), WeekStats())

    val monthlyStats = repository.getMonthlyStats()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MonthStats())
}