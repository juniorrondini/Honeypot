package com.agendaprobeauty.app.domain.usecase.appointment

import com.agendaprobeauty.app.domain.repository.SubscriptionRepository

class CanCreateAppointmentUseCase(
    private val repository: SubscriptionRepository,
) {
    suspend operator fun invoke(): Boolean = repository.canCreateAppointmentThisMonth()
}
