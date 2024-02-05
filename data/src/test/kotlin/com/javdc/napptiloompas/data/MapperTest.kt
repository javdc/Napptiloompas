package com.javdc.napptiloompas.data

import com.javdc.napptiloompas.data.remote.mapper.toBo
import com.javdc.napptiloompas.data.remote.model.OompaLoompaDto
import org.junit.Test

class MapperTest {

    @Test
    fun `when OompaLoompaDto mapper is called, then a OompaLoompaBo with correct data is returned`() {
        // Given
        val dto = OompaLoompaDto(
            id = 0,
            firstName = "firstName",
            lastName = "lastName",
            description = "description",
            quota = "quota",
            favorite = null,
            gender = "gender",
            image = "image",
            profession = "profession",
            email = "email",
            age = 1,
            country = "country",
            height = 2,
        )

        // When
        val bo = dto.toBo()

        // Then
        assert(dto.id == bo.id)
        assert(dto.firstName == bo.firstName)
        assert(dto.lastName == bo.lastName)
        assert(dto.description == bo.description)
        assert(dto.quota == bo.quota)
        assert(dto.gender == bo.gender)
        assert(dto.image == bo.image)
        assert(dto.profession == bo.profession)
        assert(dto.email == bo.email)
        assert(dto.age == bo.age)
        assert(dto.country == bo.country)
        assert(dto.height == bo.height)
    }

}