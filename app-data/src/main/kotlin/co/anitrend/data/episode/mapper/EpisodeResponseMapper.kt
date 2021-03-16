/*
 * Copyright (C) 2021  AniTrend
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

package co.anitrend.data.episode.mapper

import co.anitrend.data.arch.mapper.DefaultMapper
import co.anitrend.data.arch.railway.OutCome
import co.anitrend.data.episode.converter.EpisodeModelConverter
import co.anitrend.data.episode.datasource.local.EpisodeLocalSource
import co.anitrend.data.episode.entity.EpisodeEntity
import co.anitrend.data.episode.model.page.EpisodePageModel

internal class EpisodeResponseMapper(
    private val localSource: EpisodeLocalSource,
    private val converter: EpisodeModelConverter
) : DefaultMapper<EpisodePageModel, List<EpisodeEntity>>() {

    /**
     * Handles the persistence of [data] into a local source
     *
     * @return [OutCome.Pass] or [OutCome.Fail] of the operation
     */
    override suspend fun persistChanges(data: List<EpisodeEntity>): OutCome<Nothing?> {
        return runCatching {
            localSource.upsert(data)
            OutCome.Pass(null)
        }.getOrElse { OutCome.Fail(listOf(it)) }
    }

    /**
     * Creates mapped objects and handles the database operations which may be required to map various objects,
     *
     * @param source the incoming data source type
     * @return mapped object that will be consumed by [onResponseDatabaseInsert]
     */
    override suspend fun onResponseMapFrom(
        source: EpisodePageModel
    ) = converter.convertFrom(source.channel?.items.orEmpty())
}