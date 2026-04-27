package com.agendaprobeauty.app.core.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Badge
import androidx.compose.material.icons.outlined.RoomService
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.agendaprobeauty.app.core.di.AppContainer
import com.agendaprobeauty.app.core.di.viewModelFactory
import com.agendaprobeauty.app.feature.agenda.AgendaScreen
import com.agendaprobeauty.app.feature.agenda.AgendaViewModel
import com.agendaprobeauty.app.feature.appointmentedit.AppointmentEditScreen
import com.agendaprobeauty.app.feature.appointmentedit.AppointmentEditViewModel
import com.agendaprobeauty.app.feature.appointmentform.AppointmentFormScreen
import com.agendaprobeauty.app.feature.appointmentform.AppointmentFormViewModel
import com.agendaprobeauty.app.feature.clients.ClientsScreen
import com.agendaprobeauty.app.feature.clients.ClientsViewModel
import com.agendaprobeauty.app.feature.clientdetail.ClientDetailScreen
import com.agendaprobeauty.app.feature.clientdetail.ClientDetailViewModel
import com.agendaprobeauty.app.feature.dashboard.DashboardScreen
import com.agendaprobeauty.app.feature.dashboard.DashboardViewModel
import com.agendaprobeauty.app.feature.finance.FinanceScreen
import com.agendaprobeauty.app.feature.finance.FinanceViewModel
import com.agendaprobeauty.app.feature.onboarding.OnboardingScreen
import com.agendaprobeauty.app.feature.onboarding.OnboardingViewModel
import com.agendaprobeauty.app.feature.premium.PremiumScreen
import com.agendaprobeauty.app.feature.services.ServicesScreen
import com.agendaprobeauty.app.feature.services.ServicesViewModel
import com.agendaprobeauty.app.feature.settings.SettingsScreen
import com.agendaprobeauty.app.feature.settings.SettingsViewModel
import com.agendaprobeauty.app.feature.staff.StaffScreen
import com.agendaprobeauty.app.feature.staff.StaffViewModel

private data class BottomDestination(
    val route: String,
    val label: String,
    val icon: @Composable () -> Unit,
)

private val bottomDestinations = listOf(
    BottomDestination(Routes.DASHBOARD, "Início") { Icon(Icons.Outlined.Home, contentDescription = null) },
    BottomDestination(Routes.AGENDA, "Agenda") { Icon(Icons.Outlined.CalendarToday, contentDescription = null) },
    BottomDestination(Routes.CLIENTS, "Clientes") { Icon(Icons.Outlined.Groups, contentDescription = null) },
    BottomDestination(Routes.SERVICES, "Serviços") { Icon(Icons.Outlined.RoomService, contentDescription = null) },
    BottomDestination(Routes.STAFF, "Equipe") { Icon(Icons.Outlined.Badge, contentDescription = null) },
)

@Composable
fun AppNavHost(
    appContainer: AppContainer,
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentRoute = currentDestination?.route
    val showBottomBar = currentRoute in bottomDestinations.map { it.route }

    Scaffold(
        modifier = modifier,
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    bottomDestinations.forEach { destination ->
                        val selected = currentDestination?.hierarchy?.any { it.route == destination.route } == true
                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                navController.navigate(destination.route) {
                                    popUpTo(Routes.DASHBOARD) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = destination.icon,
                            label = { Text(destination.label) },
                        )
                    }
                }
            }
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.ONBOARDING,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable(Routes.ONBOARDING) {
                val viewModel: OnboardingViewModel = viewModel(
                    factory = viewModelFactory {
                        OnboardingViewModel(appContainer.saveProfessionalProfile, appContainer.settingsRepository)
                    },
                )
                OnboardingScreen(
                    viewModel = viewModel,
                    onFinished = {
                        navController.navigate(Routes.DASHBOARD) {
                            popUpTo(Routes.ONBOARDING) { inclusive = true }
                        }
                    },
                )
            }
            composable(Routes.DASHBOARD) {
                val viewModel: DashboardViewModel = viewModel(
                    factory = viewModelFactory {
                        DashboardViewModel(
                            appContainer.getDailyAppointments,
                            appContainer.getCurrentPlan,
                            appContainer.getFinanceSummary,
                        )
                    },
                )
                DashboardScreen(
                    viewModel = viewModel,
                    onCreateAppointment = { navController.navigate(Routes.APPOINTMENT_FORM) },
                    onOpenPremium = { navController.navigate(Routes.PREMIUM) },
                    onOpenSettings = { navController.navigate(Routes.SETTINGS) },
                    onOpenFinance = { navController.navigate(Routes.FINANCE) },
                    onOpenStaff = { navController.navigate(Routes.STAFF) },
                )
            }
            composable(Routes.AGENDA) {
                val viewModel: AgendaViewModel = viewModel(
                    factory = viewModelFactory {
                        AgendaViewModel(
                            appContainer.getDailyAppointments,
                            appContainer.getActiveStaff,
                            appContainer.cancelAppointment,
                            appContainer.completeAppointment,
                        )
                    },
                )
                AgendaScreen(
                    viewModel = viewModel,
                    onCreateAppointment = { navController.navigate(Routes.APPOINTMENT_FORM) },
                    onEditAppointment = { id -> navController.navigate("${Routes.APPOINTMENT_EDIT}/$id") },
                )
            }
            composable("${Routes.APPOINTMENT_EDIT}/{appointmentId}") { entry ->
                val appointmentId = entry.arguments?.getString("appointmentId")?.toLongOrNull() ?: 0L
                val viewModel: AppointmentEditViewModel = viewModel(
                    factory = viewModelFactory {
                        AppointmentEditViewModel(
                            appointmentId = appointmentId,
                            getAppointment = appContainer.getAppointment,
                            updateAppointment = appContainer.updateAppointment,
                        )
                    },
                )
                AppointmentEditScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
            }
            composable(Routes.APPOINTMENT_FORM) {
                val viewModel: AppointmentFormViewModel = viewModel(
                    factory = viewModelFactory {
                        AppointmentFormViewModel(
                            appContainer.createAppointment,
                            appContainer.getDailyAppointments,
                            appContainer.getActiveStaff,
                            appContainer.searchClients,
                            appContainer.getActiveServices,
                        )
                    },
                )
                AppointmentFormScreen(
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() },
                    onOpenClients = { navController.navigate(Routes.CLIENTS) },
                    onOpenServices = { navController.navigate(Routes.SERVICES) },
                    onOpenStaff = { navController.navigate(Routes.STAFF) },
                )
            }
            composable("${Routes.APPOINTMENT_FORM_CLIENT}/{clientId}") { entry ->
                val clientId = entry.arguments?.getString("clientId")?.toLongOrNull()
                val viewModel: AppointmentFormViewModel = viewModel(
                    factory = viewModelFactory {
                        AppointmentFormViewModel(
                            appContainer.createAppointment,
                            appContainer.getDailyAppointments,
                            appContainer.getActiveStaff,
                            appContainer.searchClients,
                            appContainer.getActiveServices,
                            initialClientId = clientId,
                        )
                    },
                )
                AppointmentFormScreen(
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() },
                    onOpenClients = { navController.navigate(Routes.CLIENTS) },
                    onOpenServices = { navController.navigate(Routes.SERVICES) },
                    onOpenStaff = { navController.navigate(Routes.STAFF) },
                )
            }
            composable(Routes.CLIENTS) {
                val viewModel: ClientsViewModel = viewModel(
                    factory = viewModelFactory {
                        ClientsViewModel(appContainer.searchClients, appContainer.createClient, appContainer.deleteClient)
                    },
                )
                ClientsScreen(
                    viewModel = viewModel,
                    onOpenClient = { id -> navController.navigate("${Routes.CLIENT_DETAIL}/$id") },
                    onScheduleClient = { id -> navController.navigate("${Routes.APPOINTMENT_FORM_CLIENT}/$id") },
                )
            }
            composable("${Routes.CLIENT_DETAIL}/{clientId}") { entry ->
                val clientId = entry.arguments?.getString("clientId")?.toLongOrNull() ?: 0L
                val viewModel: ClientDetailViewModel = viewModel(
                    factory = viewModelFactory {
                        ClientDetailViewModel(
                            clientId = clientId,
                            getClient = appContainer.getClient,
                            getClientAppointments = appContainer.getClientAppointments,
                        )
                    },
                )
                ClientDetailScreen(
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() },
                    onScheduleAgain = { id -> navController.navigate("${Routes.APPOINTMENT_FORM_CLIENT}/$id") },
                )
            }
            composable(Routes.SERVICES) {
                val viewModel: ServicesViewModel = viewModel(
                    factory = viewModelFactory {
                        ServicesViewModel(appContainer.getAllServices, appContainer.createService, appContainer.deactivateService)
                    },
                )
                ServicesScreen(viewModel = viewModel)
            }
            composable(Routes.STAFF) {
                val viewModel: StaffViewModel = viewModel(
                    factory = viewModelFactory {
                        StaffViewModel(appContainer.getActiveStaff, appContainer.createStaffMember, appContainer.deactivateStaffMember)
                    },
                )
                StaffScreen(viewModel = viewModel)
            }
            composable(Routes.FINANCE) {
                val viewModel: FinanceViewModel = viewModel(
                    factory = viewModelFactory {
                        FinanceViewModel(appContainer.getFinanceEntries, appContainer.getFinanceSummary, appContainer.createManualExpense)
                    },
                )
                FinanceScreen(viewModel = viewModel)
            }
            composable(Routes.SETTINGS) {
                val viewModel: SettingsViewModel = viewModel(
                    factory = viewModelFactory {
                        SettingsViewModel(appContainer.getProfessionalProfile, appContainer.settingsRepository)
                    },
                )
                SettingsScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
            }
            composable(Routes.PREMIUM) {
                PremiumScreen(onBack = { navController.popBackStack() })
            }
        }
    }
}
