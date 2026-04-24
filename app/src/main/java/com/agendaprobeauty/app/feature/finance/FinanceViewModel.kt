package com.agendaprobeauty.app.feature.finance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agendaprobeauty.app.core.util.DateUtils
import com.agendaprobeauty.app.domain.model.FinancialEntry
import com.agendaprobeauty.app.domain.usecase.finance.CreateManualExpenseUseCase
import com.agendaprobeauty.app.domain.usecase.finance.FinanceSummary
import com.agendaprobeauty.app.domain.usecase.finance.GetFinanceEntriesUseCase
import com.agendaprobeauty.app.domain.usecase.finance.GetFinanceSummaryUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class FinanceUiState(
    val entries: List<FinancialEntry> = emptyList(),
    val daySummary: FinanceSummary = FinanceSummary(0L, 0L),
    val monthSummary: FinanceSummary = FinanceSummary(0L, 0L),
    val expenseDescription: String = "",
    val expenseAmount: String = "",
)

class FinanceViewModel(
    getFinanceEntries: GetFinanceEntriesUseCase,
    private val getFinanceSummary: GetFinanceSummaryUseCase,
    private val createManualExpense: CreateManualExpenseUseCase,
) : ViewModel() {
    private val formState = MutableStateFlow(FinanceUiState())

    val state: StateFlow<FinanceUiState> = combine(formState, getFinanceEntries(DateUtils.today())) { form, entries ->
        form.copy(entries = entries)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), FinanceUiState())

    init {
        refreshSummary()
    }

    fun updateExpenseDescription(value: String) = update { copy(expenseDescription = value) }
    fun updateExpenseAmount(value: String) = update { copy(expenseAmount = value) }

    fun addExpense() {
        val current = formState.value
        if (current.expenseDescription.isBlank()) return
        viewModelScope.launch {
            createManualExpense(
                current.expenseDescription,
                com.agendaprobeauty.app.core.util.MoneyUtils.parseToCents(current.expenseAmount),
            )
            update { copy(expenseDescription = "", expenseAmount = "") }
            refreshSummary()
        }
    }

    private fun refreshSummary() {
        viewModelScope.launch {
            val day = getFinanceSummary.forDay(DateUtils.today())
            val month = getFinanceSummary.forCurrentMonth()
            update {
                copy(
                    daySummary = day,
                    monthSummary = month,
                )
            }
        }
    }

    private fun update(block: FinanceUiState.() -> FinanceUiState) {
        formState.value = formState.value.block()
    }
}
