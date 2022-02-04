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
package com.business.portfolio.breaking.presentation.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberImagePainter
import com.business.portfolio.breaking.domain.model.Character
import com.business.portfolio.breaking.presentation.theme.Shapes


@Composable
fun DetailScreen(viewModel: DetailViewModel) {
    val character = viewModel.character.collectAsState()
    Body(character = character.value)
}

@Composable
private fun Body(character: Character) {
    Surface(
        modifier = Modifier.fillMaxHeight(),
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                Image(
                    painter = rememberImagePainter(
                        data = character.img,
                        builder = {
                            crossfade(true)
                        }
                    ),
                    contentScale = ContentScale.Crop,
                    contentDescription = character.name,
                    alignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f / 1.4f)


                )
            }
            item {
                Extra(character)
            }
        }
    }
}

@Composable
private fun Extra(
    character: Character
) {
    Card(modifier = Modifier.fillMaxHeight()) {
        Column(modifier = Modifier.fillMaxSize(), Arrangement.Center) {
            Box(modifier = Modifier.align(Alignment.CenterHorizontally))
            {
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.secondary,
                )
            }

            Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text(
                    text = character.occupation.joinToString(separator = ","),
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Thin,
                    textAlign = TextAlign.Center
                )
            }

            Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text(
                    text = character.status,
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Thin,
                )
            }

            Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text(
                    text = character.nickname,
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Thin,
                )
            }

            Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text(
                    text = "Seasons: " + character.appearance.joinToString(separator = ","),
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Thin,
                )
            }


        }
    }
}

