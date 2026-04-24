package com.agendaprobeauty.app.domain.repository

import com.agendaprobeauty.app.domain.model.PlanType
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    val isOnboardingCompleted: Flow<Boolean>
    val planType: Flow<PlanType>
    suspend fun setOnboardingCompleted(completed: Boolean)
    suspend fun setPlanType(planType: PlanType)
}
