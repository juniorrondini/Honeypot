package com.agendaprobeauty.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.agendaprobeauty.app.data.local.entity.StaffMemberEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StaffDao {
    @Query("SELECT * FROM staff_members WHERE isActive = 1 ORDER BY name COLLATE NOCASE")
    fun observeActiveStaff(): Flow<List<StaffMemberEntity>>

    @Query("SELECT * FROM staff_members WHERE id = :id")
    suspend fun getById(id: Long): StaffMemberEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(member: StaffMemberEntity): Long

    @Query("UPDATE staff_members SET isActive = 0 WHERE id = :id")
    suspend fun deactivate(id: Long)
}
