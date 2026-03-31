package com.eslirodrigues.focuscounter.navigation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class FocusCounterNavRoutes {
    @Serializable @SerialName("FocusCounter")
    data object FocusCounterScreen : FocusCounterNavRoutes()

    @Serializable @SerialName("History")
    data object HistoryScreen : FocusCounterNavRoutes()

    @Serializable @SerialName("Statistics")
    data object StatisticsScreen : FocusCounterNavRoutes()

    @Serializable @SerialName("Configuration")
    data object ConfigurationScreen : FocusCounterNavRoutes()
}
