package com.example.disciplinex.SCREENS

import com.example.disciplinex.R

sealed class Screen(val route: String, val title: String, val iconRes: Int) {
    object Home     : Screen("home", "Home", R.drawable.home)
    object Stats    : Screen("Stats", "Stats", R.drawable.graph)   // your graph icon
    object Shield   : Screen("Shield_Screen", "Shield", R.drawable.shield)
    object Settings : Screen("settings", "Settings", R.drawable.settings)
    object Focus_Screeen : Screen("Focus", "Focus", R.drawable.settings)

    object  Focus_session: Screen("Fousc_session","sesseion",R.drawable.sandclock)
    object  Session_comp : Screen("Session_comp", "Session_comp",R.drawable.sandclock)

}