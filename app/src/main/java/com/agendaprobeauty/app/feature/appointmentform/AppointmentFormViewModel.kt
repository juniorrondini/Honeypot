package com.agendaprobeauty.app.feature.appointmentform

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agendaprobeauty.app.core.util.DateUtils
import com.agendaprobeauty.app.core.util.MoneyUtils
import com.agendaprobeauty.app.domain.model.Appointment
import com.agendaprobeauty.app.domain.model.AppointmentStatus
import com.agendaprobeauty.app.domain.model.BeautyService
import com.agendaprobeauty.app.domain.model.Client
import com.agendaprobeauty.app.domain.model.StaffMember
import com.agendaprobeauty.app.domain.usecase.appointment.CreateAppointmentUseCase
import com.agendaprobeauty.app.domain.usecase.appointment.GetDailyAppointmentsUseCase
import com.agendaprobeauty.app.domain.usecase.client.SearchClientsUseCase
import com.agendaprobeauty.app.domain.usecase.service.GetActiveServicesUseCase
import com.agendaprobeauty.app.domain.usecase.staff.GetActiveStaffUseCase
import java.time.LocalDate
import java.time.LocalTime
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class AppointmentFormUiState(
    val staff: List<StaffMember> = emptyList(),
    val clients: List<Client> = emptyList(),
    val services: List<BeautyService> = emptyList(),
    val selectedStaffMemberId: Long? = null,
    val selectedClientId: Long? = null,
    val selectedServiceId: Long? = null,
    val availableSlots: List<String> = emptyList(),
    val date: String = DateUtils.formatInputDate(DateUtils.today()),
    val time: String = "09:00",
    val price: String = "",
    val duration: String = "",
    val notes: String = "",
    val error: String? = null,
    val isSaving: Boolean = false,
) {
    val selectedStaff: StaffMember? = staff.firstOrNull { it.id == selectedStaffMemberId }
    val selectedClient: Client? = clients.firstOrNull { it.id == selectedClientId }
    val selectedService: BeautyService? = services.firstOrNull { it.id == selectedServiceId }
}

private data class AppointmentFormInputs(
    val selectedStaffMemberId: Long? = null,
    val selectedClientId: Long? = null,
    val selectedServiceId: Long? = null,
    val date: String = DateUtils.formatInputDate(DateUtils.today()),
    val time: String = "09:00",
    val price: String = "",
    val duration: String = "",
    val notes: String = "",
    val error: String? = null,
    val isSaving: Boolean = false,
)

@OptIn(ExperimentalCoroutinesApi::class)
class AppointmentFormViewModel(
    private val createAppointment: CreateAppointmentUseCase,
    private val getDailyAppointments: GetDailyAppointmentsUseCase,
    getActiveStaff: GetActiveStaffUseCase,
    searchClients: SearchClientsUseCase,
    getActiveServices: GetActiveServicesUseCase,
    initialClientId: Long? = null,
) : ViewModel() {
    private val inputs = MutableStateFlow(AppointmentFormInputs(selectedClientId = initialClientId))
    private val staffFlow = getActiveStaff()
    private val clientsFlow = searchClients("")
    private val servicesFlow = getActiveServices()

    val state = combine(inputs, staffFlow, clientsFlow, servicesFlow) { inputs, staff, clients, services ->
        val selectedStaffId = inputs.selectedStaffMemberId ?: staff.firstOrNull()?.id
        val selectedClientId = inputs.selectedClientId?.takeIf { id -> clients.any { it.id == id } }
        val selectedService = services.firstOrNull { it.id == inputs.selectedServiceId }
        val selectedServiceId = selectedService?.id
        val price = if (inputs.price.isBlank() && selectedService != null) {
            MoneyUtils.format(selectedService.priceCents)
        } else {
            inputs.price
        }
        val duration = if (inputs.duration.isBlank() && selectedService != null) {
            selectedService.durationMinutes.toString()
        } else {
            inputs.duration
        }

        AppointmentFormUiState(
            staff = staff,
            clients = clients,
            services = services,
            selectedStaffMemberId = selectedStaffId,
            selectedClientId = selectedClientId,
            selectedServiceId = selectedServiceId,
            date = inputs.date,
            time = inputs.time,
            price = price,
            duration = duration,
            notes = inputs.notes,
            error = inputs.error,
            isSaving = inputs.isSaving,
        )
    }.flatMapLatest { baseState ->
        observeDailyAppointments(baseState).map { appointments ->
            val slots = buildAvailableSlots(
                member = baseState.selectedStaff,
                appointments = appointments,
                serviceDurationMinutes = baseState.duration.toIntOrNull()
                    ?: baseState.selectedService?.durationMinutes
                    ?: baseState.selectedStaff?.slotMinutes
                    ?: 30,
            )
            baseState.copy(
                availableSlots = slots,
                time = baseState.time.takeIf { it in slots } ?: slots.firstOrNull().orEmpty(),
            )
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), AppointmentFormUiState())

    fun selectStaff(id: Long) {
        update { copy(selectedStaffMemberId = id, error = null) }
    }

    fun selectClient(id: Long) {
        update { copy(selectedClientId = id, error = null) }
    }

    fun selectService(id: Long) {
        val service = state.value.services.firstOrNull { it.id == id }
        update {
            copy(
                selectedServiceId = id,
                price = service?.let { MoneyUtils.format(it.priceCents) }.orEmpty(),
                duration = service?.durationMinutes?.toString().orEmpty(),
                error = null,
            )
        }
    }

    fun updateDate(value: String) = update { copy(date = value, error = null) }
    fun updateTime(value: String) = update { copy(time = value, error = null) }
    fun selectTime(value: String) = update { copy(time = value, error = null) }
    fun updatePrice(value: String) = update { copy(price = value, error = null) }
    fun updateDuration(value: String) = update { copy(duration = value, error = null) }
    fun updateNotes(value: String) = update { copy(notes = value, error = null) }

    fun save(onSaved: () -> Unit) {
        val current = state.value
        val member = current.selectedStaff
        val client = current.selectedClient
        val service = current.selectedService
        val duration = current.duration.toIntOrNull()

        when {
            member == null -> update { copy(error = "Cadastre e selecione um profissional.") }
            client == null -> update { copy(error = "Selecione um cliente cadastrado.") }
            service == null -> update { copy(error = "Selecione um servico cadastrado.") }
            duration == null || duration <= 0 -> update { copy(error = "Duracao invalida.") }
            current.time.isBlank() -> update { copy(error = "Selecione um horario livre.") }
            else -> viewModelScope.launch {
                update { copy(isSaving = true, error = null) }
                val result = runCatching {
                    DateUtils.millisFor(
                        DateUtils.parseInputDate(current.date),
                        DateUtils.parseInputTime(current.time),
                    )
                }.mapCatching { startAt ->
                    createAppointment(
                        clientId = client.id,
                        staffMemberId = member.id,
                        staffMemberName = member.name,
                        serviceId = service.id,
                        clientName = client.name,
                        serviceName = service.name,
                        priceCents = MoneyUtils.parseToCents(current.price),
                        startAt = startAt,
                        durationMinutes = duration,
                        notes = current.notes,
                    ).getOrThrow()
                }
                if (result.isSuccess) {
                    onSaved()
                } else {
                    update {
                        copy(
                            isSaving = false,
                            error = result.exceptionOrNull()?.message ?: "Nao foi possivel salvar.",
                        )
                    }
                }
            }
        }
    }

    private fun observeDailyAppointments(state: AppointmentFormUiState): Flow<List<Appointment>> {
        val date = runCatching { DateUtils.parseInputDate(state.date) }.getOrDefault(DateUtils.today())
        return getDailyAppointments.forStaff(state.selectedStaffMemberId, date)
    }

    private fun buildAvailableSlots(
        member: StaffMember?,
        appointments: List<Appointment>,
        serviceDurationMinutes: Int,
    ): List<String> {
        if (member == null) return emptyList()
        val date = runCatching { DateUtils.parseInputDate(inputs.value.date) }.getOrDefault(LocalDate.now())
        val slots = mutableListOf<String>()
        var time = LocalTime.of(member.workStartHour, 0)
        val end = LocalTime.of(member.workEndHour, 0)
        while (time.plusMinutes(serviceDurationMinutes.toLong()) <= end) {
            val startAt = DateUtils.millisFor(date, time)
            val endAt = startAt + serviceDurationMinutes * 60_000L
            val overlaps = appointments.any { appointment ->
                appointment.status != AppointmentStatus.CANCELED &&
                    appointment.status != AppointmentStatus.NO_SHOW &&
                    appointment.startAt < endAt &&
                    appointment.endAt > startAt
            }
            if (!overlaps) slots += time.toString()
            time = time.plusMinutes(member.slotMinutes.toLong())
        }
        return slots
    }

    private fun update(block: AppointmentFormInputs.() -> AppointmentFormInputs) {
        inputs.value = inputs.value.block()
    }
}
