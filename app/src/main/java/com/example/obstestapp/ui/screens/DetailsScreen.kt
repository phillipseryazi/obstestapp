package com.example.obstestapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.obsapp.ui.models.events.DetailsEvent
import com.example.obsapp.ui.models.state.DetailsScreenState
import com.example.obstestapp.R
import com.example.obstestapp.ui.elements.*
import com.example.obstestapp.ui.theme.OBSTestAppTheme
import com.example.obstestapp.utils.BASE_URL
import com.example.obstestapp.utils.Tags
import com.mukesh.MarkDown


@Composable
fun DetailsScreen(
    isLandScape: Boolean,
    athleteId: Int?,
    state: DetailsScreenState,
    handleEvent: (event: DetailsEvent) -> Unit = {},
    navigateToHome: () -> Unit = {}
) {
    LaunchedEffect(key1 = null, block = {
        athleteId?.let {
            handleEvent(DetailsEvent.GetUserDetails(it))
        }
    })
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        OBSNavbar(
            modifier = Modifier.testTag(Tags.NAVBAR_TEST_TAG),
            heading = {
                OBSText(
                    text = "${state.athlete?.name ?: stringResource(R.string.text_athlete)} ${
                        state.athlete?.surname ?: stringResource(
                            R.string.text_details
                        )
                    }",
                    style = MaterialTheme.typography.h5,
                    color = Color.White
                )
            },
            showNavIcon = true,
            navigateUp = navigateToHome
        )
        if (isLandScape) {
            DetailsScreenLandscapeLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .testTag(Tags.DETAILS_SCREEN_LANDSCAPE_TAG),
                state = state
            )
        } else {
            DetailsScreenPortraitLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .testTag(Tags.DETAILS_SCREEN_PORTRAIT_TAG),
                state = state
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DetailsScreenPortraitLayout(
    modifier: Modifier = Modifier,
    state: DetailsScreenState
) {
    Box(modifier = modifier) {
        when {
            state.showErrorCard -> {
                ErrorCard(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .testTag(Tags.DETAILS_SCREEN_ERROR_CARD_TAG),
                    message = state.errorMessage
                )
            }
            state.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.Center)
                        .testTag(Tags.DETAILS_SCREEN_LOADER_TAG),
                    color = MaterialTheme.colors.primary
                )
            }
            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    DetailsScreenImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .testTag(Tags.DETAILS_SCREEN_IMAGE_TAG),
                        state = state
                    )
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(16.dp)
                    )
                    FlowRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp)
                    ) {
                        AthleteInfoRow(
                            key = stringResource(R.string.text_name),
                            value = "${state.athlete?.name} ${state.athlete?.surname} "
                        )
                        AthleteInfoRow(
                            key = stringResource(R.string.text_dob),
                            value = "${state.athlete?.dob} "
                        )
                        AthleteInfoRow(
                            key = stringResource(R.string.text_weight),
                            value = "${state.athlete?.height}cm "
                        )
                        AthleteInfoRow(
                            key = stringResource(R.string.text_height),
                            value = "${state.athlete?.weight}kg "
                        )
                    }
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(16.dp)
                    )
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Spacer(modifier = Modifier.width(16.dp))
                        OBSText(
                            text = stringResource(R.string.text_medals),
                            style = MaterialTheme.typography.h6,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                    state.athlete?.results?.forEach { results ->
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(2.dp)
                        )
                        AthleteResultsRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp)
                                .testTag(Tags.DETAILS_SCREEN_ATHLETE_RESULTS_ROW_TAG),
                            results = results
                        )
                    }
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(16.dp)
                    )
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Spacer(modifier = Modifier.width(16.dp))
                        OBSText(
                            text = stringResource(R.string.text_bio),
                            style = MaterialTheme.typography.h6,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                    )
                    state.athlete?.let {
                        MarkDown(
                            modifier = Modifier
                                .padding(start = 16.dp, end = 16.dp)
                                .testTag(Tags.DETAILS_SCREEN_MARKDOWN_TAG),
                            text = it.bio,
                            shouldOpenUrlInBrowser = true
                        )
                    }
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DetailsScreenLandscapeLayout(
    modifier: Modifier = Modifier,
    state: DetailsScreenState
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
                        .testTag(Tags.DETAILS_SCREEN_LOADER_TAG),
                    color = MaterialTheme.colors.primary
                )
            }
            else -> {
                Row(modifier = Modifier.fillMaxSize()) {
                    Card(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(300.dp)
                            .padding(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.weight(2f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(16.dp)
                            )
                            DetailsScreenImage(
                                modifier = Modifier
                                    .padding(start = 16.dp)
                                    .size(150.dp)
                                    .clip(CircleShape)
                                    .border(
                                        width = 1.dp,
                                        shape = CircleShape,
                                        brush = Brush.linearGradient(
                                            listOf(
                                                MaterialTheme.colors.primary,
                                                MaterialTheme.colors.primary
                                            )
                                        )
                                    )
                                    .testTag(Tags.DETAILS_SCREEN_IMAGE_TAG),
                                state = state
                            )
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(8.dp)
                            )
                            FlowRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 16.dp),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                AthleteInfoRow(
                                    key = stringResource(R.string.text_name),
                                    value = "${state.athlete?.name} ${state.athlete?.surname} "
                                )
                                AthleteInfoRow(
                                    key = stringResource(R.string.text_dob),
                                    value = "${state.athlete?.dob} "
                                )
                                AthleteInfoRow(
                                    key = stringResource(R.string.text_weight),
                                    value = "${state.athlete?.height}cm "
                                )
                                AthleteInfoRow(
                                    key = stringResource(R.string.text_height),
                                    value = "${state.athlete?.weight}kg "
                                )
                            }

                        }
                    }
                    Column(
                        modifier = Modifier
                            .weight(3f)
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(16.dp)
                        )
                        Row(modifier = Modifier.fillMaxWidth()) {
                            DetailsHeading(
                                heading = stringResource(R.string.text_medals)
                            )
                        }
                        state.athlete?.results?.forEach { results ->
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(2.dp)
                            )
                            AthleteResultsRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 8.dp, end = 8.dp),
                                results = results
                            )
                        }
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(16.dp)
                        )
                        Row(modifier = Modifier.fillMaxWidth()) {
                            DetailsHeading(
                                heading = stringResource(R.string.text_bio)
                            )
                        }
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                        )
                        state.athlete?.let {
                            MarkDown(
                                modifier = Modifier
                                    .padding(start = 8.dp, end = 8.dp)
                                    .testTag(Tags.DETAILS_SCREEN_MARKDOWN_TAG),
                                text = it.bio,
                                shouldOpenUrlInBrowser = true
                            )
                        }
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DetailsScreenImage(
    modifier: Modifier = Modifier,
    state: DetailsScreenState
) {
    AsyncImage(
        modifier = modifier,
        model = "$BASE_URL/athletes/${state.athlete?.photoId}/photo",
        placeholder = painterResource(R.drawable.img_placeholder),
        contentDescription = null,
        contentScale = ContentScale.Crop
    )
}

@Composable
fun DetailsHeading(
    heading: String
) {
    OBSText(
        text = heading,
        style = MaterialTheme.typography.h6,
        fontWeight = FontWeight.Bold,
        color = Color.Black
    )
}


@Preview
@Composable
fun DetailsScreenPreview() {
    OBSTestAppTheme {
        DetailsScreen(
            isLandScape = false,
            athleteId = 1,
            state = DetailsScreenState(isLoading = false),
            handleEvent = {},
            navigateToHome = {}
        )
    }
}
