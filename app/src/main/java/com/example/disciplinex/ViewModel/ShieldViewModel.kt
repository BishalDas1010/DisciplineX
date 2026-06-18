package com.example.disciplinex.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.disciplinex.Repo.DisciplineRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ShieldViewModel(
    private val repository: DisciplineRepository
) : ViewModel() {

    val blockedApps = repository.getBlockedApps()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun toggleAppBlock(appId: Int, blocked: Boolean) {
        viewModelScope.launch {
            repository.toggleBlockedApp(appId, blocked)
        }
    }
}