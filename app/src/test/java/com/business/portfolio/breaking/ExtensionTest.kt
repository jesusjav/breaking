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
package com.business.portfolio.breaking

import com.business.portfolio.breaking.presentation.utils.areAllUnchecked
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Assert
import org.junit.Test

class ExtensionTest {
    @Test
    fun check_all_distinct() {
        val list = listOf(true, false, true, false, true)
        assertFalse(list.areAllUnchecked())
    }

    @Test
    fun check_all_unMarked() {
        val list = listOf(false, false, false, false, false)
        assert(list.areAllUnchecked())
    }
}