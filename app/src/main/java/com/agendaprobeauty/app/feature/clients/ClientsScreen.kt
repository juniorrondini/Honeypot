package com.agendaprobeauty.app.feature.clients

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ClientsScreen(
    viewModel: ClientsViewModel,
    onOpenClient: (Long) -> Unit,
    onScheduleClient: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text("Clientes", style = MaterialTheme.typography.headlineSmall)
        Text("Clientes sao as pessoas atendidas pelos profissionais da empresa.", color = MaterialTheme.colorScheme.onSurfaceVariant)
        OutlinedTextField(
            value = state.query,
            onValueChange = viewModel::updateQuery,
            label = { Text("Buscar cliente") },
            modifier = Modifier.fillMaxWidth(),
        )
        OutlinedTextField(
            value = state.name,
            onValueChange = viewModel::updateName,
            label = { Text("Nome") },
            modifier = Modifier.fillMaxWidth(),
        )
        OutlinedTextField(
            value = state.phone,
            onValueChange = viewModel::updatePhone,
            label = { Text("Telefone") },
            modifier = Modifier.fillMaxWidth(),
        )
        OutlinedTextField(
            value = state.notes,
            onValueChange = viewModel::updateNotes,
            label = { Text("Observacoes") },
            modifier = Modifier.fillMaxWidth(),
        )
        Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh)) {
            Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    if (state.editingClient == null) "Novo cliente" else "Editando ${state.editingClient?.name.orEmpty()}",
                    style = MaterialTheme.typography.titleMedium,
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = viewModel::saveClient, modifier = Modifier.weight(1f)) {
                        Text(if (state.editingClient == null) "Salvar cliente" else "Salvar alteracoes")
                    }
                    if (state.editingClient != null) {
                        OutlinedButton(onClick = viewModel::clearForm, modifier = Modifier.weight(1f)) {
                            Text("Cancelar")
                        }
                    }
                }
            }
        }
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(state.clients, key = { it.id }) { client ->
                Card {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        Column {
                            Text(client.name, style = MaterialTheme.typography.titleMedium)
                            Text(client.phone.orEmpty(), color = MaterialTheme.colorScheme.onSurfaceVariant)
                            if (!client.notes.isNullOrBlank()) {
                                Text(client.notes, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                            OutlinedButton(onClick = { viewModel.startEditing(client) }, modifier = Modifier.weight(1f)) {
                                Text("Editar")
                            }
                            OutlinedButton(onClick = { onOpenClient(client.id) }, modifier = Modifier.weight(1f)) {
                                Text("Ver")
                            }
                            OutlinedButton(onClick = { onScheduleClient(client.id) }, modifier = Modifier.weight(1f)) {
                                Text("Agendar")
                            }
                        }
                    }
                }
            }
        }
    }
}
