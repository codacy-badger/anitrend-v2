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

package co.anitrend.data.customlist.entity

import androidx.room.*
import co.anitrend.data.core.common.Identity
import co.anitrend.data.medialist.entity.MediaListEntity
import co.anitrend.data.user.entity.UserEntity
import co.anitrend.domain.media.enums.MediaType
import co.anitrend.support.query.builder.annotation.EntitySchema

@Entity(
    tableName = "custom_list",
    foreignKeys = [
        ForeignKey(
            entity = MediaListEntity::class,
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE,
            childColumns = ["media_list_id"],
            parentColumns = ["id"],
            deferred = true
        )
    ],
    indices = [
        Index(
            value = [
                "media_list_id",
                "list_name"
            ],
            unique = true
        )
    ]
)
@EntitySchema
internal data class CustomListEntity(
    @ColumnInfo(name = "enabled") val enabled: Boolean,
    @ColumnInfo(name = "list_name") val listName: String,
    @ColumnInfo(name = "media_list_id") val mediaListId: Long,
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") override val id: Long = 0
) : Identity