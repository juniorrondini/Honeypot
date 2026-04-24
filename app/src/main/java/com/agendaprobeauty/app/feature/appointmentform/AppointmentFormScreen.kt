package com.agendaprobeauty.app.feature.appointmentform

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
@OptIn(ExperimentalLayoutApi::class)
fun AppointmentFormScreen(
    viewModel: AppointmentFormViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text("Novo agendamento", style = MaterialTheme.typography.headlineSmall)
        Text("Selecione profissional, cliente, serviço e horário.", color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text("Profissional", style = MaterialTheme.typography.titleMedium)
        if (state.staff.isEmpty()) {
            Text("Cadastre um profissional na tela Equipe antes de agendar.", color = MaterialTheme.colorScheme.error)
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
        OutlinedTextField(
            value = state.clientName,
            onValueChange = viewModel::updateClientName,
            label = { Text("Cliente") },
            modifier = Modifier.fillMaxWidth(),
        )
        OutlinedTextField(
            value = state.serviceName,
            onValueChange = viewModel::updateServiceName,
            label = { Text("Serviço") },
            modifier = Modifier.fillMaxWidth(),
        )
        OutlinedTextField(
            value = state.price,
            onValueChange = viewModel::updatePrice,
            label = { Text("Preço") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth(),
        )
        OutlinedTextField(
            value = state.duration,
            onValueChange = viewModel::updateDuration,
            label = { Text("Duração em minutos") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
        )
        OutlinedTextField(
            value = state.date,
            onValueChange = viewModel::updateDate,
            label = { Text("Data yyyy-mm-dd") },
            modifier = Modifier.fillMaxWidth(),
        )
        OutlinedTextField(
            value = state.time,
            onValueChange = viewModel::updateTime,
            label = { Text("Horário HH:mm") },
            modifier = Modifier.fillMaxWidth(),
        )
        if (state.availableSlots.isNotEmpty()) {
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                state.availableSlots.take(10).forEach { slot ->
                    FilterChip(
                        selected = state.time == slot,
                        onClick = { viewModel.selectTime(slot) },
                        label = { Text(slot) },
                    )
                }
            }
        }
        OutlinedTextField(
            value = state.notes,
            onValueChange = viewModel::updateNotes,
            label = { Text("Observações") },
            modifier = Modifier.fillMaxWidth(),
        )
        state.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
        Button(
            onClick = { viewModel.save(onBack) },
            enabled = !state.isSaving,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Salvar")
        }
        OutlinedButton(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
            Text("Cancelar")
        }
    }
}
