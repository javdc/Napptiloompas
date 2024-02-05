package com.javdc.napptiloompas.domain

import com.javdc.napptiloompas.domain.model.OompaLoompaBo
import com.javdc.napptiloompas.domain.model.OompaLoompasResponseBo
import com.javdc.napptiloompas.domain.repository.OompaLoompaRepository
import com.javdc.napptiloompas.domain.usecase.GetOompaLoompasUseCase
import com.javdc.napptiloompas.domain.usecase.GetOompaLoompasUseCaseImpl
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

class GetOompaLoompasUseCaseTest {

    private lateinit var getOompaLoompasUseCase: GetOompaLoompasUseCase

    @MockK
    private lateinit var oompaLoompaRepository: OompaLoompaRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getOompaLoompasUseCase = GetOompaLoompasUseCaseImpl(oompaLoompaRepository)
    }

    @Test
    fun `when GetOompaLoompasUseCase is invoked, then emit response from repository`() = runTest {
        // Given
        val expectedResult = AsyncResult.Success(
            OompaLoompasResponseBo(
                current = 1,
                total = 20,
                results = listOf(OompaLoompaBo(id = 0), OompaLoompaBo(id = 1)),
            )
        )
        coEvery { oompaLoompaRepository.getOompaLoompas(any()) } returns flowOf(expectedResult)

        // When
        val result = getOompaLoompasUseCase(1).last()

        // Then
        assertEquals(expectedResult, result)
    }

}