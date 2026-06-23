package com.example.disciplinex.MVVM.ViewModel

 // or keep it in the main package

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.disciplinex.SCREENS.*

@Composable
fun AppNavHost(
    homeViewModel: HomeViewModel,
    focusViewModel: FocusViewModel,
    shieldViewModel: ShieldViewModel,
    statsViewModel: StatsViewModel
) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(
                viewModel = homeViewModel,
                onStartSession = { navController.navigate(Screen.Focus_Screeen.route) },
                onNavStats = { navController.navigate(Screen.Stats.route) },
                onShieldTap = { navController.navigate(Screen.Shield.route) }
            )
        }

        composable(Screen.Focus_Screeen.route) {
            FocusSessionScreen(
                viewModel = focusViewModel,
                onNavigateToFocusing = { navController.navigate(Screen.Focus_session.route) },
                on_startSession = {navController.navigate(Screen.Home.route)},
                onShild_screenn = {navController.navigate(Screen.Shield.route)}
            )
        }

        composable(Screen.Focus_session.route) {
            FocusingScreen(
                viewModel = focusViewModel,
                onEndSession = {
                    navController.navigate(Screen.Session_comp.route)
                },
                onSessionComplete ={
                    navController.navigate(Screen.Session_comp.route)
                }
            )
        }

        composable(Screen.Session_comp.route) {
            SessionCompleteScreen(
                onContinue = { navController.popBackStack(Screen.Home.route, inclusive = false) }
            )
        }

        composable(Screen.Shield.route) {
            Shield_Screen(
                viewModel = shieldViewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Stats.route) {
            Stats(
                viewModel = statsViewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}