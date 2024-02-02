package com.javdc.napptiloompas.domain.model

data class OompaLoompasResponseBo(
    val current: Int,
    val total: Int,
    val results: List<OompaLoompaBo>,
)