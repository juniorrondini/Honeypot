package com.agendaprobeauty.app.feature.premium

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.agendaprobeauty.app.domain.model.PlanType

@Composable
fun PremiumScreen(
    viewModel: PremiumViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val remaining = (state.freeMonthlyLimit - state.currentMonthAppointments).coerceAtLeast(0)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Text("Premium", style = MaterialTheme.typography.headlineSmall)
        Text("Controle real do limite gratis e desbloqueio local para testes de assinatura.", color = MaterialTheme.colorScheme.onSurfaceVariant)

        Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(if (state.type == PlanType.PREMIUM) "Plano Premium ativo" else "Plano Gratis ativo", style = MaterialTheme.typography.titleLarge)
                Text("${state.currentMonthAppointments}/${state.freeMonthlyLimit} agendamentos usados neste mes")
                Text(if (state.type == PlanType.PREMIUM) "Agenda ilimitada liberada." else "$remaining agendamentos restantes no gratis.")
            }
        }

        Card {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("O que o Premium entrega", style = MaterialTheme.typography.titleMedium)
                PremiumBenefit("Agendamentos ilimitados")
                PremiumBenefit("Relatorios completos de faturamento")
                PremiumBenefit("Backup em nuvem em fase futura")
                PremiumBenefit("Personalizacao da empresa")
                PremiumBenefit("Lembretes automaticos em fase futura")
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            Button(onClick = viewModel::activatePremiumLocally, modifier = Modifier.weight(1f)) {
                Text("Ativar teste")
            }
            OutlinedButton(onClick = viewModel::returnToFree, modifier = Modifier.weight(1f)) {
                Text("Voltar gratis")
            }
        }
        OutlinedButton(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
            Text("Voltar")
        }
    }
}

@Composable
private fun PremiumBenefit(text: String) {
    Text("- $text", color = MaterialTheme.colorScheme.onSurfaceVariant)
}
