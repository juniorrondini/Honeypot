package com.agendaprobeauty.app.domain.usecase.appointment

import com.agendaprobeauty.app.core.util.DateUtils
import com.agendaprobeauty.app.domain.repository.AppointmentRepository

class GetMonthlyAppointmentsUseCase(
    private val repository: AppointmentRepository,
) {
    operator fun invoke() = repository.observeAppointmentsBetween(
        DateUtils.startOfCurrentMonth(),
        DateUtils.startOfNextMonth(),
    )
}
