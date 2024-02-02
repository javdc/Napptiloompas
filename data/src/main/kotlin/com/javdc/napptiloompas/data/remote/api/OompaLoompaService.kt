package com.javdc.napptiloompas.data.remote.api

import com.javdc.napptiloompas.data.remote.model.OompaLoompaDto
import com.javdc.napptiloompas.data.remote.model.OompaLoompasResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface OompaLoompaService {

    @GET("/napptilus/oompa-loompas")
    suspend fun getOompaLoompas(@Query("page") page: Int): OompaLoompasResponseDto

    @GET("/napptilus/oompa-loompas/{id}")
    suspend fun getOompaLoompaDetails(@Path("id") id: Long): OompaLoompaDto

}