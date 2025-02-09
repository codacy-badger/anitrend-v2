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

package co.anitrend.data.android.cache.model

enum class CacheRequest(val alias: String) {
    CAROUSEL("carousel"),
    AIRING("airing"),
    CHARACTER("character"),
    STAFF("staff"),
    STUDIO("studio"),
    GENRE("genre"),
    MEDIA("media"),
    MEDIA_LIST("media_list"),
    SOURCE("source"),
    TAG("tag"),
    USER("user"),
    REVIEW("review"),
    USER_ID("user_id"),
    STATISTIC("statistic"),
    NEWS("news"),
    EPISODE("episode"),
    MOE("moe"),
    JIKAN("jikan"),
    XEM("the_xem"),
}