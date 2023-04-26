package com.example.obstestapp.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.example.obstestapp.ui.models.state.DetailsScreenState
import com.example.obstestapp.getListOfAthletes
import com.example.obstestapp.ui.screens.DetailsScreen
import com.example.obstestapp.utils.Tags
import org.junit.Rule
import org.junit.Test

class DetailsScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun navbar_Is_Present() {
        composeTestRule.setContent {
            DetailsScreen(
                isLandScape = false,
                athleteId = 1,
                state = DetailsScreenState()
            )
        }
        composeTestRule.onNodeWithTag(Tags.NAVBAR_TEST_TAG).assertIsDisplayed()
    }

    @Test
    fun loader_Is_Showing_On_Start() {
        composeTestRule.setContent {
            DetailsScreen(
                isLandScape = false,
                athleteId = 1,
                state = DetailsScreenState()
            )
        }
        composeTestRule.onNodeWithTag(Tags.DETAILS_SCREEN_LOADER_TAG).assertIsDisplayed()
    }

    @Test
    fun detailsScreen_Is_Portrait() {
        composeTestRule.setContent {
            DetailsScreen(
                isLandScape = false,
                athleteId = 1,
                state = DetailsScreenState()
            )
        }
        composeTestRule.onNodeWithTag(Tags.DETAILS_SCREEN_PORTRAIT_TAG).assertIsDisplayed()
    }

    @Test
    fun detailsScreen_Is_Landscape() {
        composeTestRule.setContent {
            DetailsScreen(
                isLandScape = true,
                athleteId = 1,
                state = DetailsScreenState()
            )
        }
        composeTestRule.onNodeWithTag(Tags.DETAILS_SCREEN_LANDSCAPE_TAG).assertIsDisplayed()
    }

    @Test
    fun detailsScreen_Results_Are_Shown() {
        composeTestRule.setContent {
            DetailsScreen(
                isLandScape = false,
                athleteId = 1,
                state = DetailsScreenState(
                    isLoading = false,
                    athlete = getListOfAthletes()[0]
                )
            )
        }
        composeTestRule.onNodeWithTag(Tags.DETAILS_SCREEN_ATHLETE_RESULTS_ROW_TAG)
            .assertIsDisplayed()
    }

    @Test
    fun detailsScreen_Image_Is_Shown() {
        composeTestRule.setContent {
            DetailsScreen(
                isLandScape = false,
                athleteId = 1,
                state = DetailsScreenState(
                    isLoading = false,
                    athlete = getListOfAthletes()[0]
                )
            )
        }
        composeTestRule.onNodeWithTag(Tags.DETAILS_SCREEN_IMAGE_TAG)
            .assertIsDisplayed()
    }
}
