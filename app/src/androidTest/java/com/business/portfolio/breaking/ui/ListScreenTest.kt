package com.business.portfolio.breaking.ui

//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.hilt.navigation.compose.hiltViewModel
import com.business.portfolio.breaking.presentation.ui.list.ListScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class ListScreenTest {

    @get:Rule(order = 1)
    var hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    var composeTestRule = createComposeRule()

    @Before //bug on this when injecting view model using navigation compose stackoverflow
    fun setup() {
        hiltTestRule.inject()
        composeTestRule.setContent {
            ListScreen(viewModel = hiltViewModel(), select = {})
        }
    }

    @Test
    fun todo() {

    }
}