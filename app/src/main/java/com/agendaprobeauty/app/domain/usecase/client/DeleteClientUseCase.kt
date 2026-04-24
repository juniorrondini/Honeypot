package com.agendaprobeauty.app.domain.usecase.client

import com.agendaprobeauty.app.domain.repository.ClientRepository

class DeleteClientUseCase(
    private val repository: ClientRepository,
) {
    suspend operator fun invoke(id: Long) = repository.deleteClient(id)
}
