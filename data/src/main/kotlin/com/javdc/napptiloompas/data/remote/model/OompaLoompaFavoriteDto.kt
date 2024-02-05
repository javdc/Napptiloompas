package com.javdc.napptiloompas.data.remote.model

import com.google.gson.annotations.SerializedName

data class OompaLoompaFavoriteDto(
    @SerializedName("color") val color: String?,
    @SerializedName("food") val food: String?,
    @SerializedName("random_string") val randomString: String?,
    @SerializedName("song") val song: String?,
)