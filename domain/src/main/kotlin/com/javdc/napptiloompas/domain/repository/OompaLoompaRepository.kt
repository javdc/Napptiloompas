package com.javdc.napptiloompas.domain.repository

import com.javdc.napptiloompas.domain.model.OompaLoompaBo
import com.javdc.napptiloompas.domain.model.OompaLoompasResponseBo
import com.javdc.napptiloompas.domain.util.AsyncResult
import kotlinx.coroutines.flow.Flow

interface OompaLoompaRepository {
    suspend fun getOompaLoompas(page: Int): Flow<AsyncResult<OompaLoompasResponseBo>>
    suspend fun getOompaLoompaDetails(id: Long): Flow<AsyncResult<OompaLoompaBo>>
}