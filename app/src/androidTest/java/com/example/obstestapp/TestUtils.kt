package com.example.obstestapp

import com.example.obstestapp.domain.Athlete
import com.example.obstestapp.domain.Results

fun getListOfAthletes(): List<Athlete> {
    return listOf(
        Athlete(
            1,
            "Tester",
            "User",
            "08/04/1990",
            "# test bio",
            200,
            180,
            1,
            listOf(
                Results("Test-city", 2023, 1, 1, 1, 3)
            )
        ),
        Athlete(
            2,
            "Tester2",
            "User2",
            "08/04/1995",
            "# test bio2",
            50,
            175,
            2,
            listOf(
                Results("Test-city", 2023, 2, 2, 2, 3)
            )
        )
    )
}
