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

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.google.accompanist.insets.statusBarsPadding
import com.business.portfolio.breaking.R
import com.business.portfolio.breaking.domain.model.Character

@Composable
fun ListScreen(
    viewModel: ListViewModel,
    select: (Character) -> Unit,
    modifier: Modifier = Modifier
) {
    val list = viewModel.results.collectAsState()
    val checked = viewModel.seasonsSelection.collectAsState()
    val filter = viewModel.filter.collectAsState()
    val onFilterByName = viewModel.onFilterByName
    val onFilterBySeason = viewModel.onFilterBySeason
    Body(list, select, modifier, checked, filter, onFilterByName, onFilterBySeason)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Body(
    list: State<List<Character>>,
    select: (Character) -> Unit,
    modifier: Modifier,
    checked: State<List<Boolean>>,
    filter: State<String>,
    onFilterByName: (String) -> Unit,
    onFilterBySeason: (Int) -> Unit
) {
    var init by remember { mutableStateOf(true) }
    Column(
        modifier = modifier
            .statusBarsPadding()
    ) {
        Text(
            stringResource(R.string.breaking),
            color = MaterialTheme.colors.primaryVariant,
            style = MaterialTheme.typography.body1,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
        )

        OutlinedTextField(
            value = filter.value,
            onValueChange = { charName ->
                onFilterByName(charName)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp)
                .border(1.dp, MaterialTheme.colors.secondary, RoundedCornerShape(12.dp))
                .background(MaterialTheme.colors.primaryVariant, shape = RoundedCornerShape(12.dp)),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Characters,
                autoCorrect = false,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            placeholder = {
                Text(
                    text = stringResource(R.string.placeholder),
                    color = MaterialTheme.colors.secondary
                )
            },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(MaterialTheme.colors.background)
        )

        LazyRow(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth(),
        ) {
            checked.value.forEachIndexed { index, season ->
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Checkbox(
                            checked = season,
                            onCheckedChange = {
                                onFilterBySeason(index)
                            },

                            colors = CheckboxDefaults.colors(
                                checkedColor = MaterialTheme.colors.primaryVariant,
                                checkmarkColor = MaterialTheme.colors.secondary,
                                uncheckedColor = MaterialTheme.colors.primaryVariant
                            )
                        )
                        Text(
                            text = (index + 1).toString(),
                            modifier = Modifier
                                .padding(horizontal = 10.dp),
                        )
                    }
                }
            }
        }

        if (list.value.isEmpty() && !init) {
            Text(
                stringResource(R.string.empty_list),
                color = MaterialTheme.colors.secondary,
                style = MaterialTheme.typography.body1,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 16.dp)
                    .fillMaxSize()
            )
        }

        LazyVerticalGrid(
            cells = GridCells.Fixed(4),
            modifier = Modifier.padding(start = 14.dp, end = 14.dp, top = 2.dp)
        ) {
            items(list.value) { item ->
                FeaturedList(
                    character = item, select = select
                )
            }
        }

        init = false
    }
}

@Composable
fun FeaturedList(
    character: Character,
    select: (Character) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .padding(4.dp)
            .fillMaxHeight()
            .clickable { select.invoke(character) }
            .semantics {
                contentDescription = "visual list item"
            },
//        color = MaterialTheme.colors.surface,
        elevation = 5.dp,

        shape = MaterialTheme.shapes.medium,
    ) {
//        ConstraintLayout(
//            modifier = Modifier.clickable { select.invoke(character) }
//        ) {
        //  val (image, name, dim, favorite) = createRefs()

        Column {
            Image(
                painter = rememberImagePainter(
                    data = character.img,
                    builder = {
                        crossfade(true)
                    }
                ),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)

            )

            Box(
                modifier = Modifier
                    .padding(top = 6.dp)
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center)
            ) {
                Text(
                    text = character.name,
                    color = MaterialTheme.colors.secondary,
                    // style = MaterialTheme.typography.subtitle2,

                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .size(30.dp),
                    fontSize = 8.sp,
                    maxLines = 2


                )
            }


        }

    }
}


