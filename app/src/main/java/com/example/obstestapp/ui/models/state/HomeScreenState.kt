package com.example.obsapp.ui.models.state

import com.example.obstestapp.domain.Games

data class HomeScreenState(
    val games: List<Games> = emptyList(),
    val isLoading: Boolean = true,
    val showErrorCard: Boolean = false,
    val errorMessage: String = ""
)
