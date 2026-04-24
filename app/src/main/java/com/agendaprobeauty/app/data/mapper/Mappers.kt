package com.agendaprobeauty.app.data.mapper

import com.agendaprobeauty.app.data.local.entity.AppointmentEntity
import com.agendaprobeauty.app.data.local.entity.ClientEntity
import com.agendaprobeauty.app.data.local.entity.FinancialEntryEntity
import com.agendaprobeauty.app.data.local.entity.ProfessionalEntity
import com.agendaprobeauty.app.data.local.entity.ServiceEntity
import com.agendaprobeauty.app.data.local.entity.StaffMemberEntity
import com.agendaprobeauty.app.domain.model.Appointment
import com.agendaprobeauty.app.domain.model.BeautyService
import com.agendaprobeauty.app.domain.model.Client
import com.agendaprobeauty.app.domain.model.FinancialEntry
import com.agendaprobeauty.app.domain.model.Professional
import com.agendaprobeauty.app.domain.model.StaffMember

fun ProfessionalEntity.toDomain() = Professional(id, name, businessName, phone, profession, createdAt)
fun Professional.toEntity() = ProfessionalEntity(id, name, businessName, phone, profession, createdAt)

fun ClientEntity.toDomain() = Client(id, name, phone, notes, createdAt, updatedAt)
fun Client.toEntity() = ClientEntity(id, name, phone, notes, createdAt, updatedAt)

fun ServiceEntity.toDomain() = BeautyService(id, name, priceCents, durationMinutes, isActive, createdAt)
fun BeautyService.toEntity() = ServiceEntity(id, name, priceCents, durationMinutes, isActive, createdAt)

fun StaffMemberEntity.toDomain() = StaffMember(
    id = id,
    name = name,
    role = role,
    phone = phone,
    workStartHour = workStartHour,
    workEndHour = workEndHour,
    slotMinutes = slotMinutes,
    isActive = isActive,
    createdAt = createdAt,
)

fun StaffMember.toEntity() = StaffMemberEntity(
    id = id,
    name = name,
    role = role,
    phone = phone,
    workStartHour = workStartHour,
    workEndHour = workEndHour,
    slotMinutes = slotMinutes,
    isActive = isActive,
    createdAt = createdAt,
)

fun AppointmentEntity.toDomain() = Appointment(
    id = id,
    staffMemberId = staffMemberId,
    staffMemberNameSnapshot = staffMemberNameSnapshot,
    clientId = clientId,
    serviceId = serviceId,
    clientNameSnapshot = clientNameSnapshot,
    serviceNameSnapshot = serviceNameSnapshot,
    priceCents = priceCents,
    startAt = startAt,
    endAt = endAt,
    status = status,
    notes = notes,
    createdAt = createdAt,
    updatedAt = updatedAt,
    canceledAt = canceledAt,
)

fun Appointment.toEntity() = AppointmentEntity(
    id = id,
    staffMemberId = staffMemberId,
    staffMemberNameSnapshot = staffMemberNameSnapshot,
    clientId = clientId,
    serviceId = serviceId,
    clientNameSnapshot = clientNameSnapshot,
    serviceNameSnapshot = serviceNameSnapshot,
    priceCents = priceCents,
    startAt = startAt,
    endAt = endAt,
    status = status,
    notes = notes,
    createdAt = createdAt,
    updatedAt = updatedAt,
    canceledAt = canceledAt,
)

fun FinancialEntryEntity.toDomain() = FinancialEntry(id, appointmentId, type, description, amountCents, occurredAt, createdAt)
fun FinancialEntry.toEntity() = FinancialEntryEntity(id, appointmentId, type, description, amountCents, occurredAt, createdAt)
