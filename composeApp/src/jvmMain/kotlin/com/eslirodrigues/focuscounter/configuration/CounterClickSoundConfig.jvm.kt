package com.eslirodrigues.focuscounter.configuration

import java.awt.Toolkit

actual fun playCounterSound() {
    Toolkit.getDefaultToolkit().beep()
}
