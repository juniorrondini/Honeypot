package com.agendaprobeauty.app.feature.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agendaprobeauty.app.domain.repository.SettingsRepository
import com.agendaprobeauty.app.domain.usecase.profile.SaveProfessionalProfileUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class OnboardingUiState(
    val name: String = "",
    val businessName: String = "",
    val phone: String = "",
    val profession: String = "",
    val isSaving: Boolean = false,
)

class OnboardingViewModel(
    private val saveProfile: SaveProfessionalProfileUseCase,
    settingsRepository: SettingsRepository,
) : ViewModel() {
    val isCompleted: StateFlow<Boolean> = settingsRepository.isOnboardingCompleted
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), false)

    var state = kotlinx.coroutines.flow.MutableStateFlow(OnboardingUiState())
        private set

    fun updateName(value: String) = update { copy(name = value) }
    fun updateBusinessName(value: String) = update { copy(businessName = value) }
    fun updatePhone(value: String) = update { copy(phone = value) }
    fun updateProfession(value: String) = update { copy(profession = value) }

    fun save(onFinished: () -> Unit) {
        val current = state.value
        if (current.name.isBlank() || current.profession.isBlank()) return
        viewModelScope.launch {
            update { copy(isSaving = true) }
            saveProfile(
                name = current.name,
                businessName = current.businessName,
                phone = current.phone,
                profession = current.profession,
            )
            onFinished()
        }
    }

    private fun update(block: OnboardingUiState.() -> OnboardingUiState) {
        state.value = state.value.block()
    }
}
