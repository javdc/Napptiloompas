package com.javdc.napptiloompas.data.remote.datasource

import com.javdc.napptiloompas.data.remote.api.OompaLoompaService
import com.javdc.napptiloompas.data.remote.mapper.toBo
import com.javdc.napptiloompas.domain.model.OompaLoompaBo
import com.javdc.napptiloompas.domain.model.OompaLoompasResponseBo

interface OompaLoompaRemoteDataSource {
    suspend fun getOompaLoompas(page: Int): OompaLoompasResponseBo
    suspend fun getOompaLoompaDetails(id: Long): OompaLoompaBo
}

class OompaLoompaRemoteDataSourceImpl(
    private val oompaLoompaService: OompaLoompaService
) : OompaLoompaRemoteDataSource {

    override suspend fun getOompaLoompas(page: Int): OompaLoompasResponseBo =
        oompaLoompaService
            .getOompaLoompas(page)
            .toBo()

    override suspend fun getOompaLoompaDetails(id: Long): OompaLoompaBo =
        oompaLoompaService
            .getOompaLoompaDetails(id)
            .toBo()

}