package com.agendaprobeauty.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "monthly_usage")
data class MonthlyUsageEntity(
    @PrimaryKey val monthKey: String,
    val appointmentCount: Int,
    val updatedAt: Long,
)
