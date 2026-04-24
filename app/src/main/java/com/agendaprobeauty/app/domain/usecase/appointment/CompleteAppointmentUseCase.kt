package com.agendaprobeauty.app.domain.usecase.appointment

import com.agendaprobeauty.app.core.util.DateUtils
import com.agendaprobeauty.app.domain.model.FinancialEntry
import com.agendaprobeauty.app.domain.model.FinancialType
import com.agendaprobeauty.app.domain.model.AppointmentStatus
import com.agendaprobeauty.app.domain.repository.AppointmentRepository
import com.agendaprobeauty.app.domain.repository.FinanceRepository
import kotlinx.coroutines.flow.first

class CompleteAppointmentUseCase(
    private val appointmentRepository: AppointmentRepository,
    private val financeRepository: FinanceRepository,
) {
    suspend operator fun invoke(id: Long) {
        val appointment = appointmentRepository.observeAppointment(id).first() ?: return
        val now = DateUtils.now()
        appointmentRepository.updateStatus(id, AppointmentStatus.COMPLETED, now)
        financeRepository.saveEntry(
            FinancialEntry(
                appointmentId = id,
                type = FinancialType.INCOME,
                description = appointment.serviceNameSnapshot,
                amountCents = appointment.priceCents,
                occurredAt = now,
                createdAt = now,
            ),
        )
    }
}
