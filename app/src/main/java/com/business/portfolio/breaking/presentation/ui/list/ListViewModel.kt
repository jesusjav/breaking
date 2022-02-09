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
package com.business.portfolio.breaking.presentation.ui.list

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.business.portfolio.breaking.domain.model.Character
import com.business.portfolio.breaking.domain.usecase.GetCharacterListUseCase
import com.business.portfolio.breaking.presentation.utils.Constants
import com.business.portfolio.breaking.presentation.utils.areAllUnchecked
import com.business.portfolio.breaking.presentation.utils.reset
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel
@Inject constructor(
    private val getCharacterListUseCase: GetCharacterListUseCase,
) : ViewModel() {

    //todo: best compress all in UIstate data class and pass that to compose
    private var _list = MutableStateFlow(emptyList<Character>())

    private var _results = MutableStateFlow(emptyList<Character>())
    var results: StateFlow<List<Character>> = _results

    var filter = MutableStateFlow("")

    val seasonsSelection =
        MutableStateFlow(mutableListOf(false, false, false, false, false))

    init {
        getCharacterList()
    }

    private fun getCharacterList() {
        getCharacterListUseCase()
            .onEach {
                _list.value = it
                _results.value = it
            }
            .flowOn(Dispatchers.IO)
            .catch { e -> e.printStackTrace() }
            .launchIn(viewModelScope)
    }

    val onFilterByName: (characterName: String) -> Unit = { characterName ->
        if (characterName.isBlank()) {
            _results.reset(_list.value)
            filter.value = ""
        } else {
            viewModelScope.launch {
                filter.value = characterName
                _results.value = _list.map {
                    it.filter { it.name.lowercase().startsWith(characterName.lowercase()) }
                }.debounce(Constants.BOUNCE_TIME).first()
            }
        }
    }

    var onFilterBySeason: (season: Int) -> Unit = { season ->
        val checkBoxesList = seasonsSelection.value.toMutableList()
        checkBoxesList.set(season, !checkBoxesList.get(season))
        seasonsSelection.value = checkBoxesList

        val isAllUnchecked = checkBoxesList.toList().areAllUnchecked()
        if (isAllUnchecked) {
            _results.value = _list.value
        } else {
            val checkedMapped = seasonsSelection.value.mapIndexed { index, checked ->
                (index + 1).takeIf { checked }
            }.filterNotNull()

            _results.value = _list.value.filter {
                checkedMapped.intersect(it.appearance).isNotEmpty()
            }
        }

    }
}

