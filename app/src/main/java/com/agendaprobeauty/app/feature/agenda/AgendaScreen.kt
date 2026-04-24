package com.agendaprobeauty.app.feature.agenda

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
fun AgendaScreen(
    viewModel: AgendaViewModel,
    onCreateAppointment: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text("Agenda", style = MaterialTheme.typography.headlineSmall)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            OutlinedButton(onClick = viewModel::previousDay) { Text("Anterior") }
            Text(DateUtils.formatDate(state.selectedDate), style = MaterialTheme.typography.titleMedium)
            OutlinedButton(onClick = viewModel::nextDay) { Text("Próximo") }
        }
        Button(onClick = onCreateAppointment, modifier = Modifier.fillMaxWidth()) {
            Text("Criar agendamento")
        }
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(state.appointments, key = { it.id }) { appointment ->
                Card {
                    Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text("${DateUtils.formatTime(appointment.startAt)} - ${appointment.clientNameSnapshot}")
                        Text("${appointment.serviceNameSnapshot} · ${MoneyUtils.format(appointment.priceCents)}")
                        Text("Status: ${appointment.status}", color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedButton(onClick = { viewModel.complete(appointment.id) }) { Text("Concluir") }
                            OutlinedButton(onClick = { viewModel.cancel(appointment.id) }) { Text("Cancelar") }
                        }
                    }
                }
            }
        }
    }
}
