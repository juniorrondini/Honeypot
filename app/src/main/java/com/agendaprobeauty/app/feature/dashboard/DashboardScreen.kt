package com.agendaprobeauty.app.feature.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column {
                    Text("AgendaPro Beauty", style = MaterialTheme.typography.headlineSmall)
                    Text("Empresa, equipe, clientes e agenda", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                IconButton(onClick = onOpenSettings) {
                    Icon(Icons.Outlined.Settings, contentDescription = "Configurações")
                }
            }
        }
        item {
            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text("Painel da empresa", style = MaterialTheme.typography.labelLarge)
                    Text(MoneyUtils.format(state.revenueTodayCents), style = MaterialTheme.typography.headlineMedium)
                    Text("Faturamento de hoje", color = MaterialTheme.colorScheme.onPrimaryContainer)
                    Text(
                        "Plano grátis: ${state.planStatus.currentMonthAppointments}/${state.planStatus.freeMonthlyLimit} agendamentos no mês",
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                }
            }
        }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Button(onClick = onCreateAppointment, modifier = Modifier.weight(1f)) {
                    Text("Agendar")
                }
                OutlinedButton(onClick = onOpenStaff, modifier = Modifier.weight(1f)) {
                    Text("Equipe")
                }
            }
        }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedButton(onClick = onOpenFinance, modifier = Modifier.weight(1f)) {
                    Text("Financeiro")
                }
                OutlinedButton(onClick = onOpenPremium, modifier = Modifier.weight(1f)) {
                    Text("Premium")
                }
            }
        }
        item {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("Atendimentos de hoje", style = MaterialTheme.typography.titleMedium)
                Text("Aqui aparecem os clientes agendados por profissional.", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        if (state.todayAppointments.isEmpty()) {
            item {
                Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh)) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text("Nenhum atendimento hoje", style = MaterialTheme.typography.titleMedium)
                        Text("Crie um agendamento escolhendo profissional, cliente, serviço e horário.", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        } else {
            items(state.todayAppointments.take(5), key = { it.id }) { appointment ->
                Card {
                    Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("${DateUtils.formatTime(appointment.startAt)} · ${appointment.clientNameSnapshot}", style = MaterialTheme.typography.titleMedium)
                        Text("Profissional: ${appointment.staffMemberNameSnapshot}", color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("Serviço: ${appointment.serviceNameSnapshot}", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }
    }
}
