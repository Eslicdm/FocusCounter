package com.eslirodrigues.focuscounter.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.eslirodrigues.focuscounter.navigation.FocusCounterNavRoutes.*
import com.eslirodrigues.focuscounter.counter.FocusCounterScreen
import com.eslirodrigues.focuscounter.counter.TodayStats
import com.eslirodrigues.focuscounter.history.HistoryScreen
import com.eslirodrigues.focuscounter.configuration.ConfigurationScreen

@Composable
fun FocusCounterNavGraph(
    navController: NavHostController,
    onMenuClick: () -> Unit,
    onHistoryClick: () -> Unit,
    isSoundEnabled: Boolean,
    onSoundToggled: (Boolean) -> Unit,
    isCountVisible: Boolean,
    onCountVisibilityToggled: (Boolean) -> Unit,
    isRandomColorEnabled: Boolean,
    onRandomColorToggled: (Boolean) -> Unit,
    count: Int,
    todayStats: TodayStats,
    onIncrementCount: () -> Unit,
    onResetCount: () -> Unit,
    onSaveSession: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = FocusCounterScreen
    ) {
        composable<FocusCounterScreen> {
            FocusCounterScreen(
                onMenuClick = onMenuClick,
                onHistoryClick = onHistoryClick,
                isSoundEnabled = isSoundEnabled,
                isCountVisible = isCountVisible,
                onCountVisibilityToggled = onCountVisibilityToggled,
                isRandomColorEnabled = isRandomColorEnabled,
                onRandomColorToggled = onRandomColorToggled,
                count = count,
                todayStats = todayStats,
                onIncrementCount = onIncrementCount,
                onResetCount = onResetCount,
                onSaveSession = onSaveSession
            )
        }
        composable<HistoryScreen> {
            HistoryScreen(onMenuClick = onMenuClick)
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
