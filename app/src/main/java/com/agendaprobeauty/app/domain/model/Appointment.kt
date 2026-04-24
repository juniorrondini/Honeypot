package com.agendaprobeauty.app.domain.model

data class Appointment(
    val id: Long = 0L,
    val staffMemberId: Long?,
    val staffMemberNameSnapshot: String,
    val clientId: Long?,
    val serviceId: Long?,
    val clientNameSnapshot: String,
    val serviceNameSnapshot: String,
    val priceCents: Long,
    val startAt: Long,
    val endAt: Long,
    val status: AppointmentStatus,
    val notes: String?,
    val createdAt: Long,
    val updatedAt: Long,
    val canceledAt: Long?,
)
