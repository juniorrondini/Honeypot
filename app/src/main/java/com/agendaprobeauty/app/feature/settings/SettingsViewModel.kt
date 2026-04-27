package com.agendaprobeauty.app.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agendaprobeauty.app.domain.model.Professional
import com.agendaprobeauty.app.domain.model.UserMode
import com.agendaprobeauty.app.domain.repository.SettingsRepository
import com.agendaprobeauty.app.domain.usecase.profile.GetProfessionalProfileUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class SettingsUiState(
    val professional: Professional? = null,
    val onboardingCompleted: Boolean = false,
    val userMode: UserMode = UserMode.ADMIN,
)

class SettingsViewModel(
    getProfessionalProfile: GetProfessionalProfileUseCase,
    private val settingsRepository: SettingsRepository,
) : ViewModel() {
    val state: StateFlow<SettingsUiState> = combine(
        getProfessionalProfile(),
        settingsRepository.isOnboardingCompleted,
        settingsRepository.userMode,
    ) { professional, completed, userMode ->
        SettingsUiState(professional, completed, userMode)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), SettingsUiState())

    fun setUserMode(userMode: UserMode) {
        viewModelScope.launch { settingsRepository.setUserMode(userMode) }
    }
}
