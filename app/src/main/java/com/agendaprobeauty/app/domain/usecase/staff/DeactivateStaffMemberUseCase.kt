package com.agendaprobeauty.app.domain.usecase.staff

import com.agendaprobeauty.app.domain.repository.StaffRepository

class DeactivateStaffMemberUseCase(
    private val repository: StaffRepository,
) {
    suspend operator fun invoke(id: Long) = repository.deactivateStaffMember(id)
}
