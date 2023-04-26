package com.example.obstestapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.obstestapp.ui.models.events.DetailsEvent
import com.example.obstestapp.ui.models.state.DetailsScreenState
import com.example.obstestapp.domain.IMainRepository
import com.example.obstestapp.utils.IDispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: IMainRepository,
    private val dispatcher: IDispatcherProvider
) : ViewModel() {
    var detailsScreenState = MutableStateFlow(DetailsScreenState())
        private set

    fun handleEvent(event: DetailsEvent) {
        when (event) {
            is DetailsEvent.GetUserDetails -> {
                getAthleteDetails(event.athleteId)
            }
        }
    }

    private fun getAthleteDetails(athleteId: Int) {
        viewModelScope.launch {
            repository.getAthleteDetails(athleteId)
                .flowOn(dispatcher.io)
                .catch { exc ->
                    detailsScreenState.update { state ->
                        state.copy(
                            isLoading = false,
                            showErrorCard = true,
                            errorMessage = exc.message ?: ""
                        )
                    }
                }
                .collect { athlete ->
                    detailsScreenState.update { state ->
                        state.copy(
                            athlete = athlete,
                            isLoading = false,
                        )
                    }
                }
        }
    }
}
