package com.agendaprobeauty.app.feature.finance

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.agendaprobeauty.app.domain.model.FinancialType

@Composable
fun FinanceScreen(
    viewModel: FinanceViewModel,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = modifier.padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("Financeiro", style = MaterialTheme.typography.headlineSmall)
                Text("Receitas, despesas e saldo estimado do negocio.", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                MetricCard("Receita mes", MoneyUtils.format(state.monthSummary.incomeCents), Modifier.weight(1f))
                MetricCard("Despesas", MoneyUtils.format(state.monthSummary.expenseCents), Modifier.weight(1f))
            }
        }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                MetricCard("Saldo mes", MoneyUtils.format(state.monthSummary.balanceCents), Modifier.weight(1f))
                MetricCard("Hoje", MoneyUtils.format(state.daySummary.balanceCents), Modifier.weight(1f))
            }
        }
        item {
            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh)) {
                Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Adicionar despesa", style = MaterialTheme.typography.titleMedium)
                    OutlinedTextField(
                        value = state.expenseDescription,
                        onValueChange = viewModel::updateExpenseDescription,
                        label = { Text("Descricao") },
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
                }
            }
        }
        item { Text("Receita por servico", style = MaterialTheme.typography.titleMedium) }
        if (state.incomeGroups.isEmpty()) {
            item { EmptyCard("Nenhuma receita concluida neste mes.") }
        } else {
            items(state.incomeGroups, key = { it.label }) { group -> GroupCard(group) }
        }
        item { Text("Despesas por categoria", style = MaterialTheme.typography.titleMedium) }
        if (state.expenseGroups.isEmpty()) {
            item { EmptyCard("Nenhuma despesa lancada neste mes.") }
        } else {
            items(state.expenseGroups, key = { it.label }) { group -> GroupCard(group) }
        }
        item { Text("Movimentacoes do mes", style = MaterialTheme.typography.titleMedium) }
        if (state.entries.isEmpty()) {
            item { EmptyCard("Nenhuma movimentacao encontrada.") }
        } else {
            items(state.entries, key = { it.id }) { entry ->
                Card {
                    Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(entry.description, style = MaterialTheme.typography.titleMedium)
                        Text("${entry.type.label()} | ${MoneyUtils.format(entry.amountCents)} | ${DateUtils.formatDateTime(entry.occurredAt)}")
                    }
                }
            }
        }
    }
}

@Composable
private fun MetricCard(label: String, value: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier, colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh)) {
        Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(value, style = MaterialTheme.typography.titleLarge)
            Text(label, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun GroupCard(group: FinanceGroup) {
    Card {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(group.label)
            Text(MoneyUtils.format(group.amountCents), style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Composable
private fun EmptyCard(text: String) {
    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh)) {
        Text(text, modifier = Modifier.padding(14.dp), color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

private fun FinancialType.label(): String = when (this) {
    FinancialType.INCOME -> "Receita"
    FinancialType.EXPENSE -> "Despesa"
}
