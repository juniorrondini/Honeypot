package com.agendaprobeauty.app.feature.clientdetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.agendaprobeauty.app.core.util.DateUtils
import com.agendaprobeauty.app.core.util.MoneyUtils
import com.agendaprobeauty.app.domain.model.Appointment
import com.agendaprobeauty.app.domain.model.AppointmentStatus

@Composable
fun ClientDetailScreen(
    viewModel: ClientDetailViewModel,
    onBack: () -> Unit,
    onScheduleAgain: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val client = state.client

    LazyColumn(
        modifier = modifier.padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        item {
            if (client == null) {
                Text("Cliente nao encontrado.", color = MaterialTheme.colorScheme.error)
                OutlinedButton(onClick = onBack, modifier = Modifier.fillMaxWidth()) { Text("Voltar") }
                return@item
            }
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(client.name, style = MaterialTheme.typography.headlineSmall)
                Text(client.phone.orEmpty().ifBlank { "Sem telefone" }, color = MaterialTheme.colorScheme.onSurfaceVariant)
                client.notes?.takeIf { it.isNotBlank() }?.let {
                    Text(it, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }

        if (client != null) {
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    MetricCard("Gasto total", MoneyUtils.format(state.totalSpentCents), Modifier.weight(1f))
                    MetricCard("Concluidos", state.completedCount.toString(), Modifier.weight(1f))
                }
            }
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    MetricCard("Cancelados", state.canceledCount.toString(), Modifier.weight(1f))
                    MetricCard("Ultima visita", state.lastVisitAt?.let(DateUtils::formatDateTime) ?: "-", Modifier.weight(1f))
                }
            }
            item {
                Button(onClick = { onScheduleAgain(client.id) }, modifier = Modifier.fillMaxWidth()) {
                    Text("Agendar novamente")
                }
                OutlinedButton(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
                    Text("Voltar")
                }
            }
            item {
                Text("Historico", style = MaterialTheme.typography.titleMedium)
            }
            if (state.appointments.isEmpty()) {
                item {
                    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh)) {
                        Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text("Nenhum atendimento ainda", style = MaterialTheme.typography.titleMedium)
                            Text("Use o botao agendar novamente para criar o primeiro atendimento deste cliente.")
                        }
                    }
                }
            } else {
                items(state.appointments, key = { it.id }) { appointment ->
                    ClientAppointmentCard(appointment = appointment)
                }
            }
        }
    }
}

@Composable
private fun MetricCard(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh),
    ) {
        Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(value, style = MaterialTheme.typography.titleLarge)
            Text(label, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun ClientAppointmentCard(appointment: Appointment) {
    Card {
        Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text(appointment.serviceNameSnapshot, style = MaterialTheme.typography.titleMedium)
                    Text(appointment.staffMemberNameSnapshot, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                StatusChip(appointment.status)
            }
            Text(DateUtils.formatDateTime(appointment.startAt), color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(MoneyUtils.format(appointment.priceCents))
            appointment.notes?.takeIf { it.isNotBlank() }?.let {
                Text(it, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

@Composable
private fun StatusChip(status: AppointmentStatus) {
    val label = when (status) {
        AppointmentStatus.SCHEDULED -> "Agendado"
        AppointmentStatus.COMPLETED -> "Concluido"
        AppointmentStatus.CANCELED -> "Cancelado"
        AppointmentStatus.NO_SHOW -> "Faltou"
    }
    SuggestionChip(onClick = {}, label = { Text(label) })
}
