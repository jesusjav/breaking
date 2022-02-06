package com.business.portfolio.breaking.ui

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.business.portfolio.breaking.data.mapper.toCharacter
import com.business.portfolio.breaking.data.remote.model.CharacterResponse
import com.business.portfolio.breaking.presentation.ui.list.FeaturedList
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class FeatureComponentTest {

    private val character1 = CharacterResponse(
        1,
        "Walter 1",
        "09-07-1958",
        listOf("High School Chemistry Teacher", "Meth King Pin"),
        "https://images.amcnetworks.com/amc.com/wp-content/uploads/2015/04/cast_bb_700x1000_walter-white-lg.jpg",
        "Presumed dead",
        "Heisenberg",
        listOf(1, 4, 5),
        "Bryan Cranston",
        "Breaking Bad"
    )

    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    //   @Inject
    //  lateinit var viewModel: ListViewModel

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Before
    fun setUp() {
        composeTestRule.setContent {
            FeaturedList(
                character = character1.toCharacter(), select = { }
            )
        }
    }

    @Test
    fun component_show_name() {
        composeTestRule.onNodeWithText("Walter 1").assertExists()
    }

    @Test
    fun component_show_color() {
        composeTestRule.onNodeWithText("Walter 1").assertHasClickAction()
    }

    @Test
    fun component_perform_click() {
        composeTestRule.onNodeWithText("Walter 1").performClick()
    }

}