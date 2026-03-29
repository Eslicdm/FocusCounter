package com.eslirodrigues.focuscounter.counter

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eslirodrigues.focuscounter.configuration.playCounterSound
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FocusCounterScreen(
    onMenuClick: () -> Unit,
    isSoundEnabled: Boolean,
    isCountVisible: Boolean,
    onCountVisibilityToggled: (Boolean) -> Unit,
    isRandomColorEnabled: Boolean,
    onRandomColorToggled: (Boolean) -> Unit,
    count: Int,
    onIncrementCount: () -> Unit,
    onResetCount: () -> Unit
) {
    val haptic = LocalHapticFeedback.current
    val scrollState = rememberScrollState()

    val defaultButtonColor = MaterialTheme.colorScheme.primary
    var currentButtonColor by remember { mutableStateOf(defaultButtonColor) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Focus Counter") },
                navigationIcon = {
                    IconButton(onClick = onMenuClick) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                }
            )
        }
    ) { padding ->
        Surface(
            modifier = Modifier.fillMaxSize().padding(padding),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { onCountVisibilityToggled(!isCountVisible) }) {
                        Icon(
                            imageVector = if (isCountVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = if (isCountVisible) "Hide count" else "Show count"
                        )
                    }
                    IconButton(onClick = { 
                        onRandomColorToggled(!isRandomColorEnabled)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Palette,
                            contentDescription = "Random color toggle",
                            tint = if (isRandomColorEnabled) MaterialTheme.colorScheme.primary else LocalContentColor.current
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        onIncrementCount()
                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                        if (isRandomColorEnabled) {
                            currentButtonColor = Color(
                                red = Random.nextFloat(),
                                green = Random.nextFloat(),
                                blue = Random.nextFloat(),
                                alpha = 1f
                            )
                        }
                        if (isSoundEnabled) { playCounterSound() }
                    },
                    modifier = Modifier.size(300.dp),
                    shape = CircleShape,
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = currentButtonColor)
                ) {
                    if (isCountVisible) {
                        Text(
                            text = count.toString(),
                            style = MaterialTheme.typography.displayLarge.copy(
                                fontSize = 64.sp
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                IconButton(
                    onClick = onResetCount,
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Reset Counter",
                    )
                }
            }
        }
    }
}
