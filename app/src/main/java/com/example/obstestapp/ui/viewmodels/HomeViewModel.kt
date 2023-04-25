package com.example.obstestapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.obsapp.ui.models.state.HomeScreenState
import com.example.obstestapp.domain.Games
import com.example.obstestapp.domain.IMainRepository
import com.example.obstestapp.utils.IDispatcherProvider
import com.example.obstestapp.utils.sortGamesByYearAndAthleteScore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: IMainRepository,
    private val dispatcher: IDispatcherProvider
) : ViewModel() {
    var homeScreenState = MutableStateFlow(HomeScreenState())
        private set

    init {
        getAllGames()
    }

    private fun getAllGames() {
        viewModelScope.launch {
            repository.getAllGames()
                .flowOn(dispatcher.io)
                .catch { exc ->
                    homeScreenState.update { state ->
                        state.copy(
                            isLoading = false,
                            showErrorCard = true,
                            errorMessage = exc.message!!
                        )
                    }
                }
                .collect { result ->
                    result.onSuccess { gamesList ->
                        val sortedGames = sortGamesByYearAndAthleteScore(gamesList)
                        homeScreenState.update { state ->
                            state.copy(
                                games = sortedGames,
                                isLoading = false
                            )
                        }
                    }
                }
        }
    }

    private fun sortGamesByYearAndAthleteScore(games: List<Games>): List<Games> {
        return games.sortedByDescending { it.year }
            .map { game ->
                val athleteList = game.athletes.filter { athlete ->
                    athlete.results.any { it.year == game.year }
                }.sortedByDescending { athlete ->
                    athlete.results.find { it.year == game.year }?.score
                }
                game.copy(athletes = athleteList)
            }
    }
}
