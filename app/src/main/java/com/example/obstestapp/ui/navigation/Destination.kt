package com.example.obstestapp.ui.navigation

sealed class Destination(val route: String) {
    object HomeScreen : Destination(HOME_SCREEN)
    object DetailsScreen : Destination(DETAILS_SCREEN)

    companion object {
        private const val HOME_SCREEN = "home_screen"
        private const val DETAILS_SCREEN = "details_screen"
    }
}