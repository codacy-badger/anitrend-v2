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

package co.anitrend.data.mediatrend.model.remote

import co.anitrend.data.media.model.MediaModelCore
import co.anitrend.data.mediatrend.model.contract.IMediaTrendModel
import com.google.gson.annotations.SerializedName

/** [MediaTrend](https://anilist.github.io/ApiV2-GraphQL-Docs/mediatrend.doc.html)
 * Daily media statistics
 */
internal data class MediaTrendModel(
    @SerializedName("averageScore") override val averageScore: Int?,
    @SerializedName("date") override val date: Long,
    @SerializedName("episode") override val episode: Int?,
    @SerializedName("inProgress") override val inProgress: Int?,
    @SerializedName("media") override val media: MediaModelCore,
    @SerializedName("mediaId") override val mediaId: Long,
    @SerializedName("popularity") override val popularity: Int?,
    @SerializedName("releasing") override val releasing: Boolean,
    @SerializedName("trending") override val trending: Int
) : IMediaTrendModel