package com.javdc.napptiloompas.data

import com.javdc.napptiloompas.data.repository.util.RepositoryErrorManager
import com.javdc.napptiloompas.domain.util.AsyncError
import com.javdc.napptiloompas.domain.util.AsyncResult
import com.javdc.napptiloompas.domain.util.map
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.net.ConnectException

class RepositoryErrorManagerTest {

    @Test
    fun `when call is wrapped with RepositoryErrorManager wrapper with no exceptions, then emit success`() = runTest {
        // Given

        // When
        val result = RepositoryErrorManager.wrap {
            listOf<String>()
        }.last()

        // Then
        assert(result is AsyncResult.Success)
    }

    @Test
    fun `when call is wrapped with RepositoryErrorManager wrapper with a exception, then emit error`() = runTest {
        // Given

        // When
        val result = RepositoryErrorManager.wrap {
            throw Exception()
        }.last()

        // Then
        assert(result is AsyncResult.Error)
        assert((result as? AsyncResult.Error)?.error is AsyncError.UnknownError)
    }

    @Test
    fun `when call is wrapped with RepositoryErrorManager wrapper with a exception, then emit loading at first`() = runTest {
        // Given

        // When
        val result = RepositoryErrorManager.wrap {
            throw ConnectException()
        }.first()

        // Then
        assert(result is AsyncResult.Loading)
    }

}