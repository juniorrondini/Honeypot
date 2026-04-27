package com.agendaprobeauty.app.data.repository

import com.agendaprobeauty.app.data.local.dao.AppointmentDao
import com.agendaprobeauty.app.data.mapper.toDomain
import com.agendaprobeauty.app.data.mapper.toEntity
import com.agendaprobeauty.app.domain.model.Appointment
import com.agendaprobeauty.app.domain.model.AppointmentStatus
import com.agendaprobeauty.app.domain.repository.AppointmentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AppointmentRepositoryImpl(
    private val dao: AppointmentDao,
) : AppointmentRepository {
    override fun observeAppointmentsBetween(startAt: Long, endAt: Long): Flow<List<Appointment>> =
        dao.observeBetween(startAt, endAt).map { appointments -> appointments.map { it.toDomain() } }

    override fun observeAppointmentsBetweenForStaff(staffMemberId: Long?, startAt: Long, endAt: Long): Flow<List<Appointment>> =
        dao.observeBetweenForStaff(staffMemberId, startAt, endAt).map { appointments -> appointments.map { it.toDomain() } }

    override fun observeAppointment(id: Long): Flow<Appointment?> = dao.observeById(id).map { it?.toDomain() }

    override suspend fun countAppointmentsBetween(startAt: Long, endAt: Long): Int = dao.countBetween(startAt, endAt)

    override suspend fun countOverlappingForStaff(staffMemberId: Long, startAt: Long, endAt: Long): Int =
        dao.countOverlappingForStaff(staffMemberId, startAt, endAt)

    override suspend fun saveAppointment(appointment: Appointment): Long = dao.upsert(appointment.toEntity())

    override suspend fun updateStatus(id: Long, status: AppointmentStatus, timestamp: Long) {
        dao.updateStatus(
            id = id,
            status = status,
            timestamp = timestamp,
            canceledAt = if (status == AppointmentStatus.CANCELED) timestamp else null,
        )
    }

    override suspend fun deleteAppointment(id: Long) = dao.deleteById(id)
}
