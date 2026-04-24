package com.agendaprobeauty.app.feature.staff

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agendaprobeauty.app.domain.model.StaffMember
import com.agendaprobeauty.app.domain.usecase.staff.CreateStaffMemberUseCase
import com.agendaprobeauty.app.domain.usecase.staff.DeactivateStaffMemberUseCase
import com.agendaprobeauty.app.domain.usecase.staff.GetActiveStaffUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class StaffUiState(
    val staff: List<StaffMember> = emptyList(),
    val name: String = "",
    val role: String = "",
    val phone: String = "",
    val startHour: String = "9",
    val endHour: String = "18",
    val slotMinutes: String = "30",
)

class StaffViewModel(
    getActiveStaff: GetActiveStaffUseCase,
    private val createStaffMember: CreateStaffMemberUseCase,
    private val deactivateStaffMember: DeactivateStaffMemberUseCase,
) : ViewModel() {
    private val formState = MutableStateFlow(StaffUiState())

    val state: StateFlow<StaffUiState> = combine(formState, getActiveStaff()) { form, staff ->
        form.copy(staff = staff)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), StaffUiState())

    fun updateName(value: String) = update { copy(name = value) }
    fun updateRole(value: String) = update { copy(role = value) }
    fun updatePhone(value: String) = update { copy(phone = value) }
    fun updateStartHour(value: String) = update { copy(startHour = value.filter(Char::isDigit).take(2)) }
    fun updateEndHour(value: String) = update { copy(endHour = value.filter(Char::isDigit).take(2)) }
    fun updateSlotMinutes(value: String) = update { copy(slotMinutes = value.filter(Char::isDigit).take(3)) }

    fun save() {
        val current = formState.value
        val start = current.startHour.toIntOrNull() ?: return
        val end = current.endHour.toIntOrNull() ?: return
        val slot = current.slotMinutes.toIntOrNull() ?: return
        if (current.name.isBlank() || current.role.isBlank() || start !in 0..23 || end !in 1..24 || end <= start || slot <= 0) return

        viewModelScope.launch {
            createStaffMember(current.name, current.role, current.phone, start, end, slot)
            update { copy(name = "", role = "", phone = "", startHour = "9", endHour = "18", slotMinutes = "30") }
        }
    }

    fun deactivate(id: Long) {
        viewModelScope.launch { deactivateStaffMember(id) }
    }

    private fun update(block: StaffUiState.() -> StaffUiState) {
        formState.value = formState.value.block()
    }
}
