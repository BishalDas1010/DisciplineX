package com.example.disciplinex.ViewModel

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
    private val repository: DisciplineRepository
) : ViewModel() {

    private val _blockedApps = repository.getBlockedApps()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val blockedApps: StateFlow<List<BlockedApp>> = _blockedApps

    private var currentSessionId: Int? = null
    private var timerJob: Job? = null
    private val _remainingSeconds = MutableStateFlow(0)
    val remainingSeconds: StateFlow<Int> = _remainingSeconds.asStateFlow()

    fun startNewSession(durationMinutes: Int, mode: String) {
        viewModelScope.launch {
            val session = repository.startSession(durationMinutes, mode)
            currentSessionId = session.id
            _remainingSeconds.value = durationMinutes * 60
            startTimer()
        }
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (_remainingSeconds.value > 0) {
                delay(1000)
                _remainingSeconds.value -= 1
            }
            // Timer finished – end session automatically
            endSession()
        }
    }

    fun pauseTimer() { timerJob?.cancel() }
    fun resumeTimer() { if (_remainingSeconds.value > 0) startTimer() }

    fun endSession() {
        timerJob?.cancel()
        currentSessionId?.let { id ->
            viewModelScope.launch {
                val actualMinutes =0
                    repository.endSession(id, actualMinutes)
                currentSessionId = null
            }
        }
    }
}