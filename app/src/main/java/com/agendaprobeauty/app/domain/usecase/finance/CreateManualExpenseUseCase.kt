package com.agendaprobeauty.app.domain.usecase.finance

import com.agendaprobeauty.app.core.util.DateUtils
import com.agendaprobeauty.app.domain.model.FinancialEntry
import com.agendaprobeauty.app.domain.model.FinancialType
import com.agendaprobeauty.app.domain.repository.FinanceRepository

class CreateManualExpenseUseCase(
    private val repository: FinanceRepository,
) {
    suspend operator fun invoke(description: String, amountCents: Long): Long {
        val now = DateUtils.now()
        return repository.saveEntry(
            FinancialEntry(
                appointmentId = null,
                type = FinancialType.EXPENSE,
                description = description.trim(),
                amountCents = amountCents,
                occurredAt = now,
                createdAt = now,
            ),
        )
    }
}
