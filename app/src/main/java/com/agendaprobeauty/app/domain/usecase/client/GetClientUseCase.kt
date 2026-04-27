package com.agendaprobeauty.app.domain.usecase.client

import com.agendaprobeauty.app.domain.repository.ClientRepository

class GetClientUseCase(
    private val repository: ClientRepository,
) {
    operator fun invoke(id: Long) = repository.observeClient(id)
}
