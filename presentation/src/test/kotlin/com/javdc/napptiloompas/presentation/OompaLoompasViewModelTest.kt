package com.javdc.napptiloompas.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.javdc.napptiloompas.domain.model.OompaLoompaBo
import com.javdc.napptiloompas.domain.model.OompaLoompasResponseBo
import com.javdc.napptiloompas.domain.usecase.GetOompaLoompasUseCase
import com.javdc.napptiloompas.domain.util.AsyncResult
import com.javdc.napptiloompas.presentation.model.GenderEnum
import com.javdc.napptiloompas.presentation.model.OompaLoompaVo
import com.javdc.napptiloompas.presentation.model.ProfessionEnum
import com.javdc.napptiloompas.presentation.ui.list.OompaLoompasViewModel
import com.javdc.napptiloompas.presentation.util.CoroutineTestRule
import com.javdc.napptiloompas.presentation.util.getOrAwaitValue
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class OompaLoompasViewModelTest {

    @MockK
    private lateinit var getOompaLoompasUseCase: GetOompaLoompasUseCase

    @MockK(relaxUnitFun = true)
    private lateinit var oompaLoompasObserver: Observer<AsyncResult<List<OompaLoompaVo>>>

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when viewModel is initialized, then oompa loompas are fetched and posted in livedata`() = runTest {
        // Given
        val expectedResponseFromUseCase = OompaLoompasResponseBo(
            current = 1,
            total = 25,
            results = listOf(OompaLoompaBo(id = 0), OompaLoompaBo(id = 1)),
        )
        coEvery { getOompaLoompasUseCase(any()) } returns flow {
            emit(AsyncResult.Loading())
            delay(1000)
            emit(AsyncResult.Success(expectedResponseFromUseCase))
        }
        val oompaLoompasViewModel = OompaLoompasViewModel(
            coroutineTestRule.testDispatcher,
            coroutineTestRule.testDispatcher,
            getOompaLoompasUseCase,
        )
        oompaLoompasViewModel.oompaLoompasLiveData.observeForever(oompaLoompasObserver)

        // When
        // Call is made in init block
        advanceUntilIdle()

        // Then
        coVerify(exactly = 1) { getOompaLoompasUseCase(1) }
        coVerify(exactly = 2) { oompaLoompasObserver.onChanged(any()) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when fetchOompaLoompas was called for last page and is called again, then no new list is posted`() = runTest {
        // Given
        val expectedResponseFromUseCase = OompaLoompasResponseBo(
            current = 25,
            total = 25,
            results = listOf(),
        )
        coEvery { getOompaLoompasUseCase(any()) } returns flow {
            emit(AsyncResult.Loading())
            delay(1000)
            emit(AsyncResult.Success(expectedResponseFromUseCase))
        }
        val oompaLoompasViewModel = OompaLoompasViewModel(
            coroutineTestRule.testDispatcher,
            coroutineTestRule.testDispatcher,
            getOompaLoompasUseCase,
        )
        oompaLoompasViewModel.oompaLoompasLiveData.observeForever(oompaLoompasObserver)

        // When
        // One call is made in init block
        advanceUntilIdle()
        oompaLoompasViewModel.fetchOompaLoompas()
        advanceUntilIdle()

        // Then
        coVerify(exactly = 1) { getOompaLoompasUseCase(1) }
        coVerify(exactly = 2) { oompaLoompasObserver.onChanged(any()) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when setProfessionsFilter is called, then new profession filters are posted`() = runTest {
        // Given
        val selectedFilters = listOf(ProfessionEnum.DEVELOPER, ProfessionEnum.BREWER)
        val expectedResponseFromUseCase = OompaLoompasResponseBo(
            current = 1,
            total = 25,
            results = listOf(),
        )
        coEvery { getOompaLoompasUseCase(any()) } returns flow {
            emit(AsyncResult.Loading())
            delay(1000)
            emit(AsyncResult.Success(expectedResponseFromUseCase))
        }
        val oompaLoompasViewModel = OompaLoompasViewModel(
            coroutineTestRule.testDispatcher,
            coroutineTestRule.testDispatcher,
            getOompaLoompasUseCase,
        )
        oompaLoompasViewModel.oompaLoompasLiveData.observeForever(oompaLoompasObserver)

        // When
        // One call is made in init block
        advanceUntilIdle()
        oompaLoompasViewModel.setProfessionsFilter(selectedFilters)
        advanceUntilIdle()

        // Then
        assert(oompaLoompasViewModel.filtersLiveData.getOrAwaitValue().professionsFilter.containsAll(selectedFilters))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when setGendersFilter is called, then new gender filters are posted`() = runTest {
        // Given
        val selectedFilters = listOf(GenderEnum.MALE)
        val expectedResponseFromUseCase = OompaLoompasResponseBo(
            current = 1,
            total = 25,
            results = listOf(),
        )
        coEvery { getOompaLoompasUseCase(any()) } returns flow {
            emit(AsyncResult.Loading())
            delay(1000)
            emit(AsyncResult.Success(expectedResponseFromUseCase))
        }
        val oompaLoompasViewModel = OompaLoompasViewModel(
            coroutineTestRule.testDispatcher,
            coroutineTestRule.testDispatcher,
            getOompaLoompasUseCase,
        )
        oompaLoompasViewModel.oompaLoompasLiveData.observeForever(oompaLoompasObserver)

        // When
        // One call is made in init block
        advanceUntilIdle()
        oompaLoompasViewModel.setGendersFilter(selectedFilters)
        advanceUntilIdle()

        // Then
        assert(oompaLoompasViewModel.filtersLiveData.getOrAwaitValue().gendersFilter.containsAll(selectedFilters))
    }

}