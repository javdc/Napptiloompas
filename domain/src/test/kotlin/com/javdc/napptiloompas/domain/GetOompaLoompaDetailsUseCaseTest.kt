package com.javdc.napptiloompas.domain

import com.javdc.napptiloompas.domain.model.OompaLoompaBo
import com.javdc.napptiloompas.domain.repository.OompaLoompaRepository
import com.javdc.napptiloompas.domain.usecase.GetOompaLoompaDetailsUseCase
import com.javdc.napptiloompas.domain.usecase.GetOompaLoompaDetailsUseCaseImpl
import com.javdc.napptiloompas.domain.util.AsyncResult
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetOompaLoompaDetailsUseCaseTest {

    private lateinit var getOompaLoompaDetailsUseCase: GetOompaLoompaDetailsUseCase

    @MockK
    private lateinit var oompaLoompaRepository: OompaLoompaRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getOompaLoompaDetailsUseCase = GetOompaLoompaDetailsUseCaseImpl(oompaLoompaRepository)
    }

    @Test
    fun `when GetOompaLoompaDetailsUseCase is invoked, then emit response from repository`() = runTest {
        // Given
        val expectedResult = AsyncResult.Success(OompaLoompaBo(id = 0))
        coEvery { oompaLoompaRepository.getOompaLoompaDetails(any()) } returns flowOf(expectedResult)

        // When
        val result = getOompaLoompaDetailsUseCase(0).last()

        // Then
        assertEquals(expectedResult, result)
    }

}