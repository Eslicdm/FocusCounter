package com.eslirodrigues.focuscounter.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eslirodrigues.focuscounter.database.FocusSessionEntity
import com.eslirodrigues.focuscounter.database.FocusSessionRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class HistoryViewModel(
    private val repository: FocusSessionRepository
) : ViewModel() {
    val sessions: StateFlow<List<FocusSessionEntity>> = repository.getAllSessions()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}
