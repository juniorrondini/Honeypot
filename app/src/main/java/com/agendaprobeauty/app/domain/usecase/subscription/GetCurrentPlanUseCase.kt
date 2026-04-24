package com.agendaprobeauty.app.domain.usecase.subscription

import com.agendaprobeauty.app.domain.repository.SubscriptionRepository

class GetCurrentPlanUseCase(
    private val repository: SubscriptionRepository,
) {
    operator fun invoke() = repository.observePlanStatus()
}
