package com.agendaprobeauty.app.domain.usecase.client

import com.agendaprobeauty.app.domain.repository.ClientRepository

class SearchClientsUseCase(
    private val repository: ClientRepository,
) {
    operator fun invoke(query: String = "") = repository.observeClients(query.trim())
}
