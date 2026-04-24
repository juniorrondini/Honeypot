package com.agendaprobeauty.app.domain.repository

import com.agendaprobeauty.app.domain.model.Professional
import kotlinx.coroutines.flow.Flow

interface ProfessionalRepository {
    fun observeProfessional(): Flow<Professional?>
    suspend fun saveProfessional(professional: Professional)
}
