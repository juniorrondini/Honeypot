package com.agendaprobeauty.app.domain.model

data class FinancialEntry(
    val id: Long = 0L,
    val appointmentId: Long?,
    val type: FinancialType,
    val description: String,
    val amountCents: Long,
    val occurredAt: Long,
    val createdAt: Long,
)
