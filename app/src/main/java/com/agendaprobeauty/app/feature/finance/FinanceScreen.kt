package com.agendaprobeauty.app.feature.finance

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.agendaprobeauty.app.core.util.DateUtils
import com.agendaprobeauty.app.core.util.MoneyUtils

@Composable
fun FinanceScreen(
    viewModel: FinanceViewModel,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text("Financeiro", style = MaterialTheme.typography.headlineSmall)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Card(modifier = Modifier.weight(1f)) {
                Column(Modifier.padding(12.dp)) {
                    Text("Hoje", style = MaterialTheme.typography.labelLarge)
                    Text(MoneyUtils.format(state.daySummary.balanceCents), style = MaterialTheme.typography.titleLarge)
                }
            }
            Card(modifier = Modifier.weight(1f)) {
                Column(Modifier.padding(12.dp)) {
                    Text("Mês", style = MaterialTheme.typography.labelLarge)
                    Text(MoneyUtils.format(state.monthSummary.balanceCents), style = MaterialTheme.typography.titleLarge)
                }
            }
        }
        OutlinedTextField(
            value = state.expenseDescription,
            onValueChange = viewModel::updateExpenseDescription,
            label = { Text("Despesa") },
            modifier = Modifier.fillMaxWidth(),
        )
        OutlinedTextField(
            value = state.expenseAmount,
            onValueChange = viewModel::updateExpenseAmount,
            label = { Text("Valor") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth(),
        )
        Button(onClick = viewModel::addExpense, modifier = Modifier.fillMaxWidth()) {
            Text("Adicionar despesa")
        }
        Text("Movimentações de hoje", style = MaterialTheme.typography.titleMedium)
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(state.entries, key = { it.id }) { entry ->
                Card {
                    Column(Modifier.padding(12.dp)) {
                        Text(entry.description)
                        Text("${entry.type} · ${MoneyUtils.format(entry.amountCents)} · ${DateUtils.formatTime(entry.occurredAt)}")
                    }
                }
            }
        }
    }
}
