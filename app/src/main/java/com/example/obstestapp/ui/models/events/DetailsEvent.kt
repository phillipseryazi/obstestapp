package com.example.obstestapp.ui.models.events

sealed class DetailsEvent {
    class GetUserDetails(val athleteId: Int) : DetailsEvent()
}
