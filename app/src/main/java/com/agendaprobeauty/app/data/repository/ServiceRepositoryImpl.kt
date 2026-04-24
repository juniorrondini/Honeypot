package com.agendaprobeauty.app.data.repository

import com.agendaprobeauty.app.data.local.dao.ServiceDao
import com.agendaprobeauty.app.data.mapper.toDomain
import com.agendaprobeauty.app.data.mapper.toEntity
import com.agendaprobeauty.app.domain.model.BeautyService
import com.agendaprobeauty.app.domain.repository.ServiceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ServiceRepositoryImpl(
    private val dao: ServiceDao,
) : ServiceRepository {
    override fun observeActiveServices(): Flow<List<BeautyService>> =
        dao.observeActiveServices().map { services -> services.map { it.toDomain() } }

    override fun observeAllServices(): Flow<List<BeautyService>> =
        dao.observeAllServices().map { services -> services.map { it.toDomain() } }

    override suspend fun getService(id: Long): BeautyService? = dao.getById(id)?.toDomain()

    override suspend fun saveService(service: BeautyService): Long = dao.upsert(service.toEntity())

    override suspend fun deactivateService(id: Long) = dao.deactivate(id)
}
