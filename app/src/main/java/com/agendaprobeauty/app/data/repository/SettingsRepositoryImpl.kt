package com.agendaprobeauty.app.data.repository

import com.agendaprobeauty.app.core.datastore.SettingsDataStore
import com.agendaprobeauty.app.domain.model.PlanType
import com.agendaprobeauty.app.domain.model.UserMode
import com.agendaprobeauty.app.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class SettingsRepositoryImpl(
    private val dataStore: SettingsDataStore,
) : SettingsRepository {
    override val isOnboardingCompleted: Flow<Boolean> = dataStore.isOnboardingCompleted
    override val planType: Flow<PlanType> = dataStore.planType
    override val userMode: Flow<UserMode> = dataStore.userMode

    override suspend fun setOnboardingCompleted(completed: Boolean) = dataStore.setOnboardingCompleted(completed)

    override suspend fun setPlanType(planType: PlanType) = dataStore.setPlanType(planType)

    override suspend fun setUserMode(userMode: UserMode) = dataStore.setUserMode(userMode)
}
