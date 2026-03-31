package com.eslirodrigues.focuscounter.counter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eslirodrigues.focuscounter.database.FocusSessionEntity
import com.eslirodrigues.focuscounter.database.FocusSessionRepository
import com.eslirodrigues.focuscounter.datastore.DataStoreProvider
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.Instant
import kotlin.math.log10
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.time.Duration

data class FocusCounterState(
    val count: Int = 0,
    val isSoundEnabled: Boolean = false,
    val isCountVisible: Boolean = true,
    val isRandomColorEnabled: Boolean = false,
    val startTime: Instant? = null,
    val intervals: List<Long> = emptyList(),
    val lastClickTime: Instant? = null
)

data class LastSessionStats(
    val duration: Duration = Duration.ZERO,
    val score: Double = 0.0,
    val clicks: Int = 0
)

data class TodayStats(
    val totalTime: Duration = Duration.ZERO,
    val bestScore: Double = 0.0,
    val highestClicks: Int = 0,
    val lastSession: LastSessionStats? = null
)

sealed class FocusCounterAction {
    data object IncrementCount : FocusCounterAction()
    data object ResetCount : FocusCounterAction()
    data class ToggleSound(val enabled: Boolean) : FocusCounterAction()
    data class ToggleVisibility(val visible: Boolean) : FocusCounterAction()
    data class ToggleRandomColor(val enabled: Boolean) : FocusCounterAction()
    data object SaveSession : FocusCounterAction()
}

class FocusCounterViewModel(
    private val dataStoreProvider: DataStoreProvider,
    private val repository: FocusSessionRepository
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

    val todayStats: StateFlow<TodayStats> = repository.getAllSessions()
        .map { sessionList ->
            val now = Clock.System.now()
            val today = now.toLocalDateTime(TimeZone.currentSystemDefault()).date
            val todaySessions = sessionList.filter {
                it.startTime.toLocalDateTime(TimeZone.currentSystemDefault()).date == today
            }

            val lastSessionEntity = sessionList.maxByOrNull { it.startTime }
            val lastSession = lastSessionEntity?.let {
                LastSessionStats(
                    duration = it.endTime - it.startTime,
                    score = it.focusScore,
                    clicks = it.totalClicks
                )
            }

            if (todaySessions.isEmpty()) return@map TodayStats(lastSession = lastSession)

            val totalDuration = todaySessions.fold(Duration.ZERO) { acc, session ->
                acc + (session.endTime - session.startTime)
            }
            val bestScore = todaySessions.maxOfOrNull { it.focusScore } ?: 0.0
            val highestClicks = todaySessions.maxOfOrNull { it.totalClicks } ?: 0

            TodayStats(totalDuration, bestScore, highestClicks, lastSession)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TodayStats())

    fun onAction(action: FocusCounterAction) {
        when (action) {
            FocusCounterAction.IncrementCount -> {
                val now = Clock.System.now()
                _state.update { currentState ->
                    val newStartTime = currentState.startTime ?: now
                    val newIntervals = currentState.lastClickTime?.let { last ->
                        currentState.intervals + (now.toEpochMilliseconds() - last.toEpochMilliseconds())
                    } ?: currentState.intervals

                    currentState.copy(
                        count = currentState.count + 1,
                        startTime = newStartTime,
                        intervals = newIntervals,
                        lastClickTime = now
                    )
                }
            }
            FocusCounterAction.ResetCount -> {
                _state.update { it.copy(count = 0, startTime = null, intervals = emptyList(), lastClickTime = null) }
            }
            FocusCounterAction.SaveSession -> {
                saveFocusSession()
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

    private fun saveFocusSession() {
        val currentState = _state.value
        if (currentState.intervals.isEmpty() || currentState.startTime == null) return

        viewModelScope.launch {
            val endTime = Clock.System.now()
            val focusScore = calculateFocusScore(currentState.intervals)
            
            val session = FocusSessionEntity(
                userId = "1",
                startTime = currentState.startTime,
                endTime = endTime,
                totalClicks = currentState.count,
                intervals = currentState.intervals,
                focusScore = focusScore
            )
            repository.insert(session)
            onAction(FocusCounterAction.ResetCount)
        }
    }

    private fun calculateFocusScore(intervals: List<Long>): Double {
        if (intervals.size < 2) return 0.0

        val totalTimeSec = intervals.sum() / 1000.0
        val meanMs = intervals.average()
        val variance = intervals.map { (it - meanMs).pow(2) }.average()
        val stdDevSec = sqrt(variance) / 1000.0
        val clicks = intervals.size.toDouble()

        val baseScore = totalTimeSec / (stdDevSec + 1.0)
        val finalScore = baseScore * log10(clicks)

        return (finalScore * 100).toInt() / 100.0
    }
}
