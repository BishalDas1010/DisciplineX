package com.example.disciplinex.MVVM.ViewModel   // consider renaming to lowercase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.disciplinex.MVVM.Repo.FakeDisciplineRepository
import com.example.disciplinex.ui.theme.DisciplineXTheme

class MainActivity : ComponentActivity() {
    private val repository = FakeDisciplineRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DisciplineXTheme {
                val homeViewModel = viewModel<HomeViewModel>(factory = viewModelFactory { HomeViewModel(repository) })
                val focusViewModel = viewModel<FocusViewModel>(factory = viewModelFactory { FocusViewModel(repository) })
                val shieldViewModel = viewModel<ShieldViewModel>(factory = viewModelFactory { ShieldViewModel(repository) })
                val statsViewModel = viewModel<StatsViewModel>(factory = viewModelFactory { StatsViewModel(repository) })

                AppNavHost(
                    homeViewModel = homeViewModel,
                    focusViewModel = focusViewModel,
                    shieldViewModel = shieldViewModel,
                    statsViewModel = statsViewModel
                )
            }
        }
    }
}

// Custom factory (rename if you like)
inline fun <reified T : ViewModel> viewModelFactory(crossinline creator: () -> T) =
    object : ViewModelProvider.Factory {
        override fun <U : ViewModel> create(modelClass: Class<U>): U {
            if (modelClass.isAssignableFrom(T::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return creator() as U
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }