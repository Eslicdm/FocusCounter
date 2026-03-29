package com.eslirodrigues.focuscounter.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.eslirodrigues.focuscounter.navigation.FocusCounterNavRoutes.FocusCounterScreen
import com.eslirodrigues.focuscounter.navigation.FocusCounterNavRoutes.StatisticsScreen
import com.eslirodrigues.focuscounter.counter.FocusCounterScreen
import com.eslirodrigues.focuscounter.statistics.StatisticsScreen

@Composable
fun FocusCounterNavGraph(
    navController: NavHostController,
    onMenuClick: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = FocusCounterScreen
    ) {
        composable<FocusCounterScreen> {
            FocusCounterScreen(onMenuClick = onMenuClick)
        }
        composable<StatisticsScreen> {
            StatisticsScreen(onMenuClick = onMenuClick)
        }
    }
}
