package com.example.obstestapp.domain

import com.example.obstestapp.fakes.FakeRemoteDataSource
import com.example.obstestapp.getTestGames
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainRepositoryTest {
    private val remoteDataSource = FakeRemoteDataSource()
    lateinit var repository: IMainRepository

    @Before
    fun setUp() {
        repository = MainRepository(remoteDataSource)
    }


    @Test
    fun `test all games`() = runTest {
        repository.getAllGames().collect {
            assertThat(it.getOrThrow()).hasSize(2)
        }
    }

    @Test
    fun `test build games list`() = runTest {
        val games = repository.buildGamesList(getTestGames())
        assertThat(games).hasSize(2)
        assertThat(games[0]).isInstanceOf(Games::class.java)
    }

    @Test
    fun `test getAthleteDetails`() = runTest {
        repository.getAthleteDetails(1).collect {
            assertThat(it.surname).isEqualTo("Athlete1")
            assertThat(it).isInstanceOf(Athlete::class.java)
        }
    }
}
