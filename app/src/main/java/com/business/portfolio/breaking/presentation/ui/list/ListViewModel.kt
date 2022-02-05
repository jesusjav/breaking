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

    // Mantain the internal data in viewmodel
    private var _list = MutableStateFlow(emptyList<Character>())

    // Results back field with filtered results
    private var _results = MutableStateFlow(emptyList<Character>())

    // Expose results to compose
    var results: StateFlow<List<Character>> = _results

    var filter = mutableStateOf("")

    val seasons = MutableStateFlow(mutableListOf<Boolean>(false, false, false, false, false))

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

    fun onFilterByName(characterName: String) {
        if (characterName.isBlank()) {
            _results.reset(_list.value)
            filter.value = ""
            return
        }
        viewModelScope.launch {
            filter.value = characterName
            _results.value = _list.map {
                it.filter { it.name.lowercase().startsWith(characterName.lowercase()) }
            }.debounce(Constants.BOUNCE_TIME).first()
        }
    }

    fun onFilterBySeason(season: Int) {
        var tempCheck = seasons.value.toMutableList()
        tempCheck.set(season, !tempCheck.get(season))
        seasons.value = tempCheck

        val isAllUnchecked = areAllUnchecked(tempCheck)
        if (isAllUnchecked) {
            _results.value = _list.value
            return
        }

        val listFiltered = mutableListOf<Character>()

        _list.value.forEach {
            var checked: List<Boolean> = seasons.value
            var appearance: List<Int> = it.appearance
            var allSelected = true
            for (apperianceAux in appearance) {
                if (!checked.get(apperianceAux - 1)) {
                    allSelected = false
                    break
                }
            }

            if (allSelected) {
                listFiltered.add(it)
            }
        }

        _results.value = listFiltered

    }

    private fun areAllUnchecked(checked: List<Boolean>): Boolean {
        var isAllChecked = false
        for (check in checked) {
            if (check) {
                isAllChecked = true
                break
            }
        }
        return !isAllChecked
    }
}
