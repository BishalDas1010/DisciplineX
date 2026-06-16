package com.example.disciplinex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge           // ⬅️ IMPORT THIS!
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.savedstate.savedState
import com.example.disciplinex.SCREENS.FocusSessionScreen
import com.example.disciplinex.SCREENS.FocusingScreen

// Import your screens
import com.example.disciplinex.SCREENS.HomeScreen
import com.example.disciplinex.SCREENS.Screen
import com.example.disciplinex.SCREENS.Shield_Screen
import com.example.disciplinex.SCREENS.Stats

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // ✅ Make status bar transparent and match dark theme
        enableEdgeToEdge()
        setContent {
            MyAppNavigation()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyAppNavigation() {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color(0xFF0B0B18),
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    // Pass navigation lambdas here
                    onNavHome = {
                        // Usually you don't need this for Home, but keep it
                        navController.navigate(Screen.Home.route) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    onNavStats = {
                        navController.navigate(Screen.Stats.route) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    onNavShield = {
                        navController.navigate(Screen.Shield.route) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    onNavSettings = {
                        navController.navigate(Screen.Settings.route) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    onStartSession = {
                        navController.navigate(Screen.Focus_Screeen.route){
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    onShieldTap = {
                        // Navigate to Shield screen when the card is tapped
                        navController.navigate(Screen.Shield.route) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                )
            }
            composable(Screen.Stats.route) { Stats() }
            composable(Screen.Shield.route) { Shield_Screen() }
            composable(Screen.Settings.route) { SettingsScreen() }
            composable (Screen.Focus_Screeen.route){ FocusSessionScreen()  }
            composable(Screen.Focus_session.route) { FocusingScreen()  }
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