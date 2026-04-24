package com.agendaprobeauty.app.domain.repository

import com.agendaprobeauty.app.domain.model.PlanStatus
import kotlinx.coroutines.flow.Flow

interface SubscriptionRepository {
    fun observePlanStatus(): Flow<PlanStatus>
    suspend fun canCreateAppointmentThisMonth(): Boolean
}
