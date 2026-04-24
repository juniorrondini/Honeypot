package com.agendaprobeauty.app.data.repository

import com.agendaprobeauty.app.data.local.dao.StaffDao
import com.agendaprobeauty.app.data.mapper.toDomain
import com.agendaprobeauty.app.data.mapper.toEntity
import com.agendaprobeauty.app.domain.model.StaffMember
import com.agendaprobeauty.app.domain.repository.StaffRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StaffRepositoryImpl(
    private val dao: StaffDao,
) : StaffRepository {
    override fun observeActiveStaff(): Flow<List<StaffMember>> =
        dao.observeActiveStaff().map { members -> members.map { it.toDomain() } }

    override suspend fun getStaffMember(id: Long): StaffMember? = dao.getById(id)?.toDomain()

    override suspend fun saveStaffMember(member: StaffMember): Long = dao.upsert(member.toEntity())

    override suspend fun deactivateStaffMember(id: Long) = dao.deactivate(id)
}
