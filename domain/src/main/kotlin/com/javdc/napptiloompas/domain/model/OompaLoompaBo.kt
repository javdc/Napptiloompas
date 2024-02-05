package com.javdc.napptiloompas.domain.model

data class OompaLoompaBo(
    val id: Long,
    val firstName: String? = null,
    val lastName: String? = null,
    val description: String? = null,
    val quota: String? = null,
    val favorite: OompaLoompaFavoriteBo? = null,
    val gender: String? = null,
    val image: String? = null,
    val profession: String? = null,
    val email: String? = null,
    val age: Int? = null,
    val country: String? = null,
    val height: Int? = null,
)