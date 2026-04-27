package com.agendaprobeauty.app.feature.appointmentform

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
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
import com.agendaprobeauty.app.core.util.MoneyUtils

@Composable
@OptIn(ExperimentalLayoutApi::class)
fun AppointmentFormScreen(
    viewModel: AppointmentFormViewModel,
    onBack: () -> Unit,
    onOpenClients: () -> Unit,
    onOpenServices: () -> Unit,
    onOpenStaff: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = modifier.padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        item {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("Novo agendamento", style = MaterialTheme.typography.headlineSmall)
                Text(
                    "Monte o atendimento usando os cadastros da empresa.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }

        item {
            FormSection(title = "1. Profissional") {
                if (state.staff.isEmpty()) {
                    EmptyRequirement(
                        message = "Cadastre quem atende antes de criar a agenda.",
                        action = "Cadastrar profissional",
                        onAction = onOpenStaff,
                    )
                } else {
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        state.staff.forEach { member ->
                            FilterChip(
                                selected = state.selectedStaffMemberId == member.id,
                                onClick = { viewModel.selectStaff(member.id) },
                                label = { Text("${member.name} | ${member.role}") },
                            )
                        }
                    }
                }
            }
        }

        item {
            FormSection(title = "2. Cliente") {
                if (state.clients.isEmpty()) {
                    EmptyRequirement(
                        message = "Cadastre o cliente uma vez e reutilize em todos os atendimentos.",
                        action = "Cadastrar cliente",
                        onAction = onOpenClients,
                    )
                } else {
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        state.clients.take(12).forEach { client ->
                            FilterChip(
                                selected = state.selectedClientId == client.id,
                                onClick = { viewModel.selectClient(client.id) },
                                label = { Text(client.name) },
                            )
                        }
                    }
                }
            }
        }

        item {
            FormSection(title = "3. Servico") {
                if (state.services.isEmpty()) {
                    EmptyRequirement(
                        message = "Cadastre servicos com preco e duracao para calcular a agenda corretamente.",
                        action = "Cadastrar servico",
                        onAction = onOpenServices,
                    )
                } else {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        state.services.forEach { service ->
                            FilterChip(
                                selected = state.selectedServiceId == service.id,
                                onClick = { viewModel.selectService(service.id) },
                                label = {
                                    Text("${service.name} | ${MoneyUtils.format(service.priceCents)} | ${service.durationMinutes} min")
                                },
                            )
                        }
                    }
                }
            }
        }

        item {
            FormSection(title = "4. Data e horario") {
                OutlinedTextField(
                    value = state.date,
                    onValueChange = viewModel::updateDate,
                    label = { Text("Data yyyy-mm-dd") },
                    modifier = Modifier.fillMaxWidth(),
                )
                if (state.availableSlots.isEmpty()) {
                    Text(
                        "Nenhum horario livre para a combinacao atual.",
                        color = MaterialTheme.colorScheme.error,
                    )
                } else {
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        state.availableSlots.forEach { slot ->
                            FilterChip(
                                selected = state.time == slot,
                                onClick = { viewModel.selectTime(slot) },
                                label = { Text(slot) },
                            )
                        }
                    }
                }
            }
        }

        item {
            FormSection(title = "5. Confirmacao") {
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
                OutlinedTextField(
                    value = state.notes,
                    onValueChange = viewModel::updateNotes,
                    label = { Text("Observacoes") },
                    modifier = Modifier.fillMaxWidth(),
                )
                AppointmentSummary(state = state)
                state.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                Button(
                    onClick = { viewModel.save(onBack) },
                    enabled = !state.isSaving,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text("Salvar agendamento")
                }
                OutlinedButton(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
                    Text("Cancelar")
                }
            }
        }
    }
}

@Composable
private fun FormSection(
    title: String,
    content: @Composable ColumnScope.() -> Unit,
) {
    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            content = {
                Text(title, style = MaterialTheme.typography.titleMedium)
                content()
            },
        )
    }
}

@Composable
private fun EmptyRequirement(
    message: String,
    action: String,
    onAction: () -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(message, color = MaterialTheme.colorScheme.onSurfaceVariant)
        OutlinedButton(onClick = onAction, modifier = Modifier.fillMaxWidth()) {
            Text(action)
        }
    }
}

@Composable
private fun AppointmentSummary(state: AppointmentFormUiState) {
    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
        Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text("Resumo", style = MaterialTheme.typography.titleSmall)
            Text("Profissional: ${state.selectedStaff?.name ?: "-"}")
            Text("Cliente: ${state.selectedClient?.name ?: "-"}")
            Text("Servico: ${state.selectedService?.name ?: "-"}")
            Text("Quando: ${state.date} as ${state.time.ifBlank { "-" }}")
        }
    }
}
