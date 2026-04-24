package com.agendaprobeauty.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "services")
data class ServiceEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val name: String,
    val priceCents: Long,
    val durationMinutes: Int,
    val isActive: Boolean,
    val createdAt: Long,
)
