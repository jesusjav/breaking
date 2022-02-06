package com.business.portfolio.breaking.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.business.portfolio.breaking.data.remote.api.BreakingApi
import com.business.portfolio.breaking.data.repository.CharactersRepositoryImpl
import com.business.portfolio.breaking.data.repository.FakeCharactersRepositoryImpl
import com.business.portfolio.breaking.domain.repository.CharactersRepository
import com.business.portfolio.breaking.domain.usecase.GetCharacterListUseCase
import com.business.portfolio.breaking.presentation.ui.list.ListViewModel
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import timber.log.Timber
import java.lang.Thread.sleep
import javax.inject.Inject

@HiltAndroidTest
class ListViewModelTest {

    private lateinit var viewModel: ListViewModel

    var repository: CharactersRepository = FakeCharactersRepositoryImpl()

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModel = ListViewModel(GetCharacterListUseCase(repository))
    }

    @Test
    fun get_character_by_name_test1() {
        runBlocking {
            viewModel.onFilterByName("wal")
            delay(3000)
            assert(viewModel.results.value.size == 2)
        }
    }

    @Test
    fun get_character_by_name_test2() {
        runBlocking {
            viewModel.onFilterByName("jesus")
            delay(3000)
            assert(viewModel.results.value.size == 1)
        }
    }

    @Test
    fun get_character_by_name_test3() {
        runBlocking {
            viewModel.onFilterByName("another")
            delay(3000)
            assert(viewModel.results.value.size == 0)
        }
    }

    @Test
    fun get_character_by_seasons() {
        runBlocking {
            viewModel.onFilterBySeason(0)
            delay(3000)

            val checkedMapped = viewModel.seasonsSelection.value.mapIndexed { index, checked ->
                (index + 1).takeIf { checked }
            }.filterNotNull()

            var filtered = viewModel.results.value.filter {
                checkedMapped.intersect(it.appearance).isNotEmpty()
            }

            assert(filtered.size == 1)
        }
    }

    @Test
    fun get_character_by_seasons2() {
        runBlocking {
            viewModel.onFilterBySeason(1)
            delay(3000)

            val checkedMapped = viewModel.seasonsSelection.value.mapIndexed { index, checked ->
                (index + 1).takeIf { checked }
            }.filterNotNull()

            var filtered = viewModel.results.value.filter {
                checkedMapped.intersect(it.appearance).isNotEmpty()
            }

            assert(filtered.size == 2)
        }
    }

    @Test
    fun get_character_by_seasons_When_Empty_Result() {
        runBlocking {
            viewModel.onFilterBySeason(2)
            delay(3000)

            val checkedMapped = viewModel.seasonsSelection.value.mapIndexed { index, checked ->
                (index + 1).takeIf { checked }
            }.filterNotNull()

            var filtered = viewModel.results.value.filter {
                checkedMapped.intersect(it.appearance).isNotEmpty()
            }

            assert(filtered.size == 0)
        }
    }
}
