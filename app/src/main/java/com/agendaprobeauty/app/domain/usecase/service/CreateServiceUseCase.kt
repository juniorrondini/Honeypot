package com.agendaprobeauty.app.domain.usecase.service

import com.agendaprobeauty.app.core.util.DateUtils
import com.agendaprobeauty.app.domain.model.BeautyService
import com.agendaprobeauty.app.domain.repository.ServiceRepository

class CreateServiceUseCase(
    private val repository: ServiceRepository,
) {
    suspend operator fun invoke(name: String, priceCents: Long, durationMinutes: Int): Long =
        repository.saveService(
            BeautyService(
                name = name.trim(),
                priceCents = priceCents,
                durationMinutes = durationMinutes,
                createdAt = DateUtils.now(),
            ),
        )
}
