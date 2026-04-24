package com.agendaprobeauty.app.domain.usecase.finance

import com.agendaprobeauty.app.core.util.DateUtils
import com.agendaprobeauty.app.domain.repository.FinanceRepository
import java.time.LocalDate

class GetFinanceEntriesUseCase(
    private val repository: FinanceRepository,
) {
    operator fun invoke(date: LocalDate) =
        repository.observeEntriesBetween(DateUtils.startOfDay(date), DateUtils.endOfDay(date))
}
