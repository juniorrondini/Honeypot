package com.agendaprobeauty.app.feature.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.agendaprobeauty.app.core.util.DateUtils
import com.agendaprobeauty.app.core.util.MoneyUtils

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    onCreateAppointment: () -> Unit,
    onOpenPremium: () -> Unit,
    onOpenSettings: () -> Unit,
    onOpenFinance: () -> Unit,
    onOpenStaff: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column {
                    Text("AgendaPro Beauty", style = MaterialTheme.typography.headlineSmall)
                    Text("O que ganhou, quem atende e o que vem a seguir", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(if (state.isAdmin) "Modo Administrador" else "Modo Profissional", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                IconButton(onClick = onOpenSettings) {
                    Icon(Icons.Outlined.Settings, contentDescription = "Configuracoes")
                }
            }
        }
        item {
            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
                Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Faturamento", style = MaterialTheme.typography.labelLarge)
                    Text(MoneyUtils.format(state.revenueTodayCents), style = MaterialTheme.typography.headlineMedium)
                    Text("Hoje | ${MoneyUtils.format(state.revenueMonthCents)} no mes")
                    Text("${state.planStatus.currentMonthAppointments}/${state.planStatus.freeMonthlyLimit} agendamentos no plano gratis")
                }
            }
        }
        if (state.freeLimitWarning) {
            item {
                Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)) {
                    Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text("Limite gratis perto do fim", style = MaterialTheme.typography.titleMedium)
                        Text("Considere o Premium para agenda ilimitada.", color = MaterialTheme.colorScheme.onErrorContainer)
                        OutlinedButton(onClick = onOpenPremium, modifier = Modifier.fillMaxWidth()) { Text("Ver Premium") }
                    }
                }
            }
        }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                MetricCard("Hoje", "${state.scheduledToday} agenda", Modifier.weight(1f))
                MetricCard("Concluidos", state.completedToday.toString(), Modifier.weight(1f))
            }
        }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                MetricCard("Clientes", state.clients.size.toString(), Modifier.weight(1f))
                if (state.isAdmin) {
                    MetricCard("Equipe", state.staff.size.toString(), Modifier.weight(1f))
                    MetricCard("Servicos", state.services.size.toString(), Modifier.weight(1f))
                }
            }
        }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Button(onClick = onCreateAppointment, modifier = Modifier.weight(1f)) { Text("Agendar") }
                if (state.isAdmin) {
                    OutlinedButton(onClick = onOpenStaff, modifier = Modifier.weight(1f)) { Text("Equipe") }
                }
            }
        }
        if (state.isAdmin) {
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedButton(onClick = onOpenFinance, modifier = Modifier.weight(1f)) { Text("Financeiro") }
                    OutlinedButton(onClick = onOpenPremium, modifier = Modifier.weight(1f)) { Text("Premium") }
                }
            }
        }
        item {
            Text("Proximos atendimentos", style = MaterialTheme.typography.titleMedium)
        }
        if (state.nextAppointments.isEmpty()) {
            item {
                Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh)) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text("Sem proximos atendimentos hoje", style = MaterialTheme.typography.titleMedium)
                        Text("Use o botao Agendar para preencher a agenda.")
                    }
                }
            }
        } else {
            items(state.nextAppointments, key = { it.id }) { appointment ->
                Card {
                    Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("${DateUtils.formatTime(appointment.startAt)} | ${appointment.clientNameSnapshot}", style = MaterialTheme.typography.titleMedium)
                        Text("${appointment.staffMemberNameSnapshot} | ${appointment.serviceNameSnapshot}", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }
    }
}

@Composable
private fun MetricCard(label: String, value: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier, colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh)) {
        Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(value, style = MaterialTheme.typography.titleLarge)
            Text(label, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}
