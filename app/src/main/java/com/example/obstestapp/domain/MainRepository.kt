package com.example.obstestapp.domain

import com.example.obstestapp.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val remoteDataSource: IRemoteDataSource,
) : IMainRepository {

    override fun getAllGames(): Flow<Result<List<Games>>> = flow {
        try {
            val gamesEntities = getAllGamesFromRemote()
            val games = buildGamesList(gamesEntities)
            emit(Result.success(games))
        } catch (exc: Exception) {
            emit(Result.failure(exc))
        }
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

    override suspend fun getAthletesAtGame(gamesId: Int): List<AthleteDTO> {
        return remoteDataSource.getAthletesAtGames(gamesId).getOrThrow()
    }

    override suspend fun getAthlete(athleteId: Int): AthleteDTO {
        return remoteDataSource.getAthlete(athleteId).getOrThrow()
    }

    override suspend fun getAthleteResults(athleteId: Int): List<ResultsDTO> {
        return remoteDataSource.getAthleteResults(athleteId).getOrThrow()
    }

    override suspend fun getAllGamesFromRemote(): List<GamesDTO> {
        return remoteDataSource.getAllGames().getOrThrow()
    }

    override suspend fun getAthleteDetailsFromRemote(athleteId: Int): Athlete {
        val athlete = withContext(Dispatchers.IO) {
            getAthlete(athleteId)
        }

        val results = withContext(Dispatchers.IO) {
            getAthleteResults(athleteId)
        }

        return athlete.toAthlete(results)
    }

    override suspend fun getAthleteDetails(athleteId: Int): Flow<Athlete> = flow {
        emit(getAthleteDetailsFromRemote(athleteId))
    }

}

