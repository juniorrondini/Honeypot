package com.agendaprobeauty.app.feature.services

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
import com.agendaprobeauty.app.core.util.MoneyUtils

@Composable
fun ServicesScreen(
    viewModel: ServicesViewModel,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text("Servicos", style = MaterialTheme.typography.headlineSmall)
        Text("Defina o catalogo vendido pela empresa e usado nos horarios disponiveis.", color = MaterialTheme.colorScheme.onSurfaceVariant)
        OutlinedTextField(
            value = state.name,
            onValueChange = viewModel::updateName,
            label = { Text("Nome do servico") },
            modifier = Modifier.fillMaxWidth(),
        )
        OutlinedTextField(
            value = state.price,
            onValueChange = viewModel::updatePrice,
            label = { Text("Preco") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth(),
        )
        OutlinedTextField(
            value = state.duration,
            onValueChange = viewModel::updateDuration,
            label = { Text("Duracao em minutos") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            Button(onClick = viewModel::saveService, modifier = Modifier.weight(1f)) {
                Text(if (state.editingService == null) "Salvar servico" else "Salvar alteracoes")
            }
            if (state.editingService != null) {
                OutlinedButton(onClick = viewModel::clearForm, modifier = Modifier.weight(1f)) {
                    Text("Cancelar")
                }
            }
        }
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(state.services, key = { it.id }) { service ->
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = if (service.isActive) {
                            MaterialTheme.colorScheme.surface
                        } else {
                            MaterialTheme.colorScheme.surfaceContainerHigh
                        },
                    ),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        Column {
                            Text(service.name, style = MaterialTheme.typography.titleMedium)
                            Text(
                                "${MoneyUtils.format(service.priceCents)} | ${service.durationMinutes} min",
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                            Text(
                                if (service.isActive) "Ativo para novos agendamentos" else "Inativo",
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                            OutlinedButton(onClick = { viewModel.startEditing(service) }, modifier = Modifier.weight(1f)) {
                                Text("Editar")
                            }
                            if (service.isActive) {
                                OutlinedButton(onClick = { viewModel.deactivate(service.id) }, modifier = Modifier.weight(1f)) {
                                    Text("Desativar")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
