package com.javdc.napptiloompas.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.javdc.napptiloompas.domain.model.OompaLoompaBo
import com.javdc.napptiloompas.domain.usecase.GetOompaLoompaDetailsUseCase
import com.javdc.napptiloompas.domain.util.AsyncResult
import com.javdc.napptiloompas.presentation.ui.details.OompaLoompaDetailsViewModel
import com.javdc.napptiloompas.presentation.util.CoroutineTestRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifySequence
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class OompaLoompaDetailsViewModelTest {

    private lateinit var oompaLoompaDetailsViewModel: OompaLoompaDetailsViewModel

    @MockK
    private lateinit var getOompaLoompaDetailsUseCase: GetOompaLoompaDetailsUseCase

    @MockK(relaxUnitFun = true)
    private lateinit var oompaLoompaDetailsObserver: Observer<AsyncResult<OompaLoompaBo>>

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        oompaLoompaDetailsViewModel = OompaLoompaDetailsViewModel(
            coroutineTestRule.testDispatcher,
            getOompaLoompaDetailsUseCase
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when fetchOompaLoompaDetails is called, then post results from use case in livedata`() = runTest {
        // Given
        oompaLoompaDetailsViewModel.oompaLoompaDetailsLiveData.observeForever(oompaLoompaDetailsObserver)
        val loadingResult = AsyncResult.Loading<OompaLoompaBo>()
        val expectedResult = AsyncResult.Success(OompaLoompaBo(id = 0))
        coEvery { getOompaLoompaDetailsUseCase(any()) } returns flow {
            emit(loadingResult)
            delay(1000)
            emit(expectedResult)
        }

        // When
        oompaLoompaDetailsViewModel.fetchOompaLoompaDetails(0)
        advanceUntilIdle()

        // Then
        coVerify { getOompaLoompaDetailsUseCase(0) }
        coVerifySequence {
            oompaLoompaDetailsObserver.onChanged(loadingResult)
            oompaLoompaDetailsObserver.onChanged(expectedResult)
        }
    }

}