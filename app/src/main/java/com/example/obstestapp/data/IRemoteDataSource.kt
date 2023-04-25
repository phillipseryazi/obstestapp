package com.example.obstestapp.data

interface IRemoteDataSource {
    suspend fun getAllGames(): Result<List<GamesDTO>>

    suspend fun getAllAthletes(): Result<List<AthleteDTO>>

    suspend fun getAthlete(athleteId: Int): Result<AthleteDTO>

    suspend fun getAthletesAtGames(gamesId: Int): Result<List<AthleteDTO>>

    suspend fun getAthleteResults(athleteId: Int): Result<List<ResultsDTO>>
}
