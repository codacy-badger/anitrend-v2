/*
 * Copyright (C) 2020  AniTrend
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

package co.anitrend.data.character.koin

import co.anitrend.data.character.converter.CharacterConverter
import co.anitrend.data.character.converter.CharacterModelConverter
import co.anitrend.data.character.converter.CharacterEntityConverter
import org.koin.dsl.module

private val sourceModule = module {

}

private val converterModule = module {
    factory {
        CharacterConverter()
    }
    factory {
        CharacterModelConverter()
    }
    factory {
        CharacterEntityConverter()
    }
}

private val mapperModule = module {

}

private val useCaseModule = module {

}

private val repositoryModule = module {

}

internal val characterModules = listOf(
    sourceModule,
    converterModule,
    mapperModule,
    useCaseModule,
    repositoryModule
)