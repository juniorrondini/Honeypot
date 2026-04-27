package com.agendaprobeauty.app.feature.staff

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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

@Composable
fun StaffScreen(
    viewModel: StaffViewModel,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text("Equipe", style = MaterialTheme.typography.headlineSmall)
                Text(
                    "Cadastre quem atende e defina a janela de trabalho usada para calcular horarios livres.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
        item {
            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh)) {
                Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        if (state.editingStaffMember == null) "Novo profissional" else "Editando ${state.editingStaffMember?.name.orEmpty()}",
                        style = MaterialTheme.typography.titleMedium,
                    )
                    OutlinedTextField(
                        value = state.name,
                        onValueChange = viewModel::updateName,
                        label = { Text("Nome do profissional") },
                        modifier = Modifier.fillMaxWidth(),
                    )
                    OutlinedTextField(
                        value = state.role,
                        onValueChange = viewModel::updateRole,
                        label = { Text("Funcao") },
                        modifier = Modifier.fillMaxWidth(),
                    )
                    OutlinedTextField(
                        value = state.phone,
                        onValueChange = viewModel::updatePhone,
                        label = { Text("Telefone") },
                        modifier = Modifier.fillMaxWidth(),
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = state.startHour,
                            onValueChange = viewModel::updateStartHour,
                            label = { Text("Inicio") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f),
                        )
                        OutlinedTextField(
                            value = state.endHour,
                            onValueChange = viewModel::updateEndHour,
                            label = { Text("Fim") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f),
                        )
                        OutlinedTextField(
                            value = state.slotMinutes,
                            onValueChange = viewModel::updateSlotMinutes,
                            label = { Text("Slot") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f),
                        )
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                        Button(onClick = viewModel::save, modifier = Modifier.weight(1f)) {
                            Text(if (state.editingStaffMember == null) "Salvar profissional" else "Salvar alteracoes")
                        }
                        if (state.editingStaffMember != null) {
                            OutlinedButton(onClick = viewModel::clearForm, modifier = Modifier.weight(1f)) {
                                Text("Cancelar")
                            }
                        }
                    }
                }
            }
        }
        items(state.staff, key = { it.id }) { member ->
            Card {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    Column {
                        Text(member.name, style = MaterialTheme.typography.titleMedium)
                        Text(member.role, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(
                            "${member.workStartHour}:00 as ${member.workEndHour}:00 | slot ${member.slotMinutes} min",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                        if (!member.phone.isNullOrBlank()) {
                            Text(member.phone, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                        OutlinedButton(onClick = { viewModel.startEditing(member) }, modifier = Modifier.weight(1f)) {
                            Text("Editar")
                        }
                        OutlinedButton(onClick = { viewModel.deactivate(member.id) }, modifier = Modifier.weight(1f)) {
                            Text("Desativar")
                        }
                    }
                }
            }
        }
    }
}
