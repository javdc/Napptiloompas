package com.javdc.napptiloompas.data.remote.mapper

import com.javdc.napptiloompas.data.remote.model.OompaLoompaDto
import com.javdc.napptiloompas.data.remote.model.OompaLoompaFavoriteDto
import com.javdc.napptiloompas.data.remote.model.OompaLoompasResponseDto
import com.javdc.napptiloompas.domain.model.OompaLoompaBo
import com.javdc.napptiloompas.domain.model.OompaLoompaFavoriteBo
import com.javdc.napptiloompas.domain.model.OompaLoompasResponseBo

fun OompaLoompasResponseDto.toBo() = OompaLoompasResponseBo(
    current = current,
    total = total,
    results = results?.map { it.toBo() }.orEmpty(),
)

fun OompaLoompaDto.toBo() = OompaLoompaBo(
    id = id,
    firstName = firstName,
    lastName = lastName,
    description = description,
    quota = quota,
    favorite = favorite?.toBo(),
    gender = gender,
    image = image,
    profession = profession,
    email = email,
    age = age,
    country = country,
    height = height,
)

fun OompaLoompaFavoriteDto.toBo() = OompaLoompaFavoriteBo(
    color = color,
    food = food,
    randomString = randomString,
    song = song,
)