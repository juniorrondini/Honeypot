package com.agendaprobeauty.app.domain.repository

import com.agendaprobeauty.app.domain.model.BeautyService
import kotlinx.coroutines.flow.Flow

interface ServiceRepository {
    fun observeActiveServices(): Flow<List<BeautyService>>
    fun observeAllServices(): Flow<List<BeautyService>>
    suspend fun getService(id: Long): BeautyService?
    suspend fun saveService(service: BeautyService): Long
    suspend fun deactivateService(id: Long)
}
