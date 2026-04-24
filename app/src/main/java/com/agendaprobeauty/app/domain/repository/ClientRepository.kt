package com.agendaprobeauty.app.domain.repository

import com.agendaprobeauty.app.domain.model.Client
import kotlinx.coroutines.flow.Flow

interface ClientRepository {
    fun observeClients(query: String = ""): Flow<List<Client>>
    fun observeClient(id: Long): Flow<Client?>
    suspend fun saveClient(client: Client): Long
    suspend fun deleteClient(id: Long)
}
