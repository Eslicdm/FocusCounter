package com.eslirodrigues.focuscounter.database

import kotlinx.coroutines.flow.Flow

class FocusSessionRepository(private val dao: FocusSessionDao) {
    suspend fun insert(session: FocusSessionEntity): Long = dao.insert(session)

    fun getUserSessions(userId: String): Flow<List<FocusSessionEntity>> = dao.getUserSessions(userId)

    fun getAllSessions(): Flow<List<FocusSessionEntity>> = dao.getAllSessions()
}
