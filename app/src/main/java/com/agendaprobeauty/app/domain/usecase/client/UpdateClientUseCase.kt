package com.agendaprobeauty.app.domain.usecase.client

import com.agendaprobeauty.app.core.util.DateUtils
import com.agendaprobeauty.app.domain.model.Client
import com.agendaprobeauty.app.domain.repository.ClientRepository

class UpdateClientUseCase(
    private val repository: ClientRepository,
) {
    suspend operator fun invoke(client: Client) {
        repository.saveClient(client.copy(updatedAt = DateUtils.now()))
    }
}
