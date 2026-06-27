package com.example.disciplinex.MVVM.ViewModel

import androidx.lifecycle.ViewModel
import com.example.disciplinex.DATA_CLass.AppInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class Focucs_blockedAppViewmodel(): ViewModel() {

    private val _blockedApps = MutableStateFlow<List<AppInfo>>(emptyList())
    val blockedApps: StateFlow<List<AppInfo>> = _blockedApps.asStateFlow()

    fun toggleBlockApp(app: AppInfo) {
        _blockedApps.update { currentList ->
            if (currentList.any { it.packageName == app.packageName }) {
                currentList.filter { it.packageName != app.packageName }
            } else {
                currentList + app
            }
        }
    }
}