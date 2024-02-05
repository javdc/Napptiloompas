package com.javdc.napptiloompas.data.remote.model

import com.google.gson.annotations.SerializedName

data class OompaLoompaDto(
    @SerializedName("id") val id: Long,
    @SerializedName("first_name") val firstName: String?,
    @SerializedName("last_name") val lastName: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("quota") val quota: String?,
    @SerializedName("favorite") val favorite: OompaLoompaFavoriteDto?,
    @SerializedName("gender") val gender: String?,
    @SerializedName("image") val image: String?,
    @SerializedName("profession") val profession: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("age") val age: Int?,
    @SerializedName("country") val country: String?,
    @SerializedName("height") val height: Int?,
)