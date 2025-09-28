package com.example.spendee.router

sealed class Destinations (val route: String) {
    data object Home : Destinations("home")

    data object AddSpend : Destinations("addSpend")

    data object StatsSpend : Destinations("statistique")
}