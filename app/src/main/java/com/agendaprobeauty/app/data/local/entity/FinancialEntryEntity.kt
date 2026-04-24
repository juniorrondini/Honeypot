package com.agendaprobeauty.app.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.agendaprobeauty.app.domain.model.FinancialType

@Entity(
    tableName = "financial_entries",
    indices = [
        Index("appointmentId"),
        Index("occurredAt"),
        Index("type"),
    ],
)
data class FinancialEntryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val appointmentId: Long?,
    val type: FinancialType,
    val description: String,
    val amountCents: Long,
    val occurredAt: Long,
    val createdAt: Long,
)
