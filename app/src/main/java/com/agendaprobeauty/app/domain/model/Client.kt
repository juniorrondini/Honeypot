package com.agendaprobeauty.app.domain.model

data class Client(
    val id: Long = 0L,
    val name: String,
    val phone: String?,
    val notes: String?,
    val createdAt: Long,
    val updatedAt: Long,
)
