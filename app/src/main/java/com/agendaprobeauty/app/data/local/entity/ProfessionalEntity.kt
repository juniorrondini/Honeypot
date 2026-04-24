package com.agendaprobeauty.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "professionals")
data class ProfessionalEntity(
    @PrimaryKey val id: Long = 1L,
    val name: String,
    val businessName: String?,
    val phone: String?,
    val profession: String,
    val createdAt: Long,
)
