package com.agendaprobeauty.app.domain.usecase.appointment

import com.agendaprobeauty.app.core.util.DateUtils
import com.agendaprobeauty.app.domain.model.AppointmentStatus
import com.agendaprobeauty.app.domain.repository.AppointmentRepository

class CancelAppointmentUseCase(
    private val repository: AppointmentRepository,
) {
    suspend operator fun invoke(id: Long) = repository.updateStatus(id, AppointmentStatus.CANCELED, DateUtils.now())
}
