package com.agendaprobeauty.app.domain.usecase.staff

import com.agendaprobeauty.app.core.util.DateUtils
import com.agendaprobeauty.app.domain.model.StaffMember
import com.agendaprobeauty.app.domain.repository.StaffRepository

class CreateStaffMemberUseCase(
    private val repository: StaffRepository,
) {
    suspend operator fun invoke(
        name: String,
        role: String,
        phone: String?,
        workStartHour: Int,
        workEndHour: Int,
        slotMinutes: Int,
    ): Long = repository.saveStaffMember(
        StaffMember(
            name = name.trim(),
            role = role.trim(),
            phone = phone?.trim()?.takeIf { it.isNotBlank() },
            workStartHour = workStartHour,
            workEndHour = workEndHour,
            slotMinutes = slotMinutes,
            createdAt = DateUtils.now(),
        ),
    )
}
