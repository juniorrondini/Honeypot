package com.agendaprobeauty.app.feature.services

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agendaprobeauty.app.core.util.MoneyUtils
import com.agendaprobeauty.app.domain.model.BeautyService
import com.agendaprobeauty.app.domain.usecase.service.CreateServiceUseCase
import com.agendaprobeauty.app.domain.usecase.service.DeactivateServiceUseCase
import com.agendaprobeauty.app.domain.usecase.service.GetAllServicesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class ServicesUiState(
    val services: List<BeautyService> = emptyList(),
    val name: String = "",
    val price: String = "",
    val duration: String = "30",
)

class ServicesViewModel(
    getAllServices: GetAllServicesUseCase,
    private val createService: CreateServiceUseCase,
    private val deactivateService: DeactivateServiceUseCase,
) : ViewModel() {
    private val formState = MutableStateFlow(ServicesUiState())

    val state: StateFlow<ServicesUiState> = combine(formState, getAllServices()) { form, services ->
        form.copy(services = services)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), ServicesUiState())

    fun updateName(value: String) = update { copy(name = value) }
    fun updatePrice(value: String) = update { copy(price = value) }
    fun updateDuration(value: String) = update { copy(duration = value) }

    fun saveService() {
        val current = formState.value
        val duration = current.duration.toIntOrNull() ?: return
        if (current.name.isBlank() || duration <= 0) return
        viewModelScope.launch {
            createService(current.name, MoneyUtils.parseToCents(current.price), duration)
            update { copy(name = "", price = "", duration = "30") }
        }
    }

    fun deactivate(id: Long) {
        viewModelScope.launch { deactivateService(id) }
    }

    private fun update(block: ServicesUiState.() -> ServicesUiState) {
        formState.value = formState.value.block()
    }
}
