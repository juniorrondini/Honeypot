package com.agendaprobeauty.app.data.repository

import com.agendaprobeauty.app.data.local.dao.ProfessionalDao
import com.agendaprobeauty.app.data.mapper.toDomain
import com.agendaprobeauty.app.data.mapper.toEntity
import com.agendaprobeauty.app.domain.model.Professional
import com.agendaprobeauty.app.domain.repository.ProfessionalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProfessionalRepositoryImpl(
    private val dao: ProfessionalDao,
) : ProfessionalRepository {
    override fun observeProfessional(): Flow<Professional?> = dao.observeProfessional().map { it?.toDomain() }

    override suspend fun saveProfessional(professional: Professional) {
        dao.upsert(professional.toEntity())
    }
}
