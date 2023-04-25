package com.example.obstestapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.obsapp.ui.models.state.HomeScreenState
import com.example.obstestapp.R
import com.example.obstestapp.domain.Athlete
import com.example.obstestapp.domain.Games
import com.example.obstestapp.ui.elements.ErrorCard
import com.example.obstestapp.ui.elements.OBSNavbar
import com.example.obstestapp.ui.elements.OBSText
import com.example.obstestapp.ui.theme.OBSTestAppTheme
import com.example.obstestapp.utils.BASE_URL
import com.example.obstestapp.utils.Tags

@Composable
fun HomeScreen(
    isLandScape: Boolean,
    state: HomeScreenState,
    navigateToDetails: (athleteId: Int) -> Unit = {}
) {
    val sortedGamesList = state.games.sortedByDescending { it.year }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        OBSNavbar(
            modifier = Modifier.testTag(Tags.NAVBAR_TEST_TAG),
            heading = {
                OBSText(
                    text = stringResource(id = R.string.olympics_atheletes),
                    style = MaterialTheme.typography.h5,
                    color = Color.White
                )
            },
            navigateUp = {}
        )
        if (isLandScape) {
            HomeScreenLandscapeLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .testTag(Tags.HOME_SCREEN_LANDSCAPE_TAG),
                state = state,
                sortedGamesList = sortedGamesList,
                navigateToDetails = navigateToDetails
            )
        } else {
            HomeScreenPortraitLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .testTag(Tags.HOME_SCREEN_PORTRAIT_TAG),
                state = state,
                sortedGamesList = sortedGamesList,
                navigateToDetails = navigateToDetails
            )
        }
    }
}

@Composable
fun HomeScreenPortraitLayout(
    modifier: Modifier = Modifier,
    state: HomeScreenState,
    sortedGamesList: List<Games>,
    navigateToDetails: (athleteId: Int) -> Unit
) {
    Box(modifier = modifier) {
        when {
            state.showErrorCard -> {
                ErrorCard(
                    modifier = Modifier.align(Alignment.Center),
                    message = state.errorMessage
                )
            }
            state.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.Center)
                        .testTag(Tags.HOME_SCREEN_LOADER_TAG),
                    color = MaterialTheme.colors.primary
                )
            }
            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    sortedGamesList.forEach { games ->
                        item {
                            HomeScreenHeader(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag(Tags.HOME_SCREEN_SECTION_HEADER_TAG),
                                arrangement = Arrangement.Start,
                                city = games.city,
                                year = games.year
                            )
                        }
                        item {
                            val athleteList = games.athletes.filter { athlete ->
                                athlete.results.any { it.year == games.year }
                            }.sortedByDescending { athlete ->
                                athlete.results.find { it.year == games.year }?.score
                            }
                            LazyRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag(
                                        Tags.HOME_SCREEN_LAZY_ROW_TAG
                                    ),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(items = athleteList) { athlete ->
                                    CircularImageCard(
                                        modifier = Modifier
                                            .size(width = 150.dp, height = 200.dp)
                                            .clickable {
                                                navigateToDetails(athlete.athleteId)
                                            }
                                            .testTag(Tags.CIRCULAR_IMAGE_CARD_TAG),
                                        athlete = athlete
                                    )
                                }
                            }
                        }
                    }
                    item {
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(12.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreenLandscapeLayout(
    modifier: Modifier = Modifier,
    state: HomeScreenState,
    sortedGamesList: List<Games>,
    navigateToDetails: (athleteId: Int) -> Unit
) {
    Box(modifier = modifier) {
        when {
            state.showErrorCard -> {
                ErrorCard(
                    modifier = Modifier.align(Alignment.Center),
                    message = state.errorMessage
                )
            }
            state.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.Center),
                    color = MaterialTheme.colors.primary
                )
            }
            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    sortedGamesList.forEach { games ->
                        item {
                            HomeScreenHeader(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag(Tags.HOME_SCREEN_SECTION_HEADER_TAG),
                                arrangement = Arrangement.Center,
                                city = games.city,
                                year = games.year
                            )
                        }
                        item {
                            val athleteList = games.athletes.filter { athlete ->
                                athlete.results.any { it.year == games.year }
                            }.sortedByDescending { athlete ->
                                athlete.results.find { it.year == games.year }?.score
                            }
                            LazyHorizontalGrid(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(max = 200.dp)
                                    .testTag(Tags.HOME_SCREEN_GRID_TAG),
                                rows = GridCells.Adaptive(200.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                items(athleteList.size) { idx ->
                                    CircularImageCard(
                                        modifier = Modifier
                                            .size(width = 150.dp, height = 200.dp)
                                            .clickable {
                                                navigateToDetails(athleteList[idx].athleteId)
                                            }
                                            .testTag(Tags.CIRCULAR_IMAGE_CARD_TAG),
                                        athlete = athleteList[idx]
                                    )
                                }
                            }
                        }
                    }
                    item {
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(12.dp)
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun CircularImageCard(
    modifier: Modifier = Modifier,
    athlete: Athlete
) {
    Card(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(
                modifier = Modifier
                    .height(10.dp)
                    .fillMaxWidth()
            )
            AsyncImage(
                model = "$BASE_URL/athletes/${athlete.photoId}/photo",
                placeholder = painterResource(com.example.obstestapp.R.drawable.img_placeholder),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .border(
                        width = 1.dp,
                        shape = CircleShape,
                        brush = Brush.linearGradient(
                            listOf(MaterialTheme.colors.primary, MaterialTheme.colors.primary)
                        )
                    )
            )
            Spacer(
                modifier = Modifier
                    .height(6.dp)
                    .fillMaxWidth()
            )
            OBSText(
                text = "${athlete.name} ${athlete.surname}",
                style = MaterialTheme.typography.body1,
                color = Color.Black
            )
            Spacer(
                modifier = Modifier
                    .height(10.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun HomeScreenHeader(
    modifier: Modifier = Modifier,
    arrangement: Arrangement.Horizontal,
    city: String,
    year: Int
) {
    Row(
        modifier = modifier,
        horizontalArrangement = arrangement
    ) {
        Spacer(modifier = Modifier.width(8.dp))
        OBSText(
            text = "$city $year",
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    OBSTestAppTheme {
        HomeScreen(
            isLandScape = false,
            state = HomeScreenState(),
            navigateToDetails = {}
        )
    }
}
