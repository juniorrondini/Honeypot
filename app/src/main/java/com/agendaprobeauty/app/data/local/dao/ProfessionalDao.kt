package com.agendaprobeauty.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.agendaprobeauty.app.data.local.entity.ProfessionalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfessionalDao {
    @Query("SELECT * FROM professionals WHERE id = 1")
    fun observeProfessional(): Flow<ProfessionalEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(professional: ProfessionalEntity)
}
