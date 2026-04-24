package com.agendaprobeauty.app.domain.usecase.appointment

import com.agendaprobeauty.app.core.util.DateUtils
import com.agendaprobeauty.app.domain.repository.AppointmentRepository
import java.time.LocalDate

class GetDailyAppointmentsUseCase(
    private val repository: AppointmentRepository,
) {
    operator fun invoke(date: LocalDate) =
        repository.observeAppointmentsBetween(DateUtils.startOfDay(date), DateUtils.endOfDay(date))

    fun forStaff(staffMemberId: Long?, date: LocalDate) =
        repository.observeAppointmentsBetweenForStaff(staffMemberId, DateUtils.startOfDay(date), DateUtils.endOfDay(date))
}
