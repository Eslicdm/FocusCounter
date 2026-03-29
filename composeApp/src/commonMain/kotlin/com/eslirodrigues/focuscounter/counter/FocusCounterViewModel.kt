package com.eslirodrigues.focuscounter.counter

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eslirodrigues.focuscounter.datastore.DataStoreProvider
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.random.Random

data class FocusCounterState(
    val count: Int = 0,
    val isSoundEnabled: Boolean = false,
    val isCountVisible: Boolean = true,
    val isRandomColorEnabled: Boolean = false,
)

sealed class FocusCounterAction {
    data object IncrementCount : FocusCounterAction()
    data object ResetCount : FocusCounterAction()
    data class ToggleSound(val enabled: Boolean) : FocusCounterAction()
    data class ToggleVisibility(val visible: Boolean) : FocusCounterAction()
    data class ToggleRandomColor(val enabled: Boolean) : FocusCounterAction()
}

class FocusCounterViewModel(
    private val dataStoreProvider: DataStoreProvider
) : ViewModel() {

    private val _state = MutableStateFlow(FocusCounterState())
    val state: StateFlow<FocusCounterState> = combine(
        _state,
        dataStoreProvider.isSoundEnabled,
        dataStoreProvider.isCountVisible,
        dataStoreProvider.isRandomColorEnabled
    ) { state, sound, visible, randomColor ->
        state.copy(
            isSoundEnabled = sound,
            isCountVisible = visible,
            isRandomColorEnabled = randomColor
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), FocusCounterState())

    fun onAction(action: FocusCounterAction) {
        when (action) {
            FocusCounterAction.IncrementCount -> {
                _state.update { 
                    val newCount = it.count + 1
                    it.copy(count = newCount)
                }
            }
            FocusCounterAction.ResetCount -> {
                _state.update { it.copy(count = 0) }
            }
            is FocusCounterAction.ToggleSound -> {
                viewModelScope.launch {
                    dataStoreProvider.setSoundEnabled(action.enabled)
                }
            }
            is FocusCounterAction.ToggleVisibility -> {
                viewModelScope.launch {
                    dataStoreProvider.setCountVisible(action.visible)
                }
            }
            is FocusCounterAction.ToggleRandomColor -> {
                viewModelScope.launch {
                    dataStoreProvider.setRandomColorEnabled(action.enabled)
                }
            }
        }
    }
}
