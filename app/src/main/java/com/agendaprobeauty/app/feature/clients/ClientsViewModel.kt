package com.agendaprobeauty.app.feature.clients

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agendaprobeauty.app.domain.model.Client
import com.agendaprobeauty.app.domain.usecase.client.CreateClientUseCase
import com.agendaprobeauty.app.domain.usecase.client.DeleteClientUseCase
import com.agendaprobeauty.app.domain.usecase.client.SearchClientsUseCase
import com.agendaprobeauty.app.domain.usecase.client.UpdateClientUseCase
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
    val editingClient: Client? = null,
)

@OptIn(ExperimentalCoroutinesApi::class)
class ClientsViewModel(
    private val searchClients: SearchClientsUseCase,
    private val createClient: CreateClientUseCase,
    private val updateClient: UpdateClientUseCase,
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
            val editing = current.editingClient
            if (editing == null) {
                createClient(current.name, current.phone, current.notes)
            } else {
                updateClient(
                    editing.copy(
                        name = current.name,
                        phone = current.phone.ifBlank { null },
                        notes = current.notes.ifBlank { null },
                    ),
                )
            }
            clearForm()
        }
    }

    fun startEditing(client: Client) {
        update {
            copy(
                name = client.name,
                phone = client.phone.orEmpty(),
                notes = client.notes.orEmpty(),
                editingClient = client,
            )
        }
    }

    fun clearForm() {
        update { copy(name = "", phone = "", notes = "", editingClient = null) }
    }

    fun delete(id: Long) {
        viewModelScope.launch { deleteClient(id) }
    }

    private fun update(block: ClientsUiState.() -> ClientsUiState) {
        formState.value = formState.value.block()
    }
}
