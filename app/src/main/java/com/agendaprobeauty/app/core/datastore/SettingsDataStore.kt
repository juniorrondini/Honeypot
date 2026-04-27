package com.agendaprobeauty.app.core.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.agendaprobeauty.app.domain.model.PlanType
import com.agendaprobeauty.app.domain.model.UserMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.settingsDataStore by preferencesDataStore(name = "settings")

class SettingsDataStore(
    private val context: Context,
) {
    private object Keys {
        val onboardingCompleted = booleanPreferencesKey("onboarding_completed")
        val planType = stringPreferencesKey("plan_type")
        val userMode = stringPreferencesKey("user_mode")
    }

    val isOnboardingCompleted: Flow<Boolean> = context.settingsDataStore.data
        .map { preferences -> preferences[Keys.onboardingCompleted] ?: false }

    val planType: Flow<PlanType> = context.settingsDataStore.data
        .map { preferences ->
            preferences[Keys.planType]?.let(PlanType::valueOf) ?: PlanType.FREE
        }

    val userMode: Flow<UserMode> = context.settingsDataStore.data
        .map { preferences ->
            preferences[Keys.userMode]?.let(UserMode::valueOf) ?: UserMode.ADMIN
        }

    suspend fun setOnboardingCompleted(completed: Boolean) {
        context.settingsDataStore.edit { preferences ->
            preferences[Keys.onboardingCompleted] = completed
        }
    }

    suspend fun setPlanType(planType: PlanType) {
        context.settingsDataStore.edit { preferences ->
            preferences[Keys.planType] = planType.name
        }
    }

    suspend fun setUserMode(userMode: UserMode) {
        context.settingsDataStore.edit { preferences ->
            preferences[Keys.userMode] = userMode.name
        }
    }
}
