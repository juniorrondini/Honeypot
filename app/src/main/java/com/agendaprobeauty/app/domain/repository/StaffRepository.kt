package com.agendaprobeauty.app.domain.repository

import com.agendaprobeauty.app.domain.model.StaffMember
import kotlinx.coroutines.flow.Flow

interface StaffRepository {
    fun observeActiveStaff(): Flow<List<StaffMember>>
    suspend fun getStaffMember(id: Long): StaffMember?
    suspend fun saveStaffMember(member: StaffMember): Long
    suspend fun deactivateStaffMember(id: Long)
}
