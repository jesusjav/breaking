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
package com.business.portfolio.breaking

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import com.business.portfolio.breaking.domain.repository.CharactersRepository
import com.business.portfolio.breaking.domain.usecase.GetCharacterListUseCase
import com.business.portfolio.breaking.domain.usecase.GetCharacterUseCase

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideGetCharacterListUseCase(repo: CharactersRepository) = GetCharacterListUseCase(repo)

    @Provides
    @ViewModelScoped
    fun provideGetCharacterUseCase(repo: CharactersRepository) = GetCharacterUseCase(repo)

}
