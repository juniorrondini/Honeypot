package com.agendaprobeauty.app.feature.agenda

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.agendaprobeauty.app.core.util.DateUtils
import com.agendaprobeauty.app.core.util.MoneyUtils
import com.agendaprobeauty.app.domain.model.Appointment
import com.agendaprobeauty.app.domain.model.AppointmentStatus

@Composable
@OptIn(ExperimentalLayoutApi::class)
fun AgendaScreen(
    viewModel: AgendaViewModel,
    onCreateAppointment: () -> Unit,
    onEditAppointment: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = modifier.padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        item {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("Agenda", style = MaterialTheme.typography.headlineSmall)
                Text(
                    "Linha do tempo por profissional, com horários livres e atendimentos do dia.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }

        item {
            if (state.staff.isEmpty()) {
                Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)) {
                    Text(
                        "Cadastre pelo menos um profissional em Equipe para usar a agenda.",
                        modifier = Modifier.padding(14.dp),
                        color = MaterialTheme.colorScheme.onErrorContainer,
                    )
                }
            } else {
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    state.staff.forEach { member ->
                        FilterChip(
                            selected = state.selectedStaffMemberId == member.id,
                            onClick = { viewModel.selectStaff(member.id) },
                            label = { Text(member.name) },
                        )
                    }
                }
            }
        }

        item {
            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh)) {
                Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        OutlinedButton(onClick = viewModel::previousDay) { Text("Anterior") }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(DateUtils.formatDate(state.selectedDate), style = MaterialTheme.typography.titleMedium)
                            Text(state.selectedStaff?.name.orEmpty(), color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        OutlinedButton(onClick = viewModel::nextDay) { Text("Proximo") }
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        MetricPill("Agendados", state.scheduledCount.toString())
                        MetricPill("Concluidos", state.completedCount.toString())
                        MetricPill("Cancelados", state.canceledCount.toString())
                    }
                    Button(onClick = onCreateAppointment, modifier = Modifier.fillMaxWidth()) {
                        Text("Novo agendamento")
                    }
                }
            }
        }

        if (state.timeline.isEmpty()) {
            item {
                Card {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text("Sem grade de horários", style = MaterialTheme.typography.titleMedium)
                        Text("Selecione ou cadastre um profissional com horário de trabalho definido.")
                    }
                }
            }
        } else {
            items(state.timeline, key = { it.time }) { item ->
                TimelineRow(
                    item = item,
                    onCreateAppointment = onCreateAppointment,
                    onEditAppointment = onEditAppointment,
                    onComplete = viewModel::complete,
                    onCancel = viewModel::cancel,
                )
            }
        }
    }
}

@Composable
private fun MetricPill(label: String, value: String) {
    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
        Column(Modifier.padding(horizontal = 10.dp, vertical = 8.dp)) {
            Text(value, style = MaterialTheme.typography.titleMedium)
            Text(label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun TimelineRow(
    item: AgendaTimelineItem,
    onCreateAppointment: () -> Unit,
    onEditAppointment: (Long) -> Unit,
    onComplete: (Long) -> Unit,
    onCancel: (Long) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(
            text = item.time,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier
                .padding(top = 14.dp)
                .weight(0.22f),
        )
        if (item.appointment == null) {
            EmptySlotCard(
                modifier = Modifier.weight(1f),
                onCreateAppointment = onCreateAppointment,
            )
        } else {
            AppointmentTimelineCard(
                appointment = item.appointment,
                modifier = Modifier.weight(1f),
                onEditAppointment = onEditAppointment,
                onComplete = onComplete,
                onCancel = onCancel,
            )
        }
    }
}

@Composable
private fun EmptySlotCard(
    onCreateAppointment: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text("Livre", color = MaterialTheme.colorScheme.onSurfaceVariant)
            OutlinedButton(onClick = onCreateAppointment) {
                Text("Agendar")
            }
        }
    }
}

@Composable
private fun AppointmentTimelineCard(
    appointment: Appointment,
    onEditAppointment: (Long) -> Unit,
    onComplete: (Long) -> Unit,
    onCancel: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    val statusColor = when (appointment.status) {
        AppointmentStatus.SCHEDULED -> MaterialTheme.colorScheme.primaryContainer
        AppointmentStatus.COMPLETED -> Color(0xFFDFF3E3)
        AppointmentStatus.CANCELED -> MaterialTheme.colorScheme.errorContainer
        AppointmentStatus.NO_SHOW -> Color(0xFFFFE8C7)
    }
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = statusColor),
    ) {
        Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column {
                    Text(appointment.clientNameSnapshot, style = MaterialTheme.typography.titleMedium)
                    Text(
                        "${appointment.serviceNameSnapshot} · ${MoneyUtils.format(appointment.priceCents)}",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
                StatusChip(appointment.status)
            }
            Text(
                "${DateUtils.formatTime(appointment.startAt)} - ${DateUtils.formatTime(appointment.endAt)}",
                style = MaterialTheme.typography.bodyMedium,
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(onClick = { onEditAppointment(appointment.id) }) { Text("Editar") }
                if (appointment.status == AppointmentStatus.SCHEDULED) {
                    OutlinedButton(onClick = { onComplete(appointment.id) }) { Text("Concluir") }
                    OutlinedButton(onClick = { onCancel(appointment.id) }) { Text("Cancelar") }
                }
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
