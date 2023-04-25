package com.example.obstestapp.fakes

import com.example.obstestapp.data.*
import com.example.obstestapp.domain.Athlete
import com.example.obstestapp.domain.Games
import com.example.obstestapp.domain.IMainRepository
import com.example.obstestapp.getTestAthlete
import com.example.obstestapp.getTestGames
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeMainRepository(private val dataSource: IRemoteDataSource) : IMainRepository {
    override suspend fun getAthletesAtGame(gamesId: Int): List<AthleteDTO> {
        return dataSource.getAthletesAtGames(gamesId).getOrThrow()
    }

    override suspend fun getAthlete(athleteId: Int): AthleteDTO {
        return dataSource.getAthlete(athleteId).getOrThrow()
    }

    override suspend fun getAthleteResults(athleteId: Int): List<ResultsDTO> {
        return dataSource.getAthleteResults(athleteId).getOrThrow()
    }

    override fun getAllGames(): Flow<Result<List<Games>>> = flow {
        emit(Result.success(buildGamesList(getTestGames())))
    }

    override suspend fun buildGamesList(gamesDTO: List<GamesDTO>): List<Games> {
        return gamesDTO.mapNotNull { gameEntity ->
            val athletes = getAthletesAtGame(gameEntity.gameId)
                .map { athleteDTO ->
                    getAthleteDetailsFromRemote(athleteDTO.athleteId)
                }

            if (athletes.isEmpty()) null else Games(
                city = gameEntity.city,
                year = gameEntity.year,
                athletes = athletes
            )
        }
    }

    override suspend fun getAthleteDetailsFromRemote(athleteId: Int): Athlete {
        return getTestAthlete().toAthlete(getAthleteResults(athleteId))
    }

    override suspend fun getAthleteDetails(athleteId: Int): Flow<Athlete> = flow {
        emit(getAthleteDetailsFromRemote(athleteId))
    }

    override suspend fun getAllGamesFromRemote(): List<GamesDTO> {
        return dataSource.getAllGames().getOrThrow()
    }
}
