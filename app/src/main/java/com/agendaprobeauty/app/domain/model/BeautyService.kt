package com.agendaprobeauty.app.domain.model

data class BeautyService(
    val id: Long = 0L,
    val name: String,
    val priceCents: Long,
    val durationMinutes: Int,
    val isActive: Boolean = true,
    val createdAt: Long,
)
