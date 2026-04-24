package com.agendaprobeauty.app.data.repository

import com.agendaprobeauty.app.core.util.DateUtils
import com.agendaprobeauty.app.domain.model.PlanStatus
import com.agendaprobeauty.app.domain.model.PlanType
import com.agendaprobeauty.app.domain.repository.AppointmentRepository
import com.agendaprobeauty.app.domain.repository.SettingsRepository
import com.agendaprobeauty.app.domain.repository.SubscriptionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first

class SubscriptionRepositoryImpl(
    private val settingsRepository: SettingsRepository,
    private val appointmentRepository: AppointmentRepository,
) : SubscriptionRepository {
    override fun observePlanStatus(): Flow<PlanStatus> =
        combine(
            settingsRepository.planType,
            appointmentRepository.observeAppointmentsBetween(
                DateUtils.startOfCurrentMonth(),
                DateUtils.startOfNextMonth(),
            ),
        ) { planType, appointments ->
            PlanStatus(
                type = planType,
                currentMonthAppointments = appointments.size,
            )
        }

    override suspend fun canCreateAppointmentThisMonth(): Boolean {
        if (settingsRepository.planType.first() == PlanType.PREMIUM) {
            return true
        }
        val count = appointmentRepository.countAppointmentsBetween(
            DateUtils.startOfCurrentMonth(),
            DateUtils.startOfNextMonth(),
        )
        return count < 30
    }
}
