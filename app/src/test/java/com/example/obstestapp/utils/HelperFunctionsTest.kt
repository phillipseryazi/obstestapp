package com.example.obstestapp.utils

import com.example.obstestapp.data.ResultsDTO
import com.example.obstestapp.data.toResults
import com.example.obstestapp.domain.Athlete
import com.example.obstestapp.domain.Games
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class HelperFunctionsTest {

    @Test
    fun `test sortGamesByYearAndAthleteScore`() {
        val games = listOf(
            Games(
                city = "Test-city1",
                year = 2022,
                athletes = listOf(
                    Athlete(
                        athleteId = 1,
                        name = "Test",
                        surname = "Athlete1",
                        dob = "04/08/1986",
                        bio = "test bio 1",
                        weight = 185,
                        height = 190,
                        photoId = 1,
                        results = listOf(
                            ResultsDTO(
                                city = "Test-city1",
                                year = 2022,
                                gold = 1,
                                silver = 1,
                                bronze = 1
                            ).toResults()
                        )
                    ),
                    Athlete(
                        athleteId = 2,
                        name = "Test2",
                        surname = "Athlete2",
                        dob = "04/08/1990",
                        bio = "test bio 2",
                        weight = 185,
                        height = 190,
                        photoId = 1,
                        results = listOf(
                            ResultsDTO(
                                city = "Test-city1",
                                year = 2022,
                                gold = 5,
                                silver = 1,
                                bronze = 1
                            ).toResults()
                        )
                    )
                )
            )
        )

        val sortedList = sortGamesByYearAndAthleteScore(games)
        assertThat(sortedList[0].athletes[0].name).isEqualTo("Test2")
        assertThat(sortedList[0].athletes[1].name).isEqualTo("Test")
    }
}
