package com.agendaprobeauty.app.core.database

import androidx.room.TypeConverter
import com.agendaprobeauty.app.domain.model.AppointmentStatus
import com.agendaprobeauty.app.domain.model.FinancialType

class Converters {
    @TypeConverter
    fun appointmentStatusToString(value: AppointmentStatus): String = value.name

    @TypeConverter
    fun stringToAppointmentStatus(value: String): AppointmentStatus = AppointmentStatus.valueOf(value)

    @TypeConverter
    fun financialTypeToString(value: FinancialType): String = value.name

    @TypeConverter
    fun stringToFinancialType(value: String): FinancialType = FinancialType.valueOf(value)
}
