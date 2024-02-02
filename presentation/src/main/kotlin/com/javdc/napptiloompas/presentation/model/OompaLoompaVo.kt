package com.javdc.napptiloompas.presentation.model

data class OompaLoompaVo(
    val id: Long,
    val name: String?,
    val profession: ProfessionEnum?,
    val age: Int?,
    val gender: GenderEnum?,
    val image: String?,
)