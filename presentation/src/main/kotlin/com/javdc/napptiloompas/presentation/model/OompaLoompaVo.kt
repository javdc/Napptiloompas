package com.javdc.napptiloompas.presentation.model

data class OompaLoompaVo(
    val id: Long,
    val name: String? = null,
    val profession: ProfessionEnum? = null,
    val age: Int? = null,
    val gender: GenderEnum? = null,
    val image: String? = null,
)