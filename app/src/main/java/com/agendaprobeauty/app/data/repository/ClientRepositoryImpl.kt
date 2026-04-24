package com.agendaprobeauty.app.data.repository

import com.agendaprobeauty.app.data.local.dao.ClientDao
import com.agendaprobeauty.app.data.mapper.toDomain
import com.agendaprobeauty.app.data.mapper.toEntity
import com.agendaprobeauty.app.domain.model.Client
import com.agendaprobeauty.app.domain.repository.ClientRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ClientRepositoryImpl(
    private val dao: ClientDao,
) : ClientRepository {
    override fun observeClients(query: String): Flow<List<Client>> =
        dao.observeClients(query).map { clients -> clients.map { it.toDomain() } }

    override fun observeClient(id: Long): Flow<Client?> = dao.observeClient(id).map { it?.toDomain() }

    override suspend fun saveClient(client: Client): Long = dao.upsert(client.toEntity())

    override suspend fun deleteClient(id: Long) = dao.deleteById(id)
}
