package com.agendaprobeauty.app.domain.usecase.subscription

import com.agendaprobeauty.app.domain.model.PlanType
import com.agendaprobeauty.app.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.first

class CanUsePremiumFeatureUseCase(
    private val repository: SettingsRepository,
) {
    suspend operator fun invoke(): Boolean = repository.planType.first() == PlanType.PREMIUM
}
