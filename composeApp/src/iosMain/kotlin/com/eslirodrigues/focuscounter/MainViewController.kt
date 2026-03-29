package com.eslirodrigues.focuscounter

import androidx.compose.ui.window.ComposeUIViewController
import com.eslirodrigues.focuscounter.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }
