package com.business.portfolio.breaking.presentation.utils

import kotlinx.coroutines.flow.MutableStateFlow

fun <T> MutableStateFlow<T>.reset(initValue: T) = initValue.also { this.value = it }