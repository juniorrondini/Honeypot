package com.agendaprobeauty.app.data.repository

import com.agendaprobeauty.app.data.local.dao.FinancialDao
import com.agendaprobeauty.app.data.mapper.toDomain
import com.agendaprobeauty.app.data.mapper.toEntity
import com.agendaprobeauty.app.domain.model.FinancialEntry
import com.agendaprobeauty.app.domain.model.FinancialType
import com.agendaprobeauty.app.domain.repository.FinanceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FinanceRepositoryImpl(
    private val dao: FinancialDao,
) : FinanceRepository {
    override fun observeEntriesBetween(startAt: Long, endAt: Long): Flow<List<FinancialEntry>> =
        dao.observeBetween(startAt, endAt).map { entries -> entries.map { it.toDomain() } }

    override suspend fun saveEntry(entry: FinancialEntry): Long = dao.upsert(entry.toEntity())

    override suspend fun totalIncomeBetween(startAt: Long, endAt: Long): Long =
        dao.totalByTypeBetween(FinancialType.INCOME, startAt, endAt)

    override suspend fun totalExpenseBetween(startAt: Long, endAt: Long): Long =
        dao.totalByTypeBetween(FinancialType.EXPENSE, startAt, endAt)
}
