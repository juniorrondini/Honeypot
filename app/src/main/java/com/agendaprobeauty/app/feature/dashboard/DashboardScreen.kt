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
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column {
                Text("Início", style = MaterialTheme.typography.headlineSmall)
                Text("Resumo do seu dia", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            IconButton(onClick = onOpenSettings) {
                Icon(Icons.Outlined.Settings, contentDescription = "Configurações")
            }
        }
        Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text("Faturamento hoje", style = MaterialTheme.typography.labelLarge)
                Text(MoneyUtils.format(state.revenueTodayCents), style = MaterialTheme.typography.headlineSmall)
            }
        }
        Text(
            "Agendamentos este mês: ${state.planStatus.currentMonthAppointments}/${state.planStatus.freeMonthlyLimit}",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Button(onClick = onCreateAppointment, modifier = Modifier.fillMaxWidth()) {
            Text("Novo agendamento")
        }
        OutlinedButton(onClick = onOpenPremium, modifier = Modifier.fillMaxWidth()) {
            Text("Ver plano premium")
        }
        Text("Próximos atendimentos", style = MaterialTheme.typography.titleMedium)
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(state.todayAppointments.take(5), key = { it.id }) { appointment ->
                Card {
                    Column(Modifier.padding(12.dp)) {
                        Text("${DateUtils.formatTime(appointment.startAt)} - ${appointment.clientNameSnapshot}")
                        Text(appointment.serviceNameSnapshot, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }
    }
}
