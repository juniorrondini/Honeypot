package com.agendaprobeauty.app.feature.clients

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agendaprobeauty.app.domain.model.Client
import com.agendaprobeauty.app.domain.usecase.client.CreateClientUseCase
import com.agendaprobeauty.app.domain.usecase.client.DeleteClientUseCase
import com.agendaprobeauty.app.domain.usecase.client.SearchClientsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class ClientsUiState(
    val query: String = "",
    val clients: List<Client> = emptyList(),
    val name: String = "",
    val phone: String = "",
    val notes: String = "",
)

@OptIn(ExperimentalCoroutinesApi::class)
class ClientsViewModel(
    private val searchClients: SearchClientsUseCase,
    private val createClient: CreateClientUseCase,
    private val deleteClient: DeleteClientUseCase,
) : ViewModel() {
    private val formState = MutableStateFlow(ClientsUiState())

    val state: StateFlow<ClientsUiState> = formState
        .flatMapLatest { form ->
            searchClients(form.query).map { clients -> form.copy(clients = clients) }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), ClientsUiState())

    fun updateQuery(value: String) = update { copy(query = value) }
    fun updateName(value: String) = update { copy(name = value) }
    fun updatePhone(value: String) = update { copy(phone = value) }
    fun updateNotes(value: String) = update { copy(notes = value) }

    fun saveClient() {
        val current = formState.value
        if (current.name.isBlank()) return
        viewModelScope.launch {
            createClient(current.name, current.phone, current.notes)
            update { copy(name = "", phone = "", notes = "") }
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch { deleteClient(id) }
    }

    private fun update(block: ClientsUiState.() -> ClientsUiState) {
        formState.value = formState.value.block()
    }
}
