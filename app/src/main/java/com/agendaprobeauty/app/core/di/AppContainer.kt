package com.agendaprobeauty.app.core.di

import android.content.Context
import androidx.room.Room
import com.agendaprobeauty.app.core.database.AppDatabase
import com.agendaprobeauty.app.core.datastore.SettingsDataStore
import com.agendaprobeauty.app.data.repository.AppointmentRepositoryImpl
import com.agendaprobeauty.app.data.repository.ClientRepositoryImpl
import com.agendaprobeauty.app.data.repository.FinanceRepositoryImpl
import com.agendaprobeauty.app.data.repository.ProfessionalRepositoryImpl
import com.agendaprobeauty.app.data.repository.ServiceRepositoryImpl
import com.agendaprobeauty.app.data.repository.SettingsRepositoryImpl
import com.agendaprobeauty.app.data.repository.StaffRepositoryImpl
import com.agendaprobeauty.app.data.repository.SubscriptionRepositoryImpl
import com.agendaprobeauty.app.domain.repository.AppointmentRepository
import com.agendaprobeauty.app.domain.repository.ClientRepository
import com.agendaprobeauty.app.domain.repository.FinanceRepository
import com.agendaprobeauty.app.domain.repository.ProfessionalRepository
import com.agendaprobeauty.app.domain.repository.ServiceRepository
import com.agendaprobeauty.app.domain.repository.SettingsRepository
import com.agendaprobeauty.app.domain.repository.StaffRepository
import com.agendaprobeauty.app.domain.repository.SubscriptionRepository
import com.agendaprobeauty.app.domain.usecase.appointment.CanCreateAppointmentUseCase
import com.agendaprobeauty.app.domain.usecase.appointment.CancelAppointmentUseCase
import com.agendaprobeauty.app.domain.usecase.appointment.CompleteAppointmentUseCase
import com.agendaprobeauty.app.domain.usecase.appointment.CreateAppointmentUseCase
import com.agendaprobeauty.app.domain.usecase.appointment.GetDailyAppointmentsUseCase
import com.agendaprobeauty.app.domain.usecase.appointment.GetAppointmentUseCase
import com.agendaprobeauty.app.domain.usecase.appointment.GetMonthlyAppointmentsUseCase
import com.agendaprobeauty.app.domain.usecase.appointment.UpdateAppointmentUseCase
import com.agendaprobeauty.app.domain.usecase.client.CreateClientUseCase
import com.agendaprobeauty.app.domain.usecase.client.DeleteClientUseCase
import com.agendaprobeauty.app.domain.usecase.client.GetClientAppointmentsUseCase
import com.agendaprobeauty.app.domain.usecase.client.GetClientUseCase
import com.agendaprobeauty.app.domain.usecase.client.SearchClientsUseCase
import com.agendaprobeauty.app.domain.usecase.client.UpdateClientUseCase
import com.agendaprobeauty.app.domain.usecase.finance.CreateManualExpenseUseCase
import com.agendaprobeauty.app.domain.usecase.finance.GetFinanceEntriesUseCase
import com.agendaprobeauty.app.domain.usecase.finance.GetFinanceSummaryUseCase
import com.agendaprobeauty.app.domain.usecase.profile.GetProfessionalProfileUseCase
import com.agendaprobeauty.app.domain.usecase.profile.SaveProfessionalProfileUseCase
import com.agendaprobeauty.app.domain.usecase.service.CreateServiceUseCase
import com.agendaprobeauty.app.domain.usecase.service.DeactivateServiceUseCase
import com.agendaprobeauty.app.domain.usecase.service.GetActiveServicesUseCase
import com.agendaprobeauty.app.domain.usecase.service.GetAllServicesUseCase
import com.agendaprobeauty.app.domain.usecase.service.UpdateServiceUseCase
import com.agendaprobeauty.app.domain.usecase.staff.CreateStaffMemberUseCase
import com.agendaprobeauty.app.domain.usecase.staff.DeactivateStaffMemberUseCase
import com.agendaprobeauty.app.domain.usecase.staff.GetActiveStaffUseCase
import com.agendaprobeauty.app.domain.usecase.subscription.GetCurrentPlanUseCase

class AppContainer(
    context: Context,
) {
    val database: AppDatabase = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        "agenda_pro_beauty.db",
    )
        .fallbackToDestructiveMigration(dropAllTables = true)
        .build()

    private val settingsDataStore = SettingsDataStore(context.applicationContext)

    val professionalRepository: ProfessionalRepository = ProfessionalRepositoryImpl(database.professionalDao())
    val clientRepository: ClientRepository = ClientRepositoryImpl(database.clientDao())
    val serviceRepository: ServiceRepository = ServiceRepositoryImpl(database.serviceDao())
    val staffRepository: StaffRepository = StaffRepositoryImpl(database.staffDao())
    val appointmentRepository: AppointmentRepository = AppointmentRepositoryImpl(database.appointmentDao())
    val financeRepository: FinanceRepository = FinanceRepositoryImpl(database.financialDao())
    val settingsRepository: SettingsRepository = SettingsRepositoryImpl(settingsDataStore)
    val subscriptionRepository: SubscriptionRepository = SubscriptionRepositoryImpl(settingsRepository, appointmentRepository)

    val getActiveStaff = GetActiveStaffUseCase(staffRepository)
    val createStaffMember = CreateStaffMemberUseCase(staffRepository)
    val deactivateStaffMember = DeactivateStaffMemberUseCase(staffRepository)

    val getProfessionalProfile = GetProfessionalProfileUseCase(professionalRepository)
    val saveProfessionalProfile = SaveProfessionalProfileUseCase(professionalRepository, settingsRepository, createStaffMember)

    val searchClients = SearchClientsUseCase(clientRepository)
    val getClient = GetClientUseCase(clientRepository)
    val getClientAppointments = GetClientAppointmentsUseCase(appointmentRepository)
    val createClient = CreateClientUseCase(clientRepository)
    val updateClient = UpdateClientUseCase(clientRepository)
    val deleteClient = DeleteClientUseCase(clientRepository)

    val getActiveServices = GetActiveServicesUseCase(serviceRepository)
    val getAllServices = GetAllServicesUseCase(serviceRepository)
    val createService = CreateServiceUseCase(serviceRepository)
    val updateService = UpdateServiceUseCase(serviceRepository)
    val deactivateService = DeactivateServiceUseCase(serviceRepository)

    val getDailyAppointments = GetDailyAppointmentsUseCase(appointmentRepository)
    val getMonthlyAppointments = GetMonthlyAppointmentsUseCase(appointmentRepository)
    val getAppointment = GetAppointmentUseCase(appointmentRepository)
    val createAppointment = CreateAppointmentUseCase(appointmentRepository, subscriptionRepository)
    val updateAppointment = UpdateAppointmentUseCase(appointmentRepository)
    val cancelAppointment = CancelAppointmentUseCase(appointmentRepository)
    val completeAppointment = CompleteAppointmentUseCase(appointmentRepository, financeRepository)
    val canCreateAppointment = CanCreateAppointmentUseCase(subscriptionRepository)

    val getFinanceSummary = GetFinanceSummaryUseCase(financeRepository)
    val getFinanceEntries = GetFinanceEntriesUseCase(financeRepository)
    val createManualExpense = CreateManualExpenseUseCase(financeRepository)

    val getCurrentPlan = GetCurrentPlanUseCase(subscriptionRepository)
}
