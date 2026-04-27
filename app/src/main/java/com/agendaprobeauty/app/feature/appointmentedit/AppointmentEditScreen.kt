package com.agendaprobeauty.app.feature.appointmentedit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.agendaprobeauty.app.domain.model.AppointmentStatus

@Composable
@OptIn(ExperimentalLayoutApi::class)
fun AppointmentEditScreen(
    viewModel: AppointmentEditViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val appointment = state.appointment

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Text("Editar atendimento", style = MaterialTheme.typography.headlineSmall)
        if (appointment == null) {
            Text("Atendimento nao encontrado.", color = MaterialTheme.colorScheme.error)
            OutlinedButton(onClick = onBack, modifier = Modifier.fillMaxWidth()) { Text("Voltar") }
            return@Column
        }

        Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh)) {
            Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(appointment.clientNameSnapshot, style = MaterialTheme.typography.titleMedium)
                Text(appointment.serviceNameSnapshot, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("Profissional: ${appointment.staffMemberNameSnapshot}", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            OutlinedTextField(
                value = state.date,
                onValueChange = viewModel::updateDate,
                label = { Text("Data") },
                modifier = Modifier.weight(1f),
            )
            OutlinedTextField(
                value = state.time,
                onValueChange = viewModel::updateTime,
                label = { Text("Hora") },
                modifier = Modifier.weight(1f),
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            OutlinedTextField(
                value = state.price,
                onValueChange = viewModel::updatePrice,
                label = { Text("Preco") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.weight(1f),
            )
            OutlinedTextField(
                value = state.duration,
                onValueChange = viewModel::updateDuration,
                label = { Text("Min") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f),
            )
        }

        Text("Status", style = MaterialTheme.typography.titleMedium)
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            AppointmentStatus.entries.forEach { status ->
                FilterChip(
                    selected = state.status == status,
                    onClick = { viewModel.updateStatus(status) },
                    label = { Text(status.label()) },
                )
            }
        }

        OutlinedTextField(
            value = state.notes,
            onValueChange = viewModel::updateNotes,
            label = { Text("Observacoes") },
            modifier = Modifier.fillMaxWidth(),
        )

        state.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }

        Button(
            onClick = { viewModel.save(onBack) },
            enabled = !state.isSaving,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Salvar alteracoes")
        }
        OutlinedButton(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
            Text("Cancelar")
        }
    }
}

private fun AppointmentStatus.label(): String = when (this) {
    AppointmentStatus.SCHEDULED -> "Agendado"
    AppointmentStatus.COMPLETED -> "Concluido"
    AppointmentStatus.CANCELED -> "Cancelado"
    AppointmentStatus.NO_SHOW -> "Faltou"
}
