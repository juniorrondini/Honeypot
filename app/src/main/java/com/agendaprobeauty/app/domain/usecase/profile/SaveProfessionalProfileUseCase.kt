package com.agendaprobeauty.app.domain.usecase.profile

import com.agendaprobeauty.app.core.util.DateUtils
import com.agendaprobeauty.app.domain.model.Professional
import com.agendaprobeauty.app.domain.repository.ProfessionalRepository
import com.agendaprobeauty.app.domain.repository.SettingsRepository

class SaveProfessionalProfileUseCase(
    private val professionalRepository: ProfessionalRepository,
    private val settingsRepository: SettingsRepository,
) {
    suspend operator fun invoke(name: String, businessName: String?, phone: String?, profession: String) {
        professionalRepository.saveProfessional(
            Professional(
                name = name.trim(),
                businessName = businessName?.trim()?.takeIf { it.isNotBlank() },
                phone = phone?.trim()?.takeIf { it.isNotBlank() },
                profession = profession.trim(),
                createdAt = DateUtils.now(),
            ),
        )
        settingsRepository.setOnboardingCompleted(true)
    }
}
