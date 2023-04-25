package com.example.obstestapp.data

import com.example.obstestapp.data.*
import com.example.obstestapp.utils.executeApiCall
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiService: OBSService
) : IRemoteDataSource {
    override suspend fun getAllGames(): Result<List<GamesDTO>> {
        return executeApiCall { apiService.getAllGames() }
    }

    override suspend fun getAllAthletes(): Result<List<AthleteDTO>> {
        return executeApiCall { apiService.getAllAthletes() }
    }

    override suspend fun getAthlete(athleteId: Int): Result<AthleteDTO> {
        return executeApiCall { apiService.getAthlete(athleteId) }
    }

    override suspend fun getAthletesAtGames(gamesId: Int): Result<List<AthleteDTO>> {
        return executeApiCall { apiService.getAthletesAtGames(gamesId) }
    }

    override suspend fun getAthleteResults(athleteId: Int): Result<List<ResultsDTO>> {
        return executeApiCall { apiService.getAthleteResults(athleteId) }
    }
}
