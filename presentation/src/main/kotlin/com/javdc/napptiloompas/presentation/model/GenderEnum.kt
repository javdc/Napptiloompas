package com.javdc.napptiloompas.presentation.model

enum class GenderEnum(val code: String) {
    MALE("M"),
    FEMALE("F");

    companion object {
        fun getByCode(code: String?): GenderEnum? =
            entries.firstOrNull { code == it.code }
    }
}