package com.example.obstestapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.obstestapp.ui.screens.DetailsScreen
import com.example.obstestapp.ui.screens.HomeScreen
import com.example.obstestapp.ui.viewmodels.DetailsViewModel
import com.example.obstestapp.ui.viewmodels.HomeViewModel

@Composable
fun NavigationComposable(isLandScape: Boolean, navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Destination.HomeScreen.route,
        builder = {
            composable(Destination.HomeScreen.route) {
                val viewModel = hiltViewModel<HomeViewModel>()
                HomeScreen(
                    isLandScape = isLandScape,
                    state = viewModel.homeScreenState.collectAsStateWithLifecycle().value,
                    handleEvent = viewModel::handleEvent,
                    navigateToDetails = { athleteId ->
                        navController.navigate("${Destination.DetailsScreen.route}/$athleteId")
                    }
                )
            }
            composable(
                route = "${Destination.DetailsScreen.route}/{athleteId}",
                arguments = listOf(navArgument(name = "athleteId") {
                    type = NavType.IntType
                })
            ) { navEntry ->
                val viewModel = hiltViewModel<DetailsViewModel>()
                DetailsScreen(
                    isLandScape = isLandScape,
                    athleteId = navEntry.arguments?.getInt("athleteId"),
                    state = viewModel.detailsScreenState.collectAsStateWithLifecycle().value,
                    handleEvent = viewModel::handleEvent,
                    navigateToHome = {
                        navController.popBackStack()
                    }
                )
            }
        }
    )
}
