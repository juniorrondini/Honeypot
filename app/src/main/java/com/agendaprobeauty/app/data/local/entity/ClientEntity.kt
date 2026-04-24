package com.agendaprobeauty.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clients")
data class ClientEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val name: String,
    val phone: String?,
    val notes: String?,
    val createdAt: Long,
    val updatedAt: Long,
)
