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

package co.anitrend.data.relation.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "relation",
    primaryKeys = ["anilist"]
)
data class RelationEntity(
    @ColumnInfo(name = "anilist") val anilist: Long = 0,
    @ColumnInfo(name = "anidb") val aniDb: Long?,
    @ColumnInfo(name = "kitsu") val kitsu: Long?,
    @ColumnInfo(name = "mal") val mal: Long?
)