package com.agendaprobeauty.app.feature.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agendaprobeauty.app.core.util.DateUtils
import com.agendaprobeauty.app.domain.model.Appointment
import com.agendaprobeauty.app.domain.model.PlanStatus
import com.agendaprobeauty.app.domain.model.PlanType
import com.agendaprobeauty.app.domain.usecase.appointment.GetDailyAppointmentsUseCase
import com.agendaprobeauty.app.domain.usecase.finance.GetFinanceSummaryUseCase
import com.agendaprobeauty.app.domain.usecase.subscription.GetCurrentPlanUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class DashboardUiState(
    val todayAppointments: List<Appointment> = emptyList(),
    val revenueTodayCents: Long = 0L,
    val planStatus: PlanStatus = PlanStatus(PlanType.FREE, currentMonthAppointments = 0),
)

class DashboardViewModel(
    getDailyAppointments: GetDailyAppointmentsUseCase,
    getCurrentPlan: GetCurrentPlanUseCase,
    private val getFinanceSummary: GetFinanceSummaryUseCase,
) : ViewModel() {
    private val revenueToday = MutableStateFlow(0L)

    val state: StateFlow<DashboardUiState> = combine(
        getDailyAppointments(DateUtils.today()),
        getCurrentPlan(),
        revenueToday,
    ) { appointments, plan, revenue ->
        DashboardUiState(appointments, revenue, plan)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), DashboardUiState())

    init {
        viewModelScope.launch {
            revenueToday.value = getFinanceSummary.forDay(DateUtils.today()).incomeCents
        }
    }
}
