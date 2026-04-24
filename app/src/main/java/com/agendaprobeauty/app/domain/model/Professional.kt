package com.agendaprobeauty.app.domain.model

data class Professional(
    val id: Long = 1L,
    val name: String,
    val businessName: String?,
    val phone: String?,
    val profession: String,
    val createdAt: Long,
)
