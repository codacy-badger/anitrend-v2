/*
 * Copyright (C) 2019  AniTrend
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

import co.anitrend.buildSrc.Libraries
import org.jetbrains.kotlin.gradle.dsl.KotlinCompile

plugins {
    id("co.anitrend.plugin")
}

tasks.withType(KotlinCompile::class.java) {
    kotlinOptions {
        freeCompilerArgs = listOf(
            "-Xopt-in=kotlinx.coroutines.FlowPreview",
            "-Xopt-in=coil.annotation.ExperimentalCoilApi",
            "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi"
        )
    }
}

dependencies {
    implementation(Libraries.AirBnB.Paris.paris)
    implementation(Libraries.AniTrend.Arch.recycler)
    implementation(Libraries.Google.FlexBox.flexBox)
    implementation(Libraries.prettyTime)
}