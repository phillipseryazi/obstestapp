package com.example.obstestapp.fakes

import com.example.obstestapp.data.AthleteDTO
import com.example.obstestapp.data.GamesDTO
import com.example.obstestapp.data.IRemoteDataSource
import com.example.obstestapp.data.ResultsDTO
import com.example.obstestapp.getTestAthlete
import com.example.obstestapp.getTestAthleteResults
import com.example.obstestapp.getTestAthletes
import com.example.obstestapp.getTestGames

class FakeRemoteDataSource : IRemoteDataSource {
    override suspend fun getAllGames(): Result<List<GamesDTO>> {
        return Result.success(getTestGames())
    }

    override suspend fun getAllAthletes(): Result<List<AthleteDTO>> {
        return Result.success(getTestAthletes())
    }

    override suspend fun getAthlete(athleteId: Int): Result<AthleteDTO> {
        return Result.success(getTestAthlete())
    }

    override suspend fun getAthletesAtGames(gamesId: Int): Result<List<AthleteDTO>> {
        return Result.success(getTestAthletes())
    }

    override suspend fun getAthleteResults(athleteId: Int): Result<List<ResultsDTO>> {
        return Result.success(getTestAthleteResults())
    }
}
