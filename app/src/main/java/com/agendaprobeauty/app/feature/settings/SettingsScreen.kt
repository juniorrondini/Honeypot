package com.agendaprobeauty.app.feature.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.agendaprobeauty.app.domain.model.UserMode

@Composable
@OptIn(ExperimentalLayoutApi::class)
fun SettingsScreen(
    viewModel: SettingsViewModel,
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
        Text("Configuracoes", style = MaterialTheme.typography.headlineSmall)
        Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh)) {
            Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text("Empresa", style = MaterialTheme.typography.titleMedium)
                Text("Profissional: ${state.professional?.name.orEmpty()}")
                Text("Negocio: ${state.professional?.businessName.orEmpty()}")
                Text("Funcao: ${state.professional?.profession.orEmpty()}")
                Text("Onboarding concluido: ${if (state.onboardingCompleted) "sim" else "nao"}")
            }
        }
        Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh)) {
            Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("Modo de uso", style = MaterialTheme.typography.titleMedium)
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    FilterChip(
                        selected = state.userMode == UserMode.ADMIN,
                        onClick = { viewModel.setUserMode(UserMode.ADMIN) },
                        label = { Text("Administrador") },
                    )
                    FilterChip(
                        selected = state.userMode == UserMode.PROFESSIONAL,
                        onClick = { viewModel.setUserMode(UserMode.PROFESSIONAL) },
                        label = { Text("Profissional") },
                    )
                }
                Text(
                    if (state.userMode == UserMode.ADMIN) {
                        "Administrador ve empresa, equipe, financeiro, premium e configuracoes."
                    } else {
                        "Profissional foca na propria agenda. Restricoes completas entram quando houver login."
                    },
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
        OutlinedButton(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
            Text("Voltar")
        }
    }
}
