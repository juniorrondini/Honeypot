package com.agendaprobeauty.app.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.agendaprobeauty.app.data.local.dao.AppointmentDao
import com.agendaprobeauty.app.data.local.dao.ClientDao
import com.agendaprobeauty.app.data.local.dao.FinancialDao
import com.agendaprobeauty.app.data.local.dao.MonthlyUsageDao
import com.agendaprobeauty.app.data.local.dao.ProfessionalDao
import com.agendaprobeauty.app.data.local.dao.ServiceDao
import com.agendaprobeauty.app.data.local.entity.AppointmentEntity
import com.agendaprobeauty.app.data.local.entity.ClientEntity
import com.agendaprobeauty.app.data.local.entity.FinancialEntryEntity
import com.agendaprobeauty.app.data.local.entity.MonthlyUsageEntity
import com.agendaprobeauty.app.data.local.entity.ProfessionalEntity
import com.agendaprobeauty.app.data.local.entity.ServiceEntity

@Database(
    entities = [
        ProfessionalEntity::class,
        ClientEntity::class,
        ServiceEntity::class,
        AppointmentEntity::class,
        FinancialEntryEntity::class,
        MonthlyUsageEntity::class,
    ],
    version = 1,
    exportSchema = true,
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun professionalDao(): ProfessionalDao
    abstract fun clientDao(): ClientDao
    abstract fun serviceDao(): ServiceDao
    abstract fun appointmentDao(): AppointmentDao
    abstract fun financialDao(): FinancialDao
    abstract fun monthlyUsageDao(): MonthlyUsageDao
}
