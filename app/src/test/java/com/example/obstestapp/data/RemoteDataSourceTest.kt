package com.example.obstestapp.data

import com.example.obstestapp.getTestAthlete
import com.example.obstestapp.getTestAthleteResults
import com.example.obstestapp.getTestAthletes
import com.example.obstestapp.getTestGames
import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

@OptIn(ExperimentalCoroutinesApi::class)
class RemoteDataSourceTest {
    private lateinit var apiService: OBSService
    private lateinit var mockWebServer: MockWebServer
    private lateinit var remoteDataSource: RemoteDataSource

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()

        mockWebServer.start()

        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/").toString())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OBSService::class.java)

        remoteDataSource = RemoteDataSource(apiService)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `return available games success`() = runTest {
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(Gson().toJson(getTestGames()))
        mockWebServer.enqueue(expectedResponse)

        val actualResponse = remoteDataSource.getAllGames()
        assertThat(actualResponse.getOrThrow()).hasSize(2)
    }

    @Test
    fun `return available games failed`() = runTest {
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_UNAVAILABLE)
            .setBody(Gson().toJson(getTestGames()))
        mockWebServer.enqueue(expectedResponse)

        val actualResponse = remoteDataSource.getAllGames()
        assertThat(actualResponse.isFailure)
    }

    @Test
    fun `return available athletes success`() = runTest {
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(Gson().toJson(getTestAthletes()))
        mockWebServer.enqueue(expectedResponse)

        val actualResponse = remoteDataSource.getAllAthletes()
        assertThat(actualResponse.getOrThrow()).hasSize(2)
    }

    @Test
    fun `return available athletes failed`() = runTest {
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_UNAVAILABLE)
            .setBody(Gson().toJson(getTestAthletes()))
        mockWebServer.enqueue(expectedResponse)

        val actualResponse = remoteDataSource.getAllAthletes()
        assertThat(actualResponse.isFailure)
    }

    @Test
    fun `get athlete success`() = runTest {
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(Gson().toJson(getTestAthlete()))
        mockWebServer.enqueue(expectedResponse)

        val actualResponse = remoteDataSource.getAthlete(1)
        assertThat(actualResponse.getOrThrow()).isInstanceOf(AthleteDTO::class.java)
        assertThat(actualResponse.getOrThrow().athleteId).isEqualTo(1)
    }

    @Test
    fun `get athlete failed`() = runTest {
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_UNAVAILABLE)
            .setBody(Gson().toJson(getTestAthlete()))
        mockWebServer.enqueue(expectedResponse)

        val actualResponse = remoteDataSource.getAthlete(1)
        assertThat(actualResponse.isFailure)
    }

    @Test
    fun `get athleteAtGames success`() = runTest {
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(Gson().toJson(getTestAthletes()))
        mockWebServer.enqueue(expectedResponse)

        val actualResponse = remoteDataSource.getAthletesAtGames(1)
        assertThat(actualResponse.getOrThrow()).hasSize(2)
    }

    @Test
    fun `get athleteAtGames failed`() = runTest {
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_UNAVAILABLE)
            .setBody(Gson().toJson(getTestAthletes()))
        mockWebServer.enqueue(expectedResponse)

        val actualResponse = remoteDataSource.getAthletesAtGames(1)
        assertThat(actualResponse.isFailure)
    }

    @Test
    fun `get athleteResults success`() = runTest {
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(Gson().toJson(getTestAthleteResults()))
        mockWebServer.enqueue(expectedResponse)

        val actualResponse = remoteDataSource.getAthleteResults(1)
        assertThat(actualResponse.getOrThrow()).hasSize(2)
    }

    @Test
    fun `get athleteResults failed`() = runTest {
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_UNAVAILABLE)
            .setBody(Gson().toJson(getTestAthleteResults()))
        mockWebServer.enqueue(expectedResponse)

        val actualResponse = remoteDataSource.getAthleteResults(1)
        assertThat(actualResponse.isFailure)
    }
}
