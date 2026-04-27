package com.agendaprobeauty.app.domain.repository

import com.agendaprobeauty.app.domain.model.PlanType
import com.agendaprobeauty.app.domain.model.UserMode
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    val isOnboardingCompleted: Flow<Boolean>
    val isDemoDataSeeded: Flow<Boolean>
    val planType: Flow<PlanType>
    val userMode: Flow<UserMode>
    suspend fun setOnboardingCompleted(completed: Boolean)
    suspend fun setDemoDataSeeded(seeded: Boolean)
    suspend fun setPlanType(planType: PlanType)
    suspend fun setUserMode(userMode: UserMode)
}
