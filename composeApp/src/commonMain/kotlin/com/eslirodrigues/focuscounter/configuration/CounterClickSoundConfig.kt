package com.eslirodrigues.focuscounter.configuration

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun CounterClickSoundConfig(
    isSoundEnabled: Boolean,
    onSoundToggled: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Enable Count Click Sound",
            style = MaterialTheme.typography.bodyLarge
        )
        Switch(
            checked = isSoundEnabled,
            onCheckedChange = onSoundToggled
        )
    }
}

expect fun playCounterSound()
