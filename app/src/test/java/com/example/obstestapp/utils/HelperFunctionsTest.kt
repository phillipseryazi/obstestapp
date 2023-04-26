package com.example.obstestapp.utils

import com.example.obstestapp.data.ResultsDTO
import com.example.obstestapp.data.toResults
import com.example.obstestapp.domain.Athlete
import com.example.obstestapp.domain.Games
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.UnknownHostException

class HelperFunctionsTest {

    private val successfulResponse = Response.success("test")
    private val failedResponse = Response.error<String>(
        404,
        okhttp3.ResponseBody.create(null, "Not Found")
    )

    @Test
    fun `test sortGamesByYearAndAthleteScore success`() {
        val games = listOf(
            Games(
                city = "Test-city1",
                year = 2022,
                athletes = listOf(
                    Athlete(
                        athleteId = 1,
                        name = "Test",
                        surname = "Athlete1",
                        dob = "04/08/1986",
                        bio = "test bio 1",
                        weight = 185,
                        height = 190,
                        photoId = 1,
                        results = listOf(
                            ResultsDTO(
                                city = "Test-city1",
                                year = 2022,
                                gold = 1,
                                silver = 1,
                                bronze = 1
                            ).toResults()
                        )
                    ),
                    Athlete(
                        athleteId = 2,
                        name = "Test2",
                        surname = "Athlete2",
                        dob = "04/08/1990",
                        bio = "test bio 2",
                        weight = 185,
                        height = 190,
                        photoId = 1,
                        results = listOf(
                            ResultsDTO(
                                city = "Test-city1",
                                year = 2022,
                                gold = 5,
                                silver = 1,
                                bronze = 1
                            ).toResults()
                        )
                    )
                )
            )
        )

        val sortedList = sortGamesByYearAndAthleteScore(games)
        assertThat(sortedList[0].athletes[0].name).isEqualTo("Test2")
        assertThat(sortedList[0].athletes[1].name).isEqualTo("Test")
    }

    @Test
    fun `test executeApiCall with successful response`() = runBlocking {
        val result = executeApiCall { successfulResponse }
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isEqualTo("test")
    }

    @Test
    fun `test executeApiCall with failed response`() = runBlocking {
        val result = executeApiCall { failedResponse }
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()).isInstanceOf(IOException::class.java)
    }

    @Test
    fun `test executeApiCall with HttpException`() = runBlocking {
        val exception = executeApiCall<HttpException> { throw HttpException(failedResponse) }
        assertThat(exception.exceptionOrNull()).isInstanceOf(HttpException::class.java)
    }

    @Test
    fun `test executeApiCall with IOException`() = runBlocking {
        val exception = executeApiCall<IOException> { throw IOException("Test") }
        assertThat(exception.exceptionOrNull()).isInstanceOf(IOException::class.java)
    }

    @Test
    fun `test executeApiCall with UnknownHostException`() = runBlocking {
        val exception = executeApiCall<UnknownHostException> { throw UnknownHostException("test") }
        assertThat(exception.exceptionOrNull()).isInstanceOf(UnknownHostException::class.java)
    }

    @Test
    fun `test executeApiCall with NullPointerException`() = runBlocking {
        val exception = executeApiCall<NullPointerException> { throw NullPointerException("test") }
        assertThat(exception.exceptionOrNull()).isInstanceOf(NullPointerException::class.java)
    }
}

