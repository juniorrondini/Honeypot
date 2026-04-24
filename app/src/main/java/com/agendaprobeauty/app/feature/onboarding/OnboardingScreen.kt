package com.agendaprobeauty.app.feature.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel,
    onFinished: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val isCompleted by viewModel.isCompleted.collectAsStateWithLifecycle()

    LaunchedEffect(isCompleted) {
        if (isCompleted) onFinished()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text("AgendaPro Beauty", style = MaterialTheme.typography.headlineMedium)
        Text(
            "Configure o perfil profissional para começar a controlar agenda, clientes e faturamento.",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        OutlinedTextField(
            value = state.name,
            onValueChange = viewModel::updateName,
            label = { Text("Seu nome") },
            modifier = Modifier.fillMaxWidth(),
        )
        OutlinedTextField(
            value = state.businessName,
            onValueChange = viewModel::updateBusinessName,
            label = { Text("Nome do negócio") },
            modifier = Modifier.fillMaxWidth(),
        )
        OutlinedTextField(
            value = state.phone,
            onValueChange = viewModel::updatePhone,
            label = { Text("Telefone") },
            modifier = Modifier.fillMaxWidth(),
        )
        OutlinedTextField(
            value = state.profession,
            onValueChange = viewModel::updateProfession,
            label = { Text("Profissão") },
            modifier = Modifier.fillMaxWidth(),
        )
        Button(
            onClick = { viewModel.save(onFinished) },
            enabled = state.name.isNotBlank() && state.profession.isNotBlank() && !state.isSaving,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Começar")
        }
    }
}
