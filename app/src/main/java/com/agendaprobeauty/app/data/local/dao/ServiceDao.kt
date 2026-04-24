package com.agendaprobeauty.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.agendaprobeauty.app.data.local.entity.ServiceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ServiceDao {
    @Query("SELECT * FROM services WHERE isActive = 1 ORDER BY name COLLATE NOCASE")
    fun observeActiveServices(): Flow<List<ServiceEntity>>

    @Query("SELECT * FROM services ORDER BY isActive DESC, name COLLATE NOCASE")
    fun observeAllServices(): Flow<List<ServiceEntity>>

    @Query("SELECT * FROM services WHERE id = :id")
    suspend fun getById(id: Long): ServiceEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(service: ServiceEntity): Long

    @Query("UPDATE services SET isActive = 0 WHERE id = :id")
    suspend fun deactivate(id: Long)
}
