package com.agendaprobeauty.app.domain.usecase.profile

import com.agendaprobeauty.app.core.util.DateUtils
import com.agendaprobeauty.app.domain.model.Professional
import com.agendaprobeauty.app.domain.repository.ProfessionalRepository
import com.agendaprobeauty.app.domain.repository.SettingsRepository
import com.agendaprobeauty.app.domain.usecase.staff.CreateStaffMemberUseCase

class SaveProfessionalProfileUseCase(
    private val professionalRepository: ProfessionalRepository,
    private val settingsRepository: SettingsRepository,
    private val createStaffMember: CreateStaffMemberUseCase,
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
        createStaffMember(
            name = name,
            role = profession,
            phone = phone,
            workStartHour = 9,
            workEndHour = 18,
            slotMinutes = 30,
        )
        settingsRepository.setOnboardingCompleted(true)
    }
}
