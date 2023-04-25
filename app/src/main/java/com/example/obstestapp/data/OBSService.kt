package com.example.obstestapp.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface OBSService {

    @GET("/games")
    suspend fun getAllGames(): Response<List<GamesDTO>>

    @GET("/athletes")
    suspend fun getAllAthletes(): Response<List<AthleteDTO>>

    @GET("/athletes/{id}")
    suspend fun getAthlete(@Path(value = "id") athleteId: Int): Response<AthleteDTO>

    @GET("/games/{id}/athletes")
    suspend fun getAthletesAtGames(@Path(value = "id") gamesId: Int): Response<List<AthleteDTO>>

    @GET("/athletes/{id}/results")
    suspend fun getAthleteResults(@Path(value = "id") athleteId: Int): Response<List<ResultsDTO>>
}
