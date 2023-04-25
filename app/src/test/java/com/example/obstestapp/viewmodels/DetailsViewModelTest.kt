package com.example.obstestapp.viewmodels

import com.example.obstestapp.MainDispatcherRule
import com.example.obstestapp.TestDispatcherProvider
import com.example.obstestapp.fakes.FakeMainRepository
import com.example.obstestapp.fakes.FakeRemoteDataSource
import com.example.obsapp.ui.models.events.DetailsEvent
import com.example.obstestapp.data.IRemoteDataSource
import com.example.obstestapp.domain.Athlete
import com.example.obstestapp.domain.IMainRepository
import com.example.obstestapp.ui.viewmodels.DetailsViewModel
import com.example.obstestapp.utils.IDispatcherProvider
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DetailsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: DetailsViewModel
    lateinit var repository: IMainRepository
    lateinit var dataSource: IRemoteDataSource
    private lateinit var dispatcher: IDispatcherProvider

    @Before
    fun setUp() {
        dataSource = FakeRemoteDataSource()
        repository = FakeMainRepository(dataSource)
        dispatcher = TestDispatcherProvider()
        viewModel = DetailsViewModel(repository, dispatcher)
    }

    @Test
    fun `test all games`() = runTest {
        viewModel.handleEvent(DetailsEvent.GetUserDetails(1))
        assertThat(viewModel.detailsScreenState.value.athlete).isInstanceOf(Athlete::class.java)
    }
}
