package com.agendaprobeauty.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.agendaprobeauty.app.data.local.entity.MonthlyUsageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MonthlyUsageDao {
    @Query("SELECT * FROM monthly_usage WHERE monthKey = :monthKey")
    fun observeByMonth(monthKey: String): Flow<MonthlyUsageEntity?>

    @Query("SELECT * FROM monthly_usage WHERE monthKey = :monthKey")
    suspend fun getByMonth(monthKey: String): MonthlyUsageEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(usage: MonthlyUsageEntity)
}
