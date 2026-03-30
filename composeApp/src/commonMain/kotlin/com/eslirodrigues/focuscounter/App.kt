package com.eslirodrigues.focuscounter

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.eslirodrigues.focuscounter.navigation.FocusCounterNavGraph
import com.eslirodrigues.focuscounter.navigation.FocusCounterNavRoutes.*
import com.eslirodrigues.focuscounter.theme.AppTheme
import com.eslirodrigues.focuscounter.counter.FocusCounterAction
import com.eslirodrigues.focuscounter.counter.FocusCounterViewModel
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App() {
    val viewModel: FocusCounterViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()
    
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    AppTheme {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    Spacer(Modifier.height(12.dp))
                    NavigationDrawerItem(
                        icon = { Icon(Icons.Default.Timer, contentDescription = null) },
                        label = { Text("Focus Counter") },
                        selected = currentDestination?.route == FocusCounterScreen::class.qualifiedName,
                        onClick = {
                            navController.navigate(FocusCounterScreen) {
                                popUpTo(FocusCounterScreen) { inclusive = true }
                            }
                            scope.launch { drawerState.close() }
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                    NavigationDrawerItem(
                        icon = { Icon(Icons.Default.Analytics, contentDescription = null) },
                        label = { Text("Statistics") },
                        selected = currentDestination?.route == StatisticsScreen::class.qualifiedName,
                        onClick = {
                            navController.navigate(StatisticsScreen) {
                                popUpTo(FocusCounterScreen)
                            }
                            scope.launch { drawerState.close() }
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                    NavigationDrawerItem(
                        icon = { Icon(Icons.Default.Settings, contentDescription = null) },
                        label = { Text("Configuration") },
                        selected = currentDestination?.route == ConfigurationScreen::class.qualifiedName,
                        onClick = {
                            navController.navigate(ConfigurationScreen) {
                                popUpTo(FocusCounterScreen)
                            }
                            scope.launch { drawerState.close() }
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        ) {
            FocusCounterNavGraph(
                navController = navController,
                onMenuClick = { scope.launch { drawerState.open() } },
                isSoundEnabled = state.isSoundEnabled,
                onSoundToggled = { viewModel.onAction(FocusCounterAction.ToggleSound(it)) },
                isCountVisible = state.isCountVisible,
                onCountVisibilityToggled = { viewModel.onAction(FocusCounterAction.ToggleVisibility(it)) },
                isRandomColorEnabled = state.isRandomColorEnabled,
                onRandomColorToggled = { viewModel.onAction(FocusCounterAction.ToggleRandomColor(it)) },
                count = state.count,
                onIncrementCount = { viewModel.onAction(FocusCounterAction.IncrementCount) },
                onResetCount = { viewModel.onAction(FocusCounterAction.ResetCount) },
                onSaveSession = { viewModel.onAction(FocusCounterAction.SaveSession) }
            )
        }
    }
}
