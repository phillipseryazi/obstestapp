package com.example.obstestapp.viewmodels

import com.example.obstestapp.fakes.FakeRemoteDataSource
import com.example.obstestapp.MainDispatcherRule
import com.example.obstestapp.TestDispatcherProvider
import com.example.obstestapp.data.IRemoteDataSource
import com.example.obstestapp.domain.Games
import com.example.obstestapp.domain.IMainRepository
import com.example.obstestapp.fakes.FakeMainRepository
import com.example.obstestapp.ui.viewmodels.HomeViewModel
import com.example.obstestapp.utils.IDispatcherProvider
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: HomeViewModel
    lateinit var repository: IMainRepository
    lateinit var dataSource: IRemoteDataSource
    lateinit var dispatcher: IDispatcherProvider

    @Before
    fun setUp() {
        dataSource = FakeRemoteDataSource()
        repository = FakeMainRepository(dataSource)
        dispatcher = TestDispatcherProvider()
        viewModel = HomeViewModel(repository, dispatcher)
    }

    @Test
    fun `test all games`() = runTest {
        assertThat(viewModel.homeScreenState.value.games).hasSize(2)
        assertThat(viewModel.homeScreenState.value.games[0]).isInstanceOf(Games::class.java)
    }
}
