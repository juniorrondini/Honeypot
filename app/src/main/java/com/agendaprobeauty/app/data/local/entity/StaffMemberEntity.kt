package com.agendaprobeauty.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "staff_members")
data class StaffMemberEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val name: String,
    val role: String,
    val phone: String?,
    val workStartHour: Int,
    val workEndHour: Int,
    val slotMinutes: Int,
    val isActive: Boolean,
    val createdAt: Long,
)
