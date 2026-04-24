package com.agendaprobeauty.app.domain.model

data class MonthlyUsage(
    val monthKey: String,
    val appointmentCount: Int,
    val updatedAt: Long,
)
