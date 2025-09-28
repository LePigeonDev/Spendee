package com.example.spendee.router

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavHostController
import com.example.spendee.ui.SpendViewModel
import com.example.spendee.ui.screens.AddSpendScreen
import com.example.spendee.ui.screens.HomeSpendScreen
import com.example.spendee.ui.screens.StatsSpendScreen

@Composable
fun AppNavGraph(
    navController: NavHostController,
    vm: SpendViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Destinations.Home.route,
        modifier = modifier
    ) {
        composable(Destinations.Home.route) {
            HomeSpendScreen(
                vm = vm,
                onAddClick = {
                    navController.navigate(Destinations.AddSpend.route)
                },
                onStatClick = {
                    navController.navigate(Destinations.StatsSpend.route)
                }
            )
        }

        composable(Destinations.AddSpend.route) {
            AddSpendScreen(
                vm = vm,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Destinations.StatsSpend.route) {
            StatsSpendScreen(
                vm = vm,
                onBack= { navController.popBackStack() }
            )
        }
    }
}