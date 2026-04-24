package com.agendaprobeauty.app.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.agendaprobeauty.app.domain.model.AppointmentStatus

@Entity(
    tableName = "appointments",
    indices = [
        Index("clientId"),
        Index("serviceId"),
        Index("startAt"),
        Index("status"),
    ],
)
data class AppointmentEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
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
