package com.example.obstestapp.screens


import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import com.example.obsapp.ui.models.state.HomeScreenState
import com.example.obstestapp.R
import com.example.obstestapp.domain.Games
import com.example.obstestapp.getListOfAthletes
import com.example.obstestapp.ui.screens.HomeScreen
import com.example.obstestapp.utils.Tags
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun navbar_Is_Present() {
        composeTestRule.setContent {
            HomeScreen(
                isLandScape = false,
                state = HomeScreenState()
            )
        }
        composeTestRule.onNodeWithText(
            InstrumentationRegistry.getInstrumentation().targetContext
                .getString(R.string.olympics_atheletes)
        ).assertIsDisplayed()
    }

    @Test
    fun loader_Is_Showing_On_Start() {
        composeTestRule.setContent {
            HomeScreen(
                isLandScape = false,
                state = HomeScreenState()
            )
        }
        composeTestRule.onNodeWithTag(Tags.HOME_SCREEN_LOADER_TAG).assertIsDisplayed()
    }

    @Test
    fun homeScreen_Is_Portrait() {
        composeTestRule.setContent {
            HomeScreen(
                isLandScape = false,
                state = HomeScreenState()
            )
        }
        composeTestRule.onNodeWithTag(Tags.HOME_SCREEN_PORTRAIT_TAG).assertIsDisplayed()
    }

    @Test
    fun homeScreen_Is_Landscape() {
        composeTestRule.setContent {
            HomeScreen(
                isLandScape = true,
                state = HomeScreenState()
            )
        }
        composeTestRule.onNodeWithTag(Tags.HOME_SCREEN_LANDSCAPE_TAG).assertIsDisplayed()
    }

    @Test
    fun olympics_Header_Shows_In_Landscape() {
        composeTestRule.setContent {
            HomeScreen(
                isLandScape = true,
                state = HomeScreenState(
                    isLoading = false,
                    games = listOf(
                        Games(
                            "Test-city",
                            2023,
                            getListOfAthletes()
                        )
                    )
                )
            )
        }
        composeTestRule.onNodeWithText("Test-city 2023").assertIsDisplayed()
    }

    @Test
    fun olympics_Header_Shows_In_Portrait() {
        composeTestRule.setContent {
            HomeScreen(
                isLandScape = false,
                state = HomeScreenState(
                    isLoading = false,
                    games = listOf(
                        Games(
                            "Test-city",
                            2023,
                            getListOfAthletes()
                        )
                    )
                )
            )
        }
        composeTestRule.onNodeWithText("Test-city 2023").assertIsDisplayed()
    }

    @Test
    fun homeScreen_Row_Has_Multiple_Items() {
        composeTestRule.setContent {
            HomeScreen(
                isLandScape = false,
                state = HomeScreenState(
                    isLoading = false,
                    games = listOf(
                        Games(
                            "Test-city",
                            2023,
                            getListOfAthletes()
                        )
                    )
                )
            )
        }
        composeTestRule.onAllNodesWithTag(Tags.CIRCULAR_IMAGE_CARD_TAG).assertCountEquals(2)
    }
}
