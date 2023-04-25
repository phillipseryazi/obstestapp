package com.example.obstestapp.data

import com.example.obstestapp.domain.Athlete
import com.example.obstestapp.domain.Results
import com.google.gson.annotations.SerializedName

data class GamesDTO(
    @SerializedName("game_id")
    val gameId: Int,
    @SerializedName("city")
    val city: String,
    @SerializedName("year")
    val year: Int
)

data class AthleteDTO(
    @SerializedName("athlete_id")
    val athleteId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("surname")
    val surname: String,
    @SerializedName("dateOfBirth")
    val dob: String,
    @SerializedName("bio")
    val bio: String,
    @SerializedName("weight")
    val weight: Int,
    @SerializedName("height")
    val height: Int,
    @SerializedName("photo_id")
    val photoId: Int
)

data class ResultsDTO(
    @SerializedName("city")
    val city: String,
    @SerializedName("year")
    val year: Int,
    @SerializedName("gold")
    val gold: Int,
    @SerializedName("silver")
    val silver: Int,
    @SerializedName("bronze")
    val bronze: Int
)

fun AthleteDTO.toAthlete(results: List<ResultsDTO>): Athlete {
    return Athlete(
        athleteId = athleteId,
        name = name,
        surname = surname,
        dob = dob,
        bio = bio,
        weight = weight,
        height = height,
        photoId = photoId,
        results = results.map { it.toResults() }
    )
}

fun ResultsDTO.toResults(): Results {
    return Results(
        city = city,
        year = year,
        gold = gold,
        silver = silver,
        bronze = bronze,
        score = (gold * 5) + (silver * 3) + (bronze * 1)
    )
}
