package com.agendaprobeauty.app.domain.repository

import com.agendaprobeauty.app.domain.model.FinancialEntry
import kotlinx.coroutines.flow.Flow

interface FinanceRepository {
    fun observeEntriesBetween(startAt: Long, endAt: Long): Flow<List<FinancialEntry>>
    suspend fun saveEntry(entry: FinancialEntry): Long
    suspend fun totalIncomeBetween(startAt: Long, endAt: Long): Long
    suspend fun totalExpenseBetween(startAt: Long, endAt: Long): Long
}
