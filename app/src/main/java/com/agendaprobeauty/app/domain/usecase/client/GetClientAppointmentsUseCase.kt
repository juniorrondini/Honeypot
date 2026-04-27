package com.agendaprobeauty.app.domain.usecase.client

import com.agendaprobeauty.app.domain.repository.AppointmentRepository

class GetClientAppointmentsUseCase(
    private val repository: AppointmentRepository,
) {
    operator fun invoke(clientId: Long) = repository.observeAppointmentsForClient(clientId)
}
