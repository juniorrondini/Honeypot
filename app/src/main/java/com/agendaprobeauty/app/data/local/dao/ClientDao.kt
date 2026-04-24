package com.agendaprobeauty.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.agendaprobeauty.app.data.local.entity.ClientEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ClientDao {
    @Query("SELECT * FROM clients WHERE name LIKE '%' || :query || '%' ORDER BY name COLLATE NOCASE")
    fun observeClients(query: String): Flow<List<ClientEntity>>

    @Query("SELECT * FROM clients WHERE id = :id")
    fun observeClient(id: Long): Flow<ClientEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(client: ClientEntity): Long

    @Query("DELETE FROM clients WHERE id = :id")
    suspend fun deleteById(id: Long)
}
