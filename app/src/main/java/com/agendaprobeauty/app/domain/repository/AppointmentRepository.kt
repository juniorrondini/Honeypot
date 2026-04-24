package com.agendaprobeauty.app.domain.repository

import com.agendaprobeauty.app.domain.model.Appointment
import com.agendaprobeauty.app.domain.model.AppointmentStatus
import kotlinx.coroutines.flow.Flow

interface AppointmentRepository {
    fun observeAppointmentsBetween(startAt: Long, endAt: Long): Flow<List<Appointment>>
    fun observeAppointment(id: Long): Flow<Appointment?>
    suspend fun countAppointmentsBetween(startAt: Long, endAt: Long): Int
    suspend fun saveAppointment(appointment: Appointment): Long
    suspend fun updateStatus(id: Long, status: AppointmentStatus, timestamp: Long)
    suspend fun deleteAppointment(id: Long)
}
