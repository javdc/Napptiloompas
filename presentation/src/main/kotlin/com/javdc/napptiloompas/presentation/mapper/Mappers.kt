package com.javdc.napptiloompas.presentation.mapper

import com.javdc.napptiloompas.presentation.model.GenderEnum
import com.javdc.napptiloompas.domain.model.OompaLoompaBo
import com.javdc.napptiloompas.presentation.model.ProfessionEnum
import com.javdc.napptiloompas.presentation.model.OompaLoompaVo

fun OompaLoompaBo.toVo() = OompaLoompaVo(
    id = id,
    name = "$firstName $lastName",
    profession = ProfessionEnum.getByCode(profession),
    age = age,
    gender = GenderEnum.getByCode(gender),
    image = image,
)