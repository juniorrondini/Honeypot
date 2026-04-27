package com.agendaprobeauty.app.domain.usecase.appointment

import com.agendaprobeauty.app.core.util.DateUtils
import com.agendaprobeauty.app.domain.model.Appointment
import com.agendaprobeauty.app.domain.repository.AppointmentRepository

class UpdateAppointmentUseCase(
    private val repository: AppointmentRepository,
) {
    suspend operator fun invoke(appointment: Appointment) {
        val overlapping = appointment.staffMemberId?.let { staffMemberId ->
            repository.countOverlappingForStaff(staffMemberId, appointment.startAt, appointment.endAt)
        } ?: 0
        val previous = repository.getAppointment(appointment.id)
        val conflictsWithAnotherAppointment = overlapping > if (previous != null) 1 else 0
        if (conflictsWithAnotherAppointment) {
            throw IllegalStateException("Horario indisponivel para este profissional.")
        }
        repository.saveAppointment(appointment.copy(updatedAt = DateUtils.now()))
    }
}
