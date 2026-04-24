package com.agendaprobeauty.app.domain.usecase.finance

import com.agendaprobeauty.app.core.util.DateUtils
import com.agendaprobeauty.app.domain.repository.FinanceRepository
import java.time.LocalDate

data class FinanceSummary(
    val incomeCents: Long,
    val expenseCents: Long,
) {
    val balanceCents: Long = incomeCents - expenseCents
}

class GetFinanceSummaryUseCase(
    private val repository: FinanceRepository,
) {
    suspend fun forDay(date: LocalDate): FinanceSummary {
        val start = DateUtils.startOfDay(date)
        val end = DateUtils.endOfDay(date)
        return FinanceSummary(
            incomeCents = repository.totalIncomeBetween(start, end),
            expenseCents = repository.totalExpenseBetween(start, end),
        )
    }

    suspend fun forCurrentMonth(): FinanceSummary {
        val start = DateUtils.startOfCurrentMonth()
        val end = DateUtils.startOfNextMonth()
        return FinanceSummary(
            incomeCents = repository.totalIncomeBetween(start, end),
            expenseCents = repository.totalExpenseBetween(start, end),
        )
    }
}
