package com.agendaprobeauty.app.feature.appointmentedit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agendaprobeauty.app.core.util.DateUtils
import com.agendaprobeauty.app.core.util.MoneyUtils
import com.agendaprobeauty.app.domain.model.Appointment
import com.agendaprobeauty.app.domain.model.AppointmentStatus
import com.agendaprobeauty.app.domain.usecase.appointment.GetAppointmentUseCase
import com.agendaprobeauty.app.domain.usecase.appointment.UpdateAppointmentUseCase
import java.time.Instant
import java.time.ZoneId
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class AppointmentEditUiState(
    val appointment: Appointment? = null,
    val date: String = "",
    val time: String = "",
    val price: String = "",
    val duration: String = "",
    val notes: String = "",
    val status: AppointmentStatus = AppointmentStatus.SCHEDULED,
    val error: String? = null,
    val isSaving: Boolean = false,
)

class AppointmentEditViewModel(
    private val appointmentId: Long,
    private val getAppointment: GetAppointmentUseCase,
    private val updateAppointment: UpdateAppointmentUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(AppointmentEditUiState())
    val state: StateFlow<AppointmentEditUiState> = _state

    init {
        viewModelScope.launch {
            getAppointment(appointmentId).collect { appointment ->
                if (appointment != null && state.value.appointment?.id != appointment.id) {
                    val start = Instant.ofEpochMilli(appointment.startAt).atZone(ZoneId.systemDefault()).toLocalDateTime()
                    val durationMinutes = ((appointment.endAt - appointment.startAt) / 60_000L).toInt()
                    _state.value = AppointmentEditUiState(
                        appointment = appointment,
                        date = DateUtils.formatInputDate(start.toLocalDate()),
                        time = start.toLocalTime().toString(),
                        price = MoneyUtils.format(appointment.priceCents),
                        duration = durationMinutes.toString(),
                        notes = appointment.notes.orEmpty(),
                        status = appointment.status,
                    )
                }
            }
        }
    }

    fun updateDate(value: String) = update { copy(date = value, error = null) }
    fun updateTime(value: String) = update { copy(time = value, error = null) }
    fun updatePrice(value: String) = update { copy(price = value, error = null) }
    fun updateDuration(value: String) = update { copy(duration = value, error = null) }
    fun updateNotes(value: String) = update { copy(notes = value, error = null) }
    fun updateStatus(value: AppointmentStatus) = update { copy(status = value, error = null) }

    fun save(onSaved: () -> Unit) {
        val current = state.value
        val appointment = current.appointment ?: return
        val duration = current.duration.toIntOrNull()
        if (duration == null || duration <= 0) {
            update { copy(error = "Duracao invalida.") }
            return
        }
        viewModelScope.launch {
            update { copy(isSaving = true, error = null) }
            val result = runCatching {
                DateUtils.millisFor(
                    DateUtils.parseInputDate(current.date),
                    DateUtils.parseInputTime(current.time),
                )
            }.mapCatching { startAt ->
                updateAppointment(
                    appointment.copy(
                        priceCents = MoneyUtils.parseToCents(current.price),
                        startAt = startAt,
                        endAt = startAt + duration * 60_000L,
                        status = current.status,
                        notes = current.notes.trim().takeIf { it.isNotBlank() },
                        canceledAt = if (current.status == AppointmentStatus.CANCELED) DateUtils.now() else appointment.canceledAt,
                    ),
                )
            }
            if (result.isSuccess) {
                onSaved()
            } else {
                update { copy(isSaving = false, error = result.exceptionOrNull()?.message ?: "Nao foi possivel salvar.") }
            }
        }
    }

    private fun update(block: AppointmentEditUiState.() -> AppointmentEditUiState) {
        _state.value = _state.value.block()
    }
}
