package com.business.portfolio.breaking

import com.business.portfolio.breaking.data.mapper.toCharacter
import com.business.portfolio.breaking.data.remote.model.CharacterResponse
import com.business.portfolio.breaking.domain.model.Character
import org.junit.Assert
import org.junit.Test

class MapperTest {

    private val characterResponse = CharacterResponse(
        0,
        "",
        "",
        emptyList(),
        "",
        "",
        "",
        listOf(1, 2, 3),
        "",
        ""
    )

    private val characterMapped = Character(
        0,
        "",
        "",
        emptyList(),
        "",
        "",
        "",
        listOf(1, 2, 3),
        "",
        "",
        1.0f
    )

    @Test
    fun test_mapper_to_character() {
        Assert.assertEquals(characterResponse.toCharacter(), characterMapped)
    }
}