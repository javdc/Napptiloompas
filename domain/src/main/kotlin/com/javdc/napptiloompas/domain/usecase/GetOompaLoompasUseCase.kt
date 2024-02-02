package com.javdc.napptiloompas.domain.usecase

import com.javdc.napptiloompas.domain.model.OompaLoompasResponseBo
import com.javdc.napptiloompas.domain.repository.OompaLoompaRepository
import com.javdc.napptiloompas.domain.util.AsyncResult
import kotlinx.coroutines.flow.Flow

interface GetOompaLoompasUseCase {
    suspend operator fun invoke(page: Int): Flow<AsyncResult<OompaLoompasResponseBo>>
}

class GetOompaLoompasUseCaseImpl(private val repository: OompaLoompaRepository) : GetOompaLoompasUseCase {
    override suspend operator fun invoke(page: Int): Flow<AsyncResult<OompaLoompasResponseBo>> {
        return repository.getOompaLoompas(page)
    }

}