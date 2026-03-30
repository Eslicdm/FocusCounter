package com.eslirodrigues.focuscounter.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
@Entity(tableName = "focus_sessions")
data class FocusSessionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: String,
    val startTime: Instant,
    val endTime: Instant,
    val totalClicks: Int,
    val intervals: List<Long>,
    val focusScore: Double
)
