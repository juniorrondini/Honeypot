package com.agendaprobeauty.app.feature.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agendaprobeauty.app.core.util.DateUtils
import com.agendaprobeauty.app.domain.model.Appointment
import com.agendaprobeauty.app.domain.model.AppointmentStatus
import com.agendaprobeauty.app.domain.model.BeautyService
import com.agendaprobeauty.app.domain.model.Client
import com.agendaprobeauty.app.domain.model.PlanStatus
import com.agendaprobeauty.app.domain.model.PlanType
import com.agendaprobeauty.app.domain.model.StaffMember
import com.agendaprobeauty.app.domain.model.UserMode
import com.agendaprobeauty.app.domain.repository.SettingsRepository
import com.agendaprobeauty.app.domain.usecase.appointment.GetDailyAppointmentsUseCase
import com.agendaprobeauty.app.domain.usecase.appointment.GetMonthlyAppointmentsUseCase
import com.agendaprobeauty.app.domain.usecase.client.SearchClientsUseCase
import com.agendaprobeauty.app.domain.usecase.finance.GetFinanceSummaryUseCase
import com.agendaprobeauty.app.domain.usecase.service.GetAllServicesUseCase
import com.agendaprobeauty.app.domain.usecase.staff.GetActiveStaffUseCase
import com.agendaprobeauty.app.domain.usecase.subscription.GetCurrentPlanUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

data class DashboardUiState(
    val todayAppointments: List<Appointment> = emptyList(),
    val monthAppointments: List<Appointment> = emptyList(),
    val clients: List<Client> = emptyList(),
    val staff: List<StaffMember> = emptyList(),
    val services: List<BeautyService> = emptyList(),
    val revenueTodayCents: Long = 0L,
    val revenueMonthCents: Long = 0L,
    val planStatus: PlanStatus = PlanStatus(PlanType.FREE, currentMonthAppointments = 0),
    val userMode: UserMode = UserMode.ADMIN,
) {
    val isAdmin: Boolean = userMode == UserMode.ADMIN
    val scheduledToday: Int = todayAppointments.count { it.status == AppointmentStatus.SCHEDULED }
    val completedToday: Int = todayAppointments.count { it.status == AppointmentStatus.COMPLETED }
    val nextAppointments: List<Appointment> = todayAppointments
        .filter { it.status == AppointmentStatus.SCHEDULED && it.startAt >= DateUtils.now() }
        .sortedBy { it.startAt }
        .take(5)
    val freeLimitWarning: Boolean = planStatus.type == PlanType.FREE &&
        planStatus.currentMonthAppointments >= planStatus.freeMonthlyLimit - 5
}

class DashboardViewModel(
    getDailyAppointments: GetDailyAppointmentsUseCase,
    getMonthlyAppointments: GetMonthlyAppointmentsUseCase,
    getCurrentPlan: GetCurrentPlanUseCase,
    getFinanceSummary: GetFinanceSummaryUseCase,
    searchClients: SearchClientsUseCase,
    getActiveStaff: GetActiveStaffUseCase,
    getAllServices: GetAllServicesUseCase,
    settingsRepository: SettingsRepository,
) : ViewModel() {
    private data class AppointmentPlanState(
        val today: List<Appointment>,
        val monthAppointments: List<Appointment>,
        val plan: PlanStatus,
    )

    private val appointmentPlanState = combine(
        getDailyAppointments(DateUtils.today()),
        getMonthlyAppointments(),
        getCurrentPlan(),
    ) { today, monthAppointments, plan ->
        AppointmentPlanState(today, monthAppointments, plan)
    }

    val state: StateFlow<DashboardUiState> = combine(
        appointmentPlanState,
        searchClients(""),
        getActiveStaff(),
        getAllServices(),
        settingsRepository.userMode,
    ) { appointmentPlan, clients, staff, services, userMode ->
        val dayFinance = getFinanceSummary.forDay(DateUtils.today())
        val monthFinance = getFinanceSummary.forCurrentMonth()
        DashboardUiState(
            todayAppointments = appointmentPlan.today,
            monthAppointments = appointmentPlan.monthAppointments,
            clients = clients,
            staff = staff,
            services = services,
            revenueTodayCents = dayFinance.incomeCents,
            revenueMonthCents = monthFinance.incomeCents,
            planStatus = appointmentPlan.plan,
            userMode = userMode,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), DashboardUiState())
}
