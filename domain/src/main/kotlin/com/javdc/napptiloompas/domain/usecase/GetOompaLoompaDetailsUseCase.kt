package com.javdc.napptiloompas.domain.usecase

import com.javdc.napptiloompas.domain.model.OompaLoompaBo
import com.javdc.napptiloompas.domain.repository.OompaLoompaRepository
import com.javdc.napptiloompas.domain.util.AsyncResult
import kotlinx.coroutines.flow.Flow

interface GetOompaLoompaDetailsUseCase {
    suspend operator fun invoke(id: Long): Flow<AsyncResult<OompaLoompaBo>>
}

class GetOompaLoompaDetailsUseCaseImpl(private val repository: OompaLoompaRepository) : GetOompaLoompaDetailsUseCase {
    override suspend operator fun invoke(id: Long): Flow<AsyncResult<OompaLoompaBo>> {
        return repository.getOompaLoompaDetails(id)
    }

}