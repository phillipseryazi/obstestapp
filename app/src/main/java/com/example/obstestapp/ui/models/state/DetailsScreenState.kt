package com.example.obsapp.ui.models.state

import com.example.obstestapp.domain.Athlete

data class DetailsScreenState(
    val athlete: Athlete? = null,
    val isLoading: Boolean = true,
    val showErrorCard: Boolean = false,
    val errorMessage: String = ""
)
