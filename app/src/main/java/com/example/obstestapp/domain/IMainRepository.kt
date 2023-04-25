package com.example.obstestapp.domain

import com.example.obstestapp.data.AthleteDTO
import com.example.obstestapp.data.GamesDTO
import com.example.obstestapp.data.ResultsDTO
import kotlinx.coroutines.flow.Flow

interface IMainRepository {
    suspend fun getAthletesAtGame(gamesId: Int): List<AthleteDTO>

    suspend fun getAthlete(athleteId: Int): AthleteDTO

    suspend fun getAthleteResults(athleteId: Int): List<ResultsDTO>

    fun getAllGames(): Flow<Result<List<Games>>>

    suspend fun buildGamesList(gamesDTO: List<GamesDTO>): List<Games>

    suspend fun getAthleteDetailsFromRemote(athleteId: Int): Athlete

    suspend fun getAthleteDetails(athleteId: Int): Flow<Athlete>

    suspend fun getAllGamesFromRemote(): List<GamesDTO>
}
