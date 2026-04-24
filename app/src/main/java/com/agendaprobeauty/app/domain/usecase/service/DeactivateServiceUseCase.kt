package com.agendaprobeauty.app.domain.usecase.service

import com.agendaprobeauty.app.domain.repository.ServiceRepository

class DeactivateServiceUseCase(
    private val repository: ServiceRepository,
) {
    suspend operator fun invoke(id: Long) = repository.deactivateService(id)
}
