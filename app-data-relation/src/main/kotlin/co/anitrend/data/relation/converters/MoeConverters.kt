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

package co.anitrend.data.relation.converters

import co.anitrend.arch.data.converter.SupportConverter
import co.anitrend.data.relation.entity.RelationEntity
import co.anitrend.data.relation.model.remote.MoeModel
import co.anitrend.domain.media.entity.attribute.origin.IMediaSourceId
import co.anitrend.domain.media.entity.attribute.origin.MediaSourceId

internal class RelationModelConverter : SupportConverter<MoeModel, RelationEntity>() {
    /**
     * Function reference from converting from [M] to [E] which will
     * be called by [convertFrom]
     */
    override val fromType: (MoeModel) -> RelationEntity = { model ->
        RelationEntity(
            anilist = model.anilist ?: 0,
            aniDb = model.aniDb,
            kitsu = model.kitsu,
            mal = model.mal
        )
    }

    /**
     * Function reference from converting from [E] to [M] which will
     * be called by [convertTo]
     */
    override val toType: (RelationEntity) -> MoeModel = {
        throw NotImplementedError()
    }
}

internal class RelationEntityConverter : SupportConverter<RelationEntity, IMediaSourceId>() {
    /**
     * Function reference from converting from [M] to [E] which will
     * be called by [convertFrom]
     */
    override val fromType: (RelationEntity) -> MediaSourceId = { entity ->
        MediaSourceId.empty().copy(
            anilist = entity.anilist,
            aniDb = entity.aniDb,
            kitsu = entity.kitsu,
            mal = entity.mal
        )
    }

    /**
     * Function reference from converting from [E] to [M] which will
     * be called by [convertTo]
     */
    override val toType: (IMediaSourceId) -> RelationEntity = {
        throw NotImplementedError()
    }
}