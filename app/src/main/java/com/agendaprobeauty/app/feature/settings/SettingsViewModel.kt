package com.agendaprobeauty.app.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agendaprobeauty.app.domain.model.Professional
import com.agendaprobeauty.app.domain.repository.SettingsRepository
import com.agendaprobeauty.app.domain.usecase.profile.GetProfessionalProfileUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

data class SettingsUiState(
    val professional: Professional? = null,
    val onboardingCompleted: Boolean = false,
)

class SettingsViewModel(
    getProfessionalProfile: GetProfessionalProfileUseCase,
    settingsRepository: SettingsRepository,
) : ViewModel() {
    val state: StateFlow<SettingsUiState> = combine(
        getProfessionalProfile(),
        settingsRepository.isOnboardingCompleted,
    ) { professional, completed ->
        SettingsUiState(professional, completed)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), SettingsUiState())
}
