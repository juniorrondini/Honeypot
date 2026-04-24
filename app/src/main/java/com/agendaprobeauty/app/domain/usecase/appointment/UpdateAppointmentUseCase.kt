package com.agendaprobeauty.app.domain.usecase.appointment

import com.agendaprobeauty.app.core.util.DateUtils
import com.agendaprobeauty.app.domain.model.Appointment
import com.agendaprobeauty.app.domain.repository.AppointmentRepository

class UpdateAppointmentUseCase(
    private val repository: AppointmentRepository,
) {
    suspend operator fun invoke(appointment: Appointment) {
        repository.saveAppointment(appointment.copy(updatedAt = DateUtils.now()))
    }
}
