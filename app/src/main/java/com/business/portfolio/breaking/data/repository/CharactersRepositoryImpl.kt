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
import com.business.portfolio.breaking.domain.model.Character
import com.business.portfolio.breaking.domain.repository.CharactersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

open class CharactersRepositoryImpl @Inject constructor(
    private val api: BreakingApi,
) : CharactersRepository {

    override fun getCharacterList(): Flow<List<Character>> = flow { emit(api.getCharacters()) }
        .map { it.map { r -> r.toCharacter() } }

    override fun getCharacterById(id: Long): Flow<Character> =
        flow { emit(api.getCharactersById(id)) }
            .map { it[0] }
            .map { it.toCharacter() }
}
