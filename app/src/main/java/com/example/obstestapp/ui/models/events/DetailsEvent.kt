package com.example.obsapp.ui.models.events

sealed class DetailsEvent {
    class GetUserDetails(val athleteId: Int) : DetailsEvent()
}
