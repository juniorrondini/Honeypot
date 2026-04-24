package com.agendaprobeauty.app.domain.usecase.service

import com.agendaprobeauty.app.domain.repository.ServiceRepository

class GetActiveServicesUseCase(
    private val repository: ServiceRepository,
) {
    operator fun invoke() = repository.observeActiveServices()
}
