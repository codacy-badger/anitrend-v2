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

package co.anitrend.data.media.entity.view

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import co.anitrend.data.airing.entity.AiringScheduleEntity
import co.anitrend.data.genre.entity.connection.GenreConnectionEntity
import co.anitrend.data.link.entity.LinkEntity
import co.anitrend.data.media.entity.MediaEntity
import co.anitrend.data.medialist.entity.MediaListEntity
import co.anitrend.data.rank.entity.RankEntity
import co.anitrend.data.tag.entity.TagEntity
import co.anitrend.data.tag.entity.connection.TagConnectionEntity

internal sealed class MediaEntityView {
    abstract val media: MediaEntity
    abstract val nextAiring: AiringScheduleEntity?

    abstract val tags: List<TagEntity.Extended>
    abstract val genres: List<String>

    internal data class Core(
        @Embedded override val media: MediaEntity,
        @Relation(
            parentColumn = "next_airing_id",
            entityColumn = "id"
        )
        override val nextAiring: AiringScheduleEntity?,
        @Relation(
            parentColumn = "id",
            entityColumn = "media_id",
            associateBy = Junction(value = TagConnectionEntity::class)
        )
        override val tags: List<TagEntity.Extended> = emptyList(),
        @Relation(
            projection = ["genre"],
            parentColumn = "id",
            entityColumn = "media_id",
            entity = GenreConnectionEntity::class
        )
        override val genres: List<String> = emptyList()
    ) : MediaEntityView()

    internal data class WithMediaList(
        @Relation(
            parentColumn = "id",
            entityColumn = "media_id"
        )
        val mediaList: MediaListEntity?,
        @Embedded override val media: MediaEntity,
        @Relation(
            parentColumn = "next_airing_id",
            entityColumn = "id"
        )
        override val nextAiring: AiringScheduleEntity?,
        @Relation(
            parentColumn = "id",
            entityColumn = "media_id",
            associateBy = Junction(value = TagConnectionEntity::class)
        )
        override val tags: List<TagEntity.Extended> = emptyList(),
        @Relation(
            projection = ["genre"],
            parentColumn = "id",
            entityColumn = "media_id",
            entity = GenreConnectionEntity::class
        )
        override val genres: List<String> = emptyList()
    ) : MediaEntityView()

    internal data class WithMediaListExtended(
        @Relation(
            parentColumn = "id",
            entityColumn = "media_id"
        )
        val mediaList: MediaListEntity?,
        @Relation(
            parentColumn = "id",
            entityColumn = "media_id"
        )
        val links: List<LinkEntity>,
        @Relation(
            parentColumn = "id",
            entityColumn = "media_id"
        )
        val ranks: List<RankEntity>,
        @Embedded override val media: MediaEntity,
        @Relation(
            parentColumn = "next_airing_id",
            entityColumn = "id"
        )
        override val nextAiring: AiringScheduleEntity?,
        @Relation(
            parentColumn = "id",
            entityColumn = "media_id",
            associateBy = Junction(value = TagConnectionEntity::class)
        )
        override val tags: List<TagEntity.Extended> = emptyList(),
        @Relation(
            projection = ["genre"],
            parentColumn = "id",
            entityColumn = "media_id",
            entity = GenreConnectionEntity::class
        )
        override val genres: List<String> = emptyList()
    ) : MediaEntityView()
}