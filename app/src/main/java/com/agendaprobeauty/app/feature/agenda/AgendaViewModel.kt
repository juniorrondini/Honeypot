package com.agendaprobeauty.app.feature.agenda

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agendaprobeauty.app.core.util.DateUtils
import com.agendaprobeauty.app.domain.model.Appointment
import com.agendaprobeauty.app.domain.usecase.appointment.CancelAppointmentUseCase
import com.agendaprobeauty.app.domain.usecase.appointment.CompleteAppointmentUseCase
import com.agendaprobeauty.app.domain.usecase.appointment.GetDailyAppointmentsUseCase
import java.time.LocalDate
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class AgendaUiState(
    val selectedDate: LocalDate = DateUtils.today(),
    val appointments: List<Appointment> = emptyList(),
)

@OptIn(ExperimentalCoroutinesApi::class)
class AgendaViewModel(
    private val getDailyAppointments: GetDailyAppointmentsUseCase,
    private val cancelAppointment: CancelAppointmentUseCase,
    private val completeAppointment: CompleteAppointmentUseCase,
) : ViewModel() {
    private val selectedDate = MutableStateFlow(DateUtils.today())

    val state: StateFlow<AgendaUiState> = selectedDate
        .flatMapLatest { date ->
            getDailyAppointments(date).map { appointments ->
                AgendaUiState(date, appointments)
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), AgendaUiState())

    fun previousDay() {
        selectedDate.value = selectedDate.value.minusDays(1)
    }

    fun nextDay() {
        selectedDate.value = selectedDate.value.plusDays(1)
    }

    fun cancel(id: Long) {
        viewModelScope.launch { cancelAppointment(id) }
    }

    fun complete(id: Long) {
        viewModelScope.launch { completeAppointment(id) }
    }
}
