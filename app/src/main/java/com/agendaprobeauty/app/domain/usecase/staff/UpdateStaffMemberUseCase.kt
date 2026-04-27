package com.agendaprobeauty.app.domain.usecase.staff

import com.agendaprobeauty.app.domain.model.StaffMember
import com.agendaprobeauty.app.domain.repository.StaffRepository

class UpdateStaffMemberUseCase(
    private val repository: StaffRepository,
) {
    suspend operator fun invoke(member: StaffMember) {
        repository.saveStaffMember(member)
    }
}
