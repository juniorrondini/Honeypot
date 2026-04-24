package com.agendaprobeauty.app.domain.model

data class PlanStatus(
    val type: PlanType,
    val freeMonthlyLimit: Int = 30,
    val currentMonthAppointments: Int,
) {
    val canCreateAppointment: Boolean
        get() = type == PlanType.PREMIUM || currentMonthAppointments < freeMonthlyLimit
}
