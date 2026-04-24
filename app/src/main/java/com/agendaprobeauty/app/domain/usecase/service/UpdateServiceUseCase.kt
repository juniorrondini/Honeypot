package com.agendaprobeauty.app.domain.usecase.service

import com.agendaprobeauty.app.domain.model.BeautyService
import com.agendaprobeauty.app.domain.repository.ServiceRepository

class UpdateServiceUseCase(
    private val repository: ServiceRepository,
) {
    suspend operator fun invoke(service: BeautyService) {
        repository.saveService(service)
    }
}
