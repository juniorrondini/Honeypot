package com.agendaprobeauty.app.feature.agenda

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agendaprobeauty.app.core.util.DateUtils
import com.agendaprobeauty.app.domain.model.Appointment
import com.agendaprobeauty.app.domain.model.AppointmentStatus
import com.agendaprobeauty.app.domain.model.StaffMember
import com.agendaprobeauty.app.domain.usecase.appointment.CancelAppointmentUseCase
import com.agendaprobeauty.app.domain.usecase.appointment.CompleteAppointmentUseCase
import com.agendaprobeauty.app.domain.usecase.appointment.GetDailyAppointmentsUseCase
import com.agendaprobeauty.app.domain.usecase.staff.GetActiveStaffUseCase
import java.time.LocalTime
import java.time.LocalDate
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class AgendaUiState(
    val selectedDate: LocalDate = DateUtils.today(),
    val staff: List<StaffMember> = emptyList(),
    val selectedStaffMemberId: Long? = null,
    val selectedStaff: StaffMember? = null,
    val appointments: List<Appointment> = emptyList(),
    val timeline: List<AgendaTimelineItem> = emptyList(),
    val scheduledCount: Int = 0,
    val completedCount: Int = 0,
    val canceledCount: Int = 0,
)

data class AgendaTimelineItem(
    val time: String,
    val appointment: Appointment?,
)

@OptIn(ExperimentalCoroutinesApi::class)
class AgendaViewModel(
    private val getDailyAppointments: GetDailyAppointmentsUseCase,
    getActiveStaff: GetActiveStaffUseCase,
    private val cancelAppointment: CancelAppointmentUseCase,
    private val completeAppointment: CompleteAppointmentUseCase,
) : ViewModel() {
    private val selectedDate = MutableStateFlow(DateUtils.today())
    private val selectedStaffMemberId = MutableStateFlow<Long?>(null)
    private val activeStaff = getActiveStaff()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val state: StateFlow<AgendaUiState> = combine(selectedDate, selectedStaffMemberId, activeStaff) { date, staffId, staff ->
        val effectiveStaffId = staffId ?: staff.firstOrNull()?.id
        Triple(date, effectiveStaffId, staff)
    }.flatMapLatest { (date, staffId, staff) ->
        getDailyAppointments.forStaff(staffId, date).map { appointments ->
            val selectedStaff = staff.firstOrNull { it.id == staffId }
            AgendaUiState(
                selectedDate = date,
                staff = staff,
                selectedStaffMemberId = staffId,
                selectedStaff = selectedStaff,
                appointments = appointments,
                timeline = buildTimeline(selectedStaff, appointments),
                scheduledCount = appointments.count { it.status == AppointmentStatus.SCHEDULED },
                completedCount = appointments.count { it.status == AppointmentStatus.COMPLETED },
                canceledCount = appointments.count { it.status == AppointmentStatus.CANCELED || it.status == AppointmentStatus.NO_SHOW },
            )
        }
            }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), AgendaUiState())

    fun previousDay() {
        selectedDate.value = selectedDate.value.minusDays(1)
    }

    fun nextDay() {
        selectedDate.value = selectedDate.value.plusDays(1)
    }

    fun selectStaff(id: Long) {
        selectedStaffMemberId.value = id
    }

    fun cancel(id: Long) {
        viewModelScope.launch { cancelAppointment(id) }
    }

    fun complete(id: Long) {
        viewModelScope.launch { completeAppointment(id) }
    }

    private fun buildTimeline(member: StaffMember?, appointments: List<Appointment>): List<AgendaTimelineItem> {
        if (member == null) return emptyList()
        val appointmentsByStart = appointments.associateBy { DateUtils.formatTime(it.startAt) }
        val items = mutableListOf<AgendaTimelineItem>()
        var time = LocalTime.of(member.workStartHour, 0)
        val end = LocalTime.of(member.workEndHour, 0)
        while (time.isBefore(end)) {
            val label = time.toString()
            items += AgendaTimelineItem(
                time = label,
                appointment = appointmentsByStart[label],
            )
            time = time.plusMinutes(member.slotMinutes.toLong())
        }
        return items
    }
}
