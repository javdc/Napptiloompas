package com.javdc.napptiloompas.data.repository

import com.javdc.napptiloompas.data.remote.datasource.OompaLoompaRemoteDataSource
import com.javdc.napptiloompas.data.repository.util.RepositoryErrorManager
import com.javdc.napptiloompas.domain.model.OompaLoompaBo
import com.javdc.napptiloompas.domain.model.OompaLoompasResponseBo
import com.javdc.napptiloompas.domain.repository.OompaLoompaRepository
import com.javdc.napptiloompas.domain.util.AsyncResult
import kotlinx.coroutines.flow.Flow

class OompaLoompaRepositoryImpl(
    private val oompaLoompaRemoteDataSource: OompaLoompaRemoteDataSource,
): OompaLoompaRepository {

    override suspend fun getOompaLoompas(page: Int): Flow<AsyncResult<OompaLoompasResponseBo>> =
        RepositoryErrorManager.wrap {
            oompaLoompaRemoteDataSource.getOompaLoompas(page)
        }

    override suspend fun getOompaLoompaDetails(id: Long): Flow<AsyncResult<OompaLoompaBo>> =
        RepositoryErrorManager.wrap {
            oompaLoompaRemoteDataSource.getOompaLoompaDetails(id)
        }

}