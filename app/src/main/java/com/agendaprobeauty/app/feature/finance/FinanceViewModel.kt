package com.agendaprobeauty.app.feature.finance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agendaprobeauty.app.core.util.DateUtils
import com.agendaprobeauty.app.core.util.MoneyUtils
import com.agendaprobeauty.app.domain.model.FinancialEntry
import com.agendaprobeauty.app.domain.model.FinancialType
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

data class FinanceGroup(
    val label: String,
    val amountCents: Long,
)

data class FinanceUiState(
    val entries: List<FinancialEntry> = emptyList(),
    val daySummary: FinanceSummary = FinanceSummary(0L, 0L),
    val monthSummary: FinanceSummary = FinanceSummary(0L, 0L),
    val incomeGroups: List<FinanceGroup> = emptyList(),
    val expenseGroups: List<FinanceGroup> = emptyList(),
    val expenseDescription: String = "",
    val expenseAmount: String = "",
)

class FinanceViewModel(
    getFinanceEntries: GetFinanceEntriesUseCase,
    private val getFinanceSummary: GetFinanceSummaryUseCase,
    private val createManualExpense: CreateManualExpenseUseCase,
) : ViewModel() {
    private val formState = MutableStateFlow(FinanceUiState())

    val state: StateFlow<FinanceUiState> = combine(formState, getFinanceEntries.forCurrentMonth()) { form, entries ->
        val incomeGroups = entries
            .filter { it.type == FinancialType.INCOME }
            .groupBy { it.description }
            .map { (label, values) -> FinanceGroup(label, values.sumOf { it.amountCents }) }
            .sortedByDescending { it.amountCents }
        val expenseGroups = entries
            .filter { it.type == FinancialType.EXPENSE }
            .groupBy { it.description }
            .map { (label, values) -> FinanceGroup(label, values.sumOf { it.amountCents }) }
            .sortedByDescending { it.amountCents }
        form.copy(
            entries = entries,
            incomeGroups = incomeGroups,
            expenseGroups = expenseGroups,
        )
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
            createManualExpense(current.expenseDescription, MoneyUtils.parseToCents(current.expenseAmount))
            update { copy(expenseDescription = "", expenseAmount = "") }
            refreshSummary()
        }
    }

    private fun refreshSummary() {
        viewModelScope.launch {
            val day = getFinanceSummary.forDay(DateUtils.today())
            val month = getFinanceSummary.forCurrentMonth()
            update { copy(daySummary = day, monthSummary = month) }
        }
    }

    private fun update(block: FinanceUiState.() -> FinanceUiState) {
        formState.value = formState.value.block()
    }
}
