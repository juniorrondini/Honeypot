package com.agendaprobeauty.app.domain.usecase.appointment

import com.agendaprobeauty.app.domain.repository.AppointmentRepository

class GetAppointmentUseCase(
    private val repository: AppointmentRepository,
) {
    operator fun invoke(id: Long) = repository.observeAppointment(id)
}
