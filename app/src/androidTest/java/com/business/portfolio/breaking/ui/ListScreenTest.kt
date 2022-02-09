package com.business.portfolio.breaking.ui

//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.hilt.navigation.compose.hiltViewModel
import com.business.portfolio.breaking.data.mapper.toCharacter
import com.business.portfolio.breaking.data.repository.FakeCharactersRepositoryImpl
import com.business.portfolio.breaking.presentation.ui.list.Body
import com.business.portfolio.breaking.presentation.ui.list.ListScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class ListScreenTest {

    private val fakeCharactersRepositoryImpl = FakeCharactersRepositoryImpl()

    @get:Rule
    var composeTestRule = createComposeRule()

    @Before //bug on this when injecting view model using navigation compose stackoverflow
    fun setup() {
        composeTestRule.setContent {

            Body(
                list = mutableStateOf(fakeCharactersRepositoryImpl.characterList.map { it.toCharacter() }),
                select = {},
                modifier = Modifier,
                checked = mutableStateOf(emptyList()),
                filter = mutableStateOf(""),
                onFilterByName = {},
                onFilterBySeason = {}
            )
        }

        composeTestRule.waitForIdle()
    }

    @Test
    fun show_all_data() {
        composeTestRule.onAllNodesWithContentDescription(
            "visual list item"
        ).assertCountEquals(3)
    }
}