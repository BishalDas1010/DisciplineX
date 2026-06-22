package com.example.disciplinex.MVVM.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.disciplinex.MVVM.BlockedApp
import com.example.disciplinex.MVVM.Repo.DisciplineRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FocusViewModel(
    // Repository acts as a bridge between ViewModel and Database/API
    private val repository: DisciplineRepository
) : ViewModel() {
    //this fetch all the Blocked apps from my db
    private val _blockedApps = repository.getBlockedApps()
        //ViewModel converts it to StateFlow using .stateIn()
        //.state In function need three things Scope = from where the code will start (where)
        //started = from when   the flow should start collecting.(when)
        //initialValue =
        .stateIn(scope = viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    //here i'm just store  All  events into state-flow formate
    val blockedApps: StateFlow<List<BlockedApp>> = _blockedApps
    //
    private val _isRunning = MutableStateFlow(false)
    val isRunning: StateFlow<Boolean> = _isRunning.asStateFlow()

    private var currentSessionId: Int? = null
    private var timerJob: Job? = null
    private val _remainingSeconds = MutableStateFlow(0)
    val remainingSeconds: StateFlow<Int> = _remainingSeconds.asStateFlow()


    // Inside FocusViewModel

    private val _totalSeconds = MutableStateFlow(0)
    val totalSeconds: StateFlow<Int> = _totalSeconds.asStateFlow()


    //
    private val _selectedMode = MutableStateFlow("Monk Mode")
    val selectedMode: StateFlow<String> = _selectedMode.asStateFlow()


    // Inside FocusViewModel class:

    private val _selectedDurationMinutes = MutableStateFlow(0)
    val selectedDurationMinutes: StateFlow<Int> = _selectedDurationMinutes.asStateFlow()

    fun updateDuration(minutes: Int) {
        _selectedDurationMinutes.value = minutes
    }

    fun updateMode(mode: String) {
        _selectedMode.value = mode
    }


    // Updated: sets running state to true
    private fun startTimer() {
        timerJob?.cancel()
        _isRunning.value = true   // mark as running
        timerJob = viewModelScope.launch {
            while (_remainingSeconds.value > 0) {
                delay(1000)
                _remainingSeconds.value -= 1
            }
            // Timer finished – end session automatically
            _isRunning.value = false  // mark as stopped
            endSession()
        }
    }

    // Updated: sets running state to false
    fun pauseTimer() {
        timerJob?.cancel()
        _isRunning.value = false  // mark as paused
    }

    // Updated: resume uses startTimer() which sets state to true
    fun resumeTimer() {
        if (_remainingSeconds.value > 0) startTimer()
    }

    // 🆕 Optional helper to toggle with one call (you can use this in your click handler)
    fun toggleTimer() {
        if (_isRunning.value) pauseTimer() else resumeTimer()
    }

    // Updated: ensures running state is false when session ends
    fun endSession() {
        timerJob?.cancel()
        _isRunning.value = false  // clean reset
        currentSessionId?.let { id ->
            viewModelScope.launch {
                val actualMinutes = 0
                repository.endSession(id, actualMinutes)
                currentSessionId = null
            }
        }
    }

    fun startNewSession(durationMinutes: Int, mode: String) {
        viewModelScope.launch {
            val session = repository.startSession(durationMinutes, mode)
            currentSessionId = session.id
            val total = durationMinutes * 60
            _totalSeconds.value = total          // store total
            _remainingSeconds.value = total      // start with full time
            startTimer()
        }
    }
}