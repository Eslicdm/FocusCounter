package com.eslirodrigues.focuscounter.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.eslirodrigues.focuscounter.navigation.FocusCounterNavRoutes.*
import com.eslirodrigues.focuscounter.counter.FocusCounterScreen
import com.eslirodrigues.focuscounter.statistics.StatisticsScreen
import com.eslirodrigues.focuscounter.configuration.ConfigurationScreen

@Composable
fun FocusCounterNavGraph(
    navController: NavHostController,
    onMenuClick: () -> Unit,
    isSoundEnabled: Boolean,
    onSoundToggled: (Boolean) -> Unit,
    isCountVisible: Boolean,
    onCountVisibilityToggled: (Boolean) -> Unit,
    isRandomColorEnabled: Boolean,
    onRandomColorToggled: (Boolean) -> Unit,
    count: Int,
    onIncrementCount: () -> Unit,
    onResetCount: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = FocusCounterScreen
    ) {
        composable<FocusCounterScreen> {
            FocusCounterScreen(
                onMenuClick = onMenuClick,
                isSoundEnabled = isSoundEnabled,
                isCountVisible = isCountVisible,
                onCountVisibilityToggled = onCountVisibilityToggled,
                isRandomColorEnabled = isRandomColorEnabled,
                onRandomColorToggled = onRandomColorToggled,
                count = count,
                onIncrementCount = onIncrementCount,
                onResetCount = onResetCount
            )
        }
        composable<StatisticsScreen> {
            StatisticsScreen(onMenuClick = onMenuClick)
        }
        composable<ConfigurationScreen> {
            ConfigurationScreen(
                onMenuClick = onMenuClick,
                isSoundEnabled = isSoundEnabled,
                onSoundToggled = onSoundToggled
            )
        }
    }
}
