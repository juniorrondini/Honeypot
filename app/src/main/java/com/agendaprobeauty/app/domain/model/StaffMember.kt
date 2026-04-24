package com.agendaprobeauty.app.domain.model

data class StaffMember(
    val id: Long = 0L,
    val name: String,
    val role: String,
    val phone: String?,
    val workStartHour: Int,
    val workEndHour: Int,
    val slotMinutes: Int,
    val isActive: Boolean = true,
    val createdAt: Long,
)
