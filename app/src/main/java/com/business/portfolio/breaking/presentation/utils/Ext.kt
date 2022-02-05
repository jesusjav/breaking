package com.business.portfolio.breaking.presentation.utils

import kotlinx.coroutines.flow.MutableStateFlow
import java.util.*

fun <T> MutableStateFlow<T>.reset(initValue: T) = initValue.also { this.value = it }

fun <T> List<T>.areAllUnchecked(): Boolean = Collections.frequency(this, false) == this.size