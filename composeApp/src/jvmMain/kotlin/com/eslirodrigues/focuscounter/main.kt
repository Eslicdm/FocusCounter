package com.eslirodrigues.focuscounter

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.eslirodrigues.focuscounter.di.initKoin

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "FocusCounter",
        ) {
            App()
        }
    }
}
