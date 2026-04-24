package com.agendaprobeauty.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.agendaprobeauty.app.data.local.entity.AppointmentEntity
import com.agendaprobeauty.app.domain.model.AppointmentStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface AppointmentDao {
    @Query("SELECT * FROM appointments WHERE startAt >= :startAt AND startAt < :endAt ORDER BY startAt ASC")
    fun observeBetween(startAt: Long, endAt: Long): Flow<List<AppointmentEntity>>

    @Query("SELECT * FROM appointments WHERE (:staffMemberId IS NULL OR staffMemberId = :staffMemberId) AND startAt >= :startAt AND startAt < :endAt ORDER BY startAt ASC")
    fun observeBetweenForStaff(staffMemberId: Long?, startAt: Long, endAt: Long): Flow<List<AppointmentEntity>>

    @Query("SELECT * FROM appointments WHERE id = :id")
    fun observeById(id: Long): Flow<AppointmentEntity?>

    @Query("SELECT COUNT(*) FROM appointments WHERE startAt >= :startAt AND startAt < :endAt")
    suspend fun countBetween(startAt: Long, endAt: Long): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(appointment: AppointmentEntity): Long

    @Query("UPDATE appointments SET status = :status, updatedAt = :timestamp, canceledAt = :canceledAt WHERE id = :id")
    suspend fun updateStatus(id: Long, status: AppointmentStatus, timestamp: Long, canceledAt: Long?)

    @Query("DELETE FROM appointments WHERE id = :id")
    suspend fun deleteById(id: Long)
}
