package com.agendaprobeauty.app.feature.clientdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agendaprobeauty.app.domain.model.Appointment
import com.agendaprobeauty.app.domain.model.AppointmentStatus
import com.agendaprobeauty.app.domain.model.Client
import com.agendaprobeauty.app.domain.usecase.client.GetClientAppointmentsUseCase
import com.agendaprobeauty.app.domain.usecase.client.GetClientUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

data class ClientDetailUiState(
    val client: Client? = null,
    val appointments: List<Appointment> = emptyList(),
    val completedCount: Int = 0,
    val canceledCount: Int = 0,
    val totalSpentCents: Long = 0L,
    val lastVisitAt: Long? = null,
)

class ClientDetailViewModel(
    clientId: Long,
    getClient: GetClientUseCase,
    getClientAppointments: GetClientAppointmentsUseCase,
) : ViewModel() {
    val state: StateFlow<ClientDetailUiState> = combine(
        getClient(clientId),
        getClientAppointments(clientId),
    ) { client, appointments ->
        val completed = appointments.filter { it.status == AppointmentStatus.COMPLETED }
        ClientDetailUiState(
            client = client,
            appointments = appointments,
            completedCount = completed.size,
            canceledCount = appointments.count { it.status == AppointmentStatus.CANCELED || it.status == AppointmentStatus.NO_SHOW },
            totalSpentCents = completed.sumOf { it.priceCents },
            lastVisitAt = completed.maxByOrNull { it.startAt }?.startAt ?: appointments.maxByOrNull { it.startAt }?.startAt,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), ClientDetailUiState())
}
