package com.javdc.napptiloompas.data.remote.model

import com.google.gson.annotations.SerializedName

data class OompaLoompasResponseDto(
    @SerializedName("current") val current: Int,
    @SerializedName("total") val total: Int,
    @SerializedName("results") val results: List<OompaLoompaDto>?,
)