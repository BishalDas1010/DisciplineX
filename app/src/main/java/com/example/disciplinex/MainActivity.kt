package com.example.disciplinex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.disciplinex.MVVM.Repo.FakeDisciplineRepository
import com.example.disciplinex.MVVM.ViewModel.FocusViewModel
import com.example.disciplinex.MVVM.ViewModel.HomeViewModel
import com.example.disciplinex.MVVM.ViewModel.ShieldViewModel
import com.example.disciplinex.MVVM.ViewModel.StatsViewModel
import com.example.disciplinex.MVVM.ViewModel.viewModelFactory
import com.example.disciplinex.SCREENS.*

class MainActivity : ComponentActivity() {
    // Create the repository once (later swap with real Room)
    private val repository = FakeDisciplineRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Create ViewModels using the repository
            val homeViewModel: HomeViewModel = viewModel(factory = viewModelFactory {
                HomeViewModel(
                    repository
                )
            })
            val focusViewModel: FocusViewModel = viewModel(factory = viewModelFactory {
                FocusViewModel(
                    repository
                )
            })
            val shieldViewModel: ShieldViewModel = viewModel(factory = viewModelFactory {
                ShieldViewModel(
                    repository
                )
            })
            val statsViewModel: StatsViewModel = viewModel(factory = viewModelFactory {
                StatsViewModel(
                    repository
                )
            })

            MyAppNavigation(
                homeViewModel = homeViewModel,
                focusViewModel = focusViewModel,
                shieldViewModel = shieldViewModel,
                statsViewModel = statsViewModel
            )
        }
    }
}

@Composable
fun MyAppNavigation(
    homeViewModel: HomeViewModel,
    focusViewModel: FocusViewModel,
    shieldViewModel: ShieldViewModel,
    statsViewModel: StatsViewModel
) {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color(0xFF0B0B18),
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // HOME
            composable(Screen.Home.route) {
                HomeScreen(
                    viewModel = homeViewModel,
                    onStartSession = {
                        navController.navigate(Screen.Focus_Screeen.route) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                        }
                    },
                    onNavStats = {
                        navController.navigate(Screen.Stats.route) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                        }
                    },
                    onNavShield = {
                        navController.navigate(Screen.Shield.route) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                        }
                    },
                    onNavSettings = {
                        navController.navigate(Screen.Settings.route) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                        }
                    },
                    onShieldTap = {
                        navController.navigate(Screen.Shield.route) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                        }
                    }
                )
            }

            // STATS
            composable(Screen.Stats.route) {
                Stats(
                    viewModel = statsViewModel,
                    onBack = { navController.popBackStack() }
                )
            }

            // SHIELD
            composable(Screen.Shield.route) {
                Shield_Screen(
                    viewModel = shieldViewModel,
                    onBack = { navController.popBackStack() }
                )
            }

            // SETTINGS (keep as placeholder)
            composable(Screen.Settings.route) {
                SettingsScreen()
            }

            // FOCUS – Setup (Duration & Mode selection)
            composable(Screen.Focus_Screeen.route) {
                FocusSessionScreen(
                    viewModel = focusViewModel,
                    onNavigateToFocusing = {
                        navController.navigate(Screen.Focus_session.route) {
                            // Clear back stack so user can't go back to setup
                            popUpTo(Screen.Focus_Screeen.route) { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    on_startSession = {
                        navController.navigate(Screen.Home.route){
                            popUpTo (Screen.Home.route){  inclusive = true}
                            launchSingleTop = true
                        }
                    },
                    onShild_screenn = {navController.navigate(Screen.Shield.route){
                        popUpTo(Screen.Focus_Screeen.route){inclusive=true}
                        launchSingleTop=true
                    } }
                )
            }

            // FOCUS – Active timer screen
            composable(Screen.Focus_session.route) {
                FocusingScreen(
                    viewModel = focusViewModel,
                    onEndSession = {
                        navController.navigate(Screen.Session_comp.route) {
                            popUpTo(Screen.Focus_session.route) { inclusive = true }
                        }
                    },
                    onSessionComplete = {
                        navController.navigate(Screen.Session_comp.route){
                            popUpTo(Screen.Focus_session.route){
                                inclusive=true
                            }
                        }
                    }
                )
            }

            // SESSION COMPLETE
            composable(Screen.Session_comp.route) {
                SessionCompleteScreen(
                    onContinue = {
                        navController.popBackStack(Screen.Home.route, inclusive = false)
                    }
                )
            }
        }
    }
}

// Temporary placeholder – replace with your actual Settings UI
@Composable
fun SettingsScreen() {
    androidx.compose.material3.Text(
        text = "Settings Screen",
        color = Color.White
    )
}