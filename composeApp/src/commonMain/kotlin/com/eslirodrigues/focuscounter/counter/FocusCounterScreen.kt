package com.eslirodrigues.focuscounter.counter

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FocusCounterScreen(onMenuClick: () -> Unit) {
    var count by rememberSaveable { mutableStateOf(0) }
    var isCountVisible by rememberSaveable { mutableStateOf(true) }
    var isRandomColorEnabled by rememberSaveable { mutableStateOf(false) }
    
    val defaultButtonColor = MaterialTheme.colorScheme.primary
    var currentButtonColor by remember { mutableStateOf(defaultButtonColor) }
    
    val haptic = LocalHapticFeedback.current

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
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { isCountVisible = !isCountVisible }) {
                        Icon(
                            imageVector = if (isCountVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = if (isCountVisible) "Hide count" else "Show count"
                        )
                    }
                    IconButton(onClick = { 
                        isRandomColorEnabled = !isRandomColorEnabled 
                        if (!isRandomColorEnabled) currentButtonColor = defaultButtonColor
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
                        count++
                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                        if (isRandomColorEnabled) {
                            currentButtonColor = Color(
                                red = Random.nextFloat(),
                                green = Random.nextFloat(),
                                blue = Random.nextFloat(),
                                alpha = 1f
                            )
                        }
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
                    onClick = { count = 0 },
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
