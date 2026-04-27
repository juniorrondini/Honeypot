package com.agendaprobeauty.app.domain.usecase.demo

import com.agendaprobeauty.app.core.util.DateUtils
import com.agendaprobeauty.app.domain.model.BeautyService
import com.agendaprobeauty.app.domain.model.Client
import com.agendaprobeauty.app.domain.model.Professional
import com.agendaprobeauty.app.domain.model.StaffMember
import com.agendaprobeauty.app.domain.repository.ClientRepository
import com.agendaprobeauty.app.domain.repository.ProfessionalRepository
import com.agendaprobeauty.app.domain.repository.ServiceRepository
import com.agendaprobeauty.app.domain.repository.SettingsRepository
import com.agendaprobeauty.app.domain.repository.StaffRepository
import kotlinx.coroutines.flow.first

class SeedDemoDataUseCase(
    private val professionalRepository: ProfessionalRepository,
    private val staffRepository: StaffRepository,
    private val serviceRepository: ServiceRepository,
    private val clientRepository: ClientRepository,
    private val settingsRepository: SettingsRepository,
) {
    suspend operator fun invoke() {
        if (settingsRepository.isDemoDataSeeded.first()) return

        val now = DateUtils.now()
        if (professionalRepository.observeProfessional().first() == null) {
            professionalRepository.saveProfessional(
                Professional(
                    name = "Lucas Andrade",
                    businessName = "Honeypot Beauty Studio",
                    phone = "(11) 98888-1000",
                    profession = "Barbeiro",
                    createdAt = now,
                ),
            )
        }

        val existingStaffNames = staffRepository.observeActiveStaff().first().map { it.name.normalizedName() }.toSet()
        demoStaff(now)
            .filterNot { it.name.normalizedName() in existingStaffNames }
            .forEach { staffRepository.saveStaffMember(it) }

        val existingServiceNames = serviceRepository.observeAllServices().first().map { it.name.normalizedName() }.toSet()
        demoServices(now)
            .filterNot { it.name.normalizedName() in existingServiceNames }
            .forEach { serviceRepository.saveService(it) }

        val existingClientNames = clientRepository.observeClients("").first().map { it.name.normalizedName() }.toSet()
        demoClients(now)
            .filterNot { it.name.normalizedName() in existingClientNames }
            .forEach { clientRepository.saveClient(it) }

        settingsRepository.setOnboardingCompleted(true)
        settingsRepository.setDemoDataSeeded(true)
    }

    private fun demoStaff(now: Long) = listOf(
        StaffMember(
            name = "Lucas Andrade",
            role = "Barbeiro",
            phone = "(11) 98888-1000",
            workStartHour = 8,
            workEndHour = 18,
            slotMinutes = 30,
            createdAt = now,
        ),
        StaffMember(
            name = "Marina Costa",
            role = "Cabeleireira",
            phone = "(11) 97777-2000",
            workStartHour = 9,
            workEndHour = 19,
            slotMinutes = 30,
            createdAt = now,
        ),
        StaffMember(
            name = "Bianca Lima",
            role = "Manicure",
            phone = "(11) 96666-3000",
            workStartHour = 10,
            workEndHour = 18,
            slotMinutes = 30,
            createdAt = now,
        ),
    )

    private fun demoServices(now: Long) = listOf(
        BeautyService(name = "Corte masculino", priceCents = 4500, durationMinutes = 30, createdAt = now),
        BeautyService(name = "Barba", priceCents = 3500, durationMinutes = 30, createdAt = now),
        BeautyService(name = "Corte + barba", priceCents = 7500, durationMinutes = 60, createdAt = now),
        BeautyService(name = "Escova", priceCents = 6000, durationMinutes = 45, createdAt = now),
        BeautyService(name = "Manicure", priceCents = 4000, durationMinutes = 60, createdAt = now),
    )

    private fun demoClients(now: Long) = listOf(
        Client(
            name = "Rafael Souza",
            phone = "(11) 95555-0101",
            notes = "Prefere horario de almoco.",
            createdAt = now,
            updatedAt = now,
        ),
        Client(
            name = "Camila Nunes",
            phone = "(11) 95555-0202",
            notes = "Cliente recorrente de sexta.",
            createdAt = now,
            updatedAt = now,
        ),
        Client(
            name = "Thiago Martins",
            phone = "(11) 95555-0303",
            notes = "Corte baixo e barba desenhada.",
            createdAt = now,
            updatedAt = now,
        ),
        Client(
            name = "Juliana Rocha",
            phone = "(11) 95555-0404",
            notes = "Gosta de confirmar no dia anterior.",
            createdAt = now,
            updatedAt = now,
        ),
    )

    private fun String.normalizedName(): String = trim().lowercase()
}
