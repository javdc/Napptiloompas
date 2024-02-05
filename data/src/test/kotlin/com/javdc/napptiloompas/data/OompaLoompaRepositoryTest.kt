package com.javdc.napptiloompas.data

import com.javdc.napptiloompas.data.remote.datasource.OompaLoompaRemoteDataSource
import com.javdc.napptiloompas.data.repository.OompaLoompaRepositoryImpl
import com.javdc.napptiloompas.domain.model.OompaLoompaBo
import com.javdc.napptiloompas.domain.model.OompaLoompasResponseBo
import com.javdc.napptiloompas.domain.repository.OompaLoompaRepository
import com.javdc.napptiloompas.domain.util.AsyncResult
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class OompaLoompaRepositoryTest {

    private lateinit var oompaLoompaRepository: OompaLoompaRepository

    @MockK
    private lateinit var oompaLoompaRemoteDataSource: OompaLoompaRemoteDataSource

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        oompaLoompaRepository = OompaLoompaRepositoryImpl(oompaLoompaRemoteDataSource)
    }

    @Test
    fun `when getOompaLoompas is called, then emit oompa loompas response from remote data source`() = runTest {
        // Given
        val expectedResponse = OompaLoompasResponseBo(
            current = 1,
            total = 25,
            results = listOf(OompaLoompaBo(id = 0), OompaLoompaBo(id = 1)),
        )
        coEvery { oompaLoompaRemoteDataSource.getOompaLoompas(any()) } returns expectedResponse

        // When
        val result = oompaLoompaRepository.getOompaLoompas(1).last()
        val response = (result as? AsyncResult.Success)?.data

        // Then
        assert(result is AsyncResult.Success)
        assertEquals(expectedResponse, response)
    }

    @Test
    fun `when getOompaLoompas is called and remote data source throws exception, then emit an error`() = runTest {
        // Given
        coEvery { oompaLoompaRemoteDataSource.getOompaLoompas(any()) } throws Exception()

        // When
        val result = oompaLoompaRepository.getOompaLoompas(1).last()

        // Then
        assert(result is AsyncResult.Error)
    }

    @Test
    fun `when getOompaLoompaDetails is called, then emit oompa loompa details response from remote data source`() = runTest {
        // Given
        val expectedResponse = OompaLoompaBo(id = 0)
        coEvery { oompaLoompaRemoteDataSource.getOompaLoompaDetails(any()) } returns expectedResponse

        // When
        val result = oompaLoompaRepository.getOompaLoompaDetails(0).last()
        val response = (result as? AsyncResult.Success)?.data

        // Then
        assert(result is AsyncResult.Success)
        assertEquals(expectedResponse, response)
    }

    @Test
    fun `when getOompaLoompaDetails is called and remote data source throws exception, then emit an error`() = runTest {
        // Given
        coEvery { oompaLoompaRemoteDataSource.getOompaLoompaDetails(any()) } throws Exception()

        // When
        val result = oompaLoompaRepository.getOompaLoompaDetails(0).last()

        // Then
        assert(result is AsyncResult.Error)
    }

}