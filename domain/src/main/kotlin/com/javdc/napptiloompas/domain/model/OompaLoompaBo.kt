package com.javdc.napptiloompas.domain.model

data class OompaLoompaBo(
    val id: Long,
    val firstName: String?,
    val lastName: String?,
    val description: String?,
    val quota: String?,
    val favorite: OompaLoompaFavoriteBo?,
    val gender: String?,
    val image: String?,
    val profession: String?,
    val email: String?,
    val age: Int?,
    val country: String?,
    val height: Int?,
)