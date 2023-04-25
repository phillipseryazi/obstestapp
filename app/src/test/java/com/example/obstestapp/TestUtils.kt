package com.example.obstestapp

import com.example.obstestapp.data.AthleteDTO
import com.example.obstestapp.data.GamesDTO
import com.example.obstestapp.data.ResultsDTO
import com.example.obstestapp.utils.IDispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

fun getTestGames(): List<GamesDTO> {
    return listOf(
        GamesDTO(
            gameId = 1,
            city = "Test-city1",
            year = 2022
        ),
        GamesDTO(
            gameId = 2,
            city = "Test-city2",
            year = 2023
        )
    )
}

fun getTestAthletes(): List<AthleteDTO> {
    return listOf(
        AthleteDTO(
            athleteId = 1,
            name = "Test",
            surname = "Athlete1",
            dob = "04/08/1986",
            bio = "test bio 1",
            weight = 185,
            height = 190,
            photoId = 1
        ),
        AthleteDTO(
            athleteId = 2,
            name = "Test",
            surname = "Athlete2",
            dob = "04/04/1990",
            bio = "test bio 2",
            weight = 150,
            height = 186,
            photoId = 2
        )
    )
}

fun getTestAthlete(): AthleteDTO {
    return AthleteDTO(
        athleteId = 1,
        name = "Test",
        surname = "Athlete1",
        dob = "04/08/1986",
        bio = "test bio 1",
        weight = 185,
        height = 190,
        photoId = 1
    )
}

fun getTestAthleteResults(): List<ResultsDTO> {
    return listOf(
        ResultsDTO(
            city = "Test-city1",
            year = 2022,
            gold = 1,
            silver = 1,
            bronze = 1
        ),
        ResultsDTO(
            city = "Test-city2",
            year = 2023,
            gold = 1,
            silver = 1,
            bronze = 1
        ),
    )
}


@ExperimentalCoroutinesApi
class TestDispatcherProvider : IDispatcherProvider {

    private val testDispatcher = UnconfinedTestDispatcher()

    override val main: CoroutineDispatcher
        get() = testDispatcher

    override val io: CoroutineDispatcher
        get() = testDispatcher

    override val default: CoroutineDispatcher
        get() = testDispatcher

}

@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule(
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher(),
) : TestWatcher() {

    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}
