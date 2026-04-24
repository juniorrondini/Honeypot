package com.agendaprobeauty.app.domain.usecase.client

import com.agendaprobeauty.app.core.util.DateUtils
import com.agendaprobeauty.app.domain.model.Client
import com.agendaprobeauty.app.domain.repository.ClientRepository

class CreateClientUseCase(
    private val repository: ClientRepository,
) {
    suspend operator fun invoke(name: String, phone: String?, notes: String?): Long {
        val now = DateUtils.now()
        return repository.saveClient(
            Client(
                name = name.trim(),
                phone = phone?.trim()?.takeIf { it.isNotBlank() },
                notes = notes?.trim()?.takeIf { it.isNotBlank() },
                createdAt = now,
                updatedAt = now,
            ),
        )
    }
}
