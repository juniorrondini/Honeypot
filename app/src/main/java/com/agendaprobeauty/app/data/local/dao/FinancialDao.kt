package com.agendaprobeauty.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.agendaprobeauty.app.data.local.entity.FinancialEntryEntity
import com.agendaprobeauty.app.domain.model.FinancialType
import kotlinx.coroutines.flow.Flow

@Dao
interface FinancialDao {
    @Query("SELECT * FROM financial_entries WHERE occurredAt >= :startAt AND occurredAt < :endAt ORDER BY occurredAt DESC")
    fun observeBetween(startAt: Long, endAt: Long): Flow<List<FinancialEntryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entry: FinancialEntryEntity): Long

    @Query("SELECT COALESCE(SUM(amountCents), 0) FROM financial_entries WHERE type = :type AND occurredAt >= :startAt AND occurredAt < :endAt")
    suspend fun totalByTypeBetween(type: FinancialType, startAt: Long, endAt: Long): Long
}
