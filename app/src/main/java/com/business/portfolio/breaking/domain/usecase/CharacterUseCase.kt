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
package com.business.portfolio.breaking.domain.usecase

import com.business.portfolio.breaking.domain.model.Character
import com.business.portfolio.breaking.domain.repository.CharactersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import java.util.Calendar
import kotlin.random.Random

class GetCharacterListUseCase(private val repo: CharactersRepository) {
    operator fun invoke(): Flow<List<Character>> = repo.getCharacterList()
        .onEach { Timber.i("execute: $it") }
}

class GetCharacterUseCase(private val repo: CharactersRepository) {
    operator fun invoke(id: Long): Flow<Character> = repo.getCharacterById(id)
}
