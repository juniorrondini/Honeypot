package com.agendaprobeauty.app.domain.usecase.appointment

import com.agendaprobeauty.app.core.util.DateUtils
import com.agendaprobeauty.app.domain.model.Appointment
import com.agendaprobeauty.app.domain.model.AppointmentStatus
import com.agendaprobeauty.app.domain.repository.AppointmentRepository
import com.agendaprobeauty.app.domain.repository.SubscriptionRepository

class CreateAppointmentUseCase(
    private val appointmentRepository: AppointmentRepository,
    private val subscriptionRepository: SubscriptionRepository,
) {
    suspend operator fun invoke(
        clientId: Long?,
        serviceId: Long?,
        clientName: String,
        serviceName: String,
        priceCents: Long,
        startAt: Long,
        durationMinutes: Int,
        notes: String?,
    ): Result<Long> {
        if (!subscriptionRepository.canCreateAppointmentThisMonth()) {
            return Result.failure(IllegalStateException("Limite gratuito mensal atingido."))
        }
        val now = DateUtils.now()
        val endAt = startAt + durationMinutes * 60_000L
        val id = appointmentRepository.saveAppointment(
            Appointment(
                clientId = clientId,
                serviceId = serviceId,
                clientNameSnapshot = clientName.trim(),
                serviceNameSnapshot = serviceName.trim(),
                priceCents = priceCents,
                startAt = startAt,
                endAt = endAt,
                status = AppointmentStatus.SCHEDULED,
                notes = notes?.trim()?.takeIf { it.isNotBlank() },
                createdAt = now,
                updatedAt = now,
                canceledAt = null,
            ),
        )
        return Result.success(id)
    }
}
