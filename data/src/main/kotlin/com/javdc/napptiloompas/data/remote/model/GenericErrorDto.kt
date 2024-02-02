package com.javdc.napptiloompas.data.remote.model

import com.google.gson.annotations.SerializedName

data class GenericErrorDto (
    @SerializedName("message") val message: String?,
)