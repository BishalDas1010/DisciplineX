package com.example.disciplinex.MVVM.ViewModel

// FocusViewModel.kt (additions)
import RealDisciplineRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.disciplinex.MVVM.Repo.DATAclass.AppItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FocusViewModel_blockedAPP(
    private val repository: RealDisciplineRepository
) : ViewModel() {

    private val _apps = MutableStateFlow<List<AppItem>>(emptyList())
    val apps = _apps.asStateFlow()

    init {
        loadApps()
    }

    fun loadApps() {
        viewModelScope.launch {
            _apps.value = repository.getInstalledApps()
        }
    }

    fun blockSelectedApps(selectedIds: Set<Int>) {
        viewModelScope.launch {
            selectedIds.forEach { id ->
                repository.toggleBlockedApp(id, blocked = true)
            }
            // Refresh list to update isBlocked flags
            _apps.value = repository.getInstalledApps()
        }
    }
}