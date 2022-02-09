/*
 * Copyright 2022 Jesus
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.business.portfolio.breaking.data.repository

import com.business.portfolio.breaking.data.mapper.toCharacter
import com.business.portfolio.breaking.data.remote.api.BreakingApi
import com.business.portfolio.breaking.data.remote.model.CharacterResponse
import com.business.portfolio.breaking.domain.model.Character
import com.business.portfolio.breaking.domain.repository.CharactersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FakeCharactersRepositoryImpl : CharactersRepository {

    private val character1 = CharacterResponse(
        1,
        "Walter 1",
        "09-07-1958",
        listOf("High School Chemistry Teacher", "Meth King Pin"),
        "https://images.amcnetworks.com/amc.com/wp-content/uploads/2015/04/cast_bb_700x1000_walter-white-lg.jpg",
        "Presumed dead",
        "Heisenberg",
        listOf(2, 4, 5),
        "Bryan Cranston",
        "Breaking Bad"
    )

    private val character2 = CharacterResponse(
        2,
        "Walter 2",
        "09-07-1958",
        listOf("High School Chemistry Teacher", "Meth King Pin"),
        "https://images.amcnetworks.com/amc.com/wp-content/uploads/2015/04/cast_bb_700x1000_walter-white-lg.jpg",
        "Presumed dead",
        "Heisenberg",
        listOf(2),
        "Bryan Cranston",
        "Breaking Bad"
    )

    private val character3 = CharacterResponse(
        3,
        "jesus 3",
        "09-07-1958",
        listOf("High School Chemistry Teacher", "Meth King Pin"),
        "https://images.amcnetworks.com/amc.com/wp-content/uploads/2015/04/cast_bb_700x1000_walter-white-lg.jpg",
        "Presumed dead",
        "Heisenberg",
        listOf(1),
        "Bryan Cranston",
        "Breaking Bad"
    )

    val characterList = listOf<CharacterResponse>(character1, character2, character3)


    override fun getCharacterList(): Flow<List<Character>> = flow { emit(characterList) }
        .map { it.map { r -> r.toCharacter() } }

    override fun getCharacterById(id: Long): Flow<Character> =
        flow { emit(characterList) }
            .map { it[0] }
            .map { it.toCharacter() }
}
