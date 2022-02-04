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

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.business.portfolio.breaking.domain.model.Character
import com.business.portfolio.breaking.domain.usecase.GetCharacterListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ListViewModel
@Inject constructor(
    private val getCharacterListUseCase: GetCharacterListUseCase,
) : ViewModel() {

    private var _list = MutableStateFlow(emptyList<Character>())

    var results = MutableStateFlow(emptyList<Character>())

    var filter = mutableStateOf("")

    val seasons = MutableStateFlow(mutableListOf<Boolean>(false, false, false, false, false))

    init {
        getCharacterList()
    }

    private fun getCharacterList() {
        getCharacterListUseCase()
            .onEach { _list.value = it }
            .onEach { results.value = it }
            .flowOn(Dispatchers.IO)
            .catch { e -> e.printStackTrace() }
            .launchIn(viewModelScope)
    }

    fun onFilterByString(characterName: String) {
        if (characterName.isNullOrBlank()) {
            results.value = _list.value
            filter.value = ""
            return
        }
        filter.value = characterName
        val listFiltered = mutableListOf<Character>()
        _list.value.forEach {
            if (it.name.lowercase().startsWith(characterName.lowercase())) {
                listFiltered.add(it)
            }
        }
        results.value = listFiltered
    }

    fun onFilterBySeason(season: Int) {
        var tempCheck = seasons.value.toMutableList()
        tempCheck.set(season, !tempCheck.get(season))
        seasons.value = tempCheck

        val isAllUnchecked = areAllUnchecked(tempCheck)
        if (isAllUnchecked) {
            results.value = _list.value
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

        results.value = listFiltered

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
