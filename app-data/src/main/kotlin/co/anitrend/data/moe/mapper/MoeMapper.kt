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

package co.anitrend.data.moe.mapper

import co.anitrend.data.arch.mapper.DefaultMapper
import co.anitrend.data.moe.converters.SourceModelConverter
import co.anitrend.data.moe.datasource.local.MoeLocalSource
import co.anitrend.data.moe.entity.MoeEntity
import co.anitrend.data.moe.model.remote.MoeModel

internal class MoeMapper(
    private val localSource: MoeLocalSource,
    private val converter: SourceModelConverter
) : DefaultMapper<MoeModel?, MoeEntity?>() {

    /**
     * Save [data] into your desired local source
     */
    override suspend fun persist(data: MoeEntity?) {
        if (data != null)
            localSource.upsert(data)
    }

    /**
     * Creates mapped objects and handles the database operations which may be required to map various objects,
     *
     * @param source the incoming data source type
     * @return mapped object that will be consumed by [onResponseDatabaseInsert]
     */
    override suspend fun onResponseMapFrom(
        source: MoeModel?
    ) = source?.let { converter.convertFrom(it) }
}