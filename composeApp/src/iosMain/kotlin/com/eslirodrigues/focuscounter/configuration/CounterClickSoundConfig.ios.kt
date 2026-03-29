package com.eslirodrigues.focuscounter.configuration

import platform.AudioToolbox.AudioServicesPlaySystemSound
import platform.AudioToolbox.kSystemSoundID_Vibrate

actual fun playCounterSound() {
    // kSystemSoundID_UserPreferredAlert or similar could be used for a beep
    AudioServicesPlaySystemSound(1104u) // Standard click/tap sound on iOS
}
