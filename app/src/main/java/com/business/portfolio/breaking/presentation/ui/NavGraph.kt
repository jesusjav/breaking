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
package com.business.portfolio.breaking.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.business.portfolio.breaking.R
import com.business.portfolio.breaking.domain.model.Character
import com.business.portfolio.breaking.presentation.ui.detail.DetailScreen
import com.business.portfolio.breaking.presentation.ui.list.ListScreen

sealed class NavScreens(val route: String) {
    object MAIN : NavScreens("main")
    object DETAIL : NavScreens("detail")
}

@Composable
fun NavGraph(startDestination: NavScreens = NavScreens.MAIN) {
    val navController = rememberNavController()
    val actions = remember(navController) { MainActions(navController) }
    val selectedTab = remember { mutableStateOf(BottomNavTabs.LIST) }
    NavHost(navController = navController, startDestination = startDestination.route) {
        composable(
            route = NavScreens.MAIN.route
        ) { NavScreen(actions = actions) }
        composable(
            route = "${NavScreens.DETAIL.route}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { DetailScreen(viewModel = hiltViewModel()) }
    }
}

// NavTabs
enum class BottomNavTabs(val label: String, val icon: Int) {
    LIST("Character", R.drawable.ic_launcher_foreground),
}

@Composable
fun NavScreen(
    actions: MainActions,
) {
    ListScreen(
        hiltViewModel(),
        actions.moveDetail,
    )
}

class MainActions(navController: NavHostController) {
    val moveDetail: (Character) -> Unit = { character ->
        navController.navigate("${NavScreens.DETAIL.route}/${character.charId}")
    }
}
