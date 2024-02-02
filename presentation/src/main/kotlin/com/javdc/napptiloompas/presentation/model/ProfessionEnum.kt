package com.javdc.napptiloompas.presentation.model

enum class ProfessionEnum(val code: String) {
    DEVELOPER("Developer"),
    METALWORKER("Metalworker"),
    GEMCUTTER("Gemcutter"),
    MEDIC("Medic"),
    BREWER("Brewer");

    companion object {
        fun getByCode(code: String?): ProfessionEnum? =
            ProfessionEnum.entries.firstOrNull { code == it.code }
    }
}