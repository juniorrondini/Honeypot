package com.agendaprobeauty.app.domain.usecase.profile

import com.agendaprobeauty.app.domain.repository.ProfessionalRepository

class GetProfessionalProfileUseCase(
    private val repository: ProfessionalRepository,
) {
    operator fun invoke() = repository.observeProfessional()
}
