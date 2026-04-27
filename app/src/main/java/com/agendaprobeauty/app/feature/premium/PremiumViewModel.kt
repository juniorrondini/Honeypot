package com.agendaprobeauty.app.feature.premium

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agendaprobeauty.app.domain.model.PlanStatus
import com.agendaprobeauty.app.domain.model.PlanType
import com.agendaprobeauty.app.domain.repository.SettingsRepository
import com.agendaprobeauty.app.domain.usecase.subscription.GetCurrentPlanUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PremiumViewModel(
    getCurrentPlan: GetCurrentPlanUseCase,
    private val settingsRepository: SettingsRepository,
) : ViewModel() {
    val state: StateFlow<PlanStatus> = getCurrentPlan()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = PlanStatus(PlanType.FREE, currentMonthAppointments = 0),
        )

    fun activatePremiumLocally() {
        viewModelScope.launch { settingsRepository.setPlanType(PlanType.PREMIUM) }
    }

    fun returnToFree() {
        viewModelScope.launch { settingsRepository.setPlanType(PlanType.FREE) }
    }
}
