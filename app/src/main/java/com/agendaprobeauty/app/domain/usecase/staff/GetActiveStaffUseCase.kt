package com.agendaprobeauty.app.domain.usecase.staff

import com.agendaprobeauty.app.domain.repository.StaffRepository

class GetActiveStaffUseCase(
    private val repository: StaffRepository,
) {
    operator fun invoke() = repository.observeActiveStaff()
}
