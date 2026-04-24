package com.agendaprobeauty.app.feature.appointmentform

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agendaprobeauty.app.core.util.DateUtils
import com.agendaprobeauty.app.core.util.MoneyUtils
import com.agendaprobeauty.app.domain.usecase.appointment.CreateAppointmentUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class AppointmentFormUiState(
    val clientName: String = "",
    val serviceName: String = "",
    val price: String = "",
    val duration: String = "30",
    val date: String = DateUtils.formatInputDate(DateUtils.today()),
    val time: String = "09:00",
    val notes: String = "",
    val error: String? = null,
    val isSaving: Boolean = false,
)

class AppointmentFormViewModel(
    private val createAppointment: CreateAppointmentUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(AppointmentFormUiState())
    val state: StateFlow<AppointmentFormUiState> = _state

    fun updateClientName(value: String) = update { copy(clientName = value, error = null) }
    fun updateServiceName(value: String) = update { copy(serviceName = value, error = null) }
    fun updatePrice(value: String) = update { copy(price = value, error = null) }
    fun updateDuration(value: String) = update { copy(duration = value, error = null) }
    fun updateDate(value: String) = update { copy(date = value, error = null) }
    fun updateTime(value: String) = update { copy(time = value, error = null) }
    fun updateNotes(value: String) = update { copy(notes = value, error = null) }

    fun save(onSaved: () -> Unit) {
        val current = state.value
        if (current.clientName.isBlank() || current.serviceName.isBlank()) {
            update { copy(error = "Informe cliente e serviço.") }
            return
        }
        val duration = current.duration.toIntOrNull()
        if (duration == null || duration <= 0) {
            update { copy(error = "Duração inválida.") }
            return
        }
        viewModelScope.launch {
            update { copy(isSaving = true) }
            val result = runCatching {
                DateUtils.millisFor(
                    DateUtils.parseInputDate(current.date),
                    DateUtils.parseInputTime(current.time),
                )
            }.mapCatching { startAt ->
                createAppointment(
                    clientId = null,
                    serviceId = null,
                    clientName = current.clientName,
                    serviceName = current.serviceName,
                    priceCents = MoneyUtils.parseToCents(current.price),
                    startAt = startAt,
                    durationMinutes = duration,
                    notes = current.notes,
                ).getOrThrow()
            }
            if (result.isSuccess) {
                onSaved()
            } else {
                update { copy(isSaving = false, error = result.exceptionOrNull()?.message ?: "Não foi possível salvar.") }
            }
        }
    }

    private fun update(block: AppointmentFormUiState.() -> AppointmentFormUiState) {
        _state.value = _state.value.block()
    }
}
