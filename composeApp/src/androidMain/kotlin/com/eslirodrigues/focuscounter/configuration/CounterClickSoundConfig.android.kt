package com.eslirodrigues.focuscounter.configuration

import android.media.AudioManager
import android.media.ToneGenerator

private val toneGenerator = ToneGenerator(AudioManager.STREAM_MUSIC, 100)

actual fun playCounterSound() {
    toneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP, 100)
}
