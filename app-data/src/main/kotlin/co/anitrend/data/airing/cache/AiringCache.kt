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

package co.anitrend.data.airing.cache

import co.anitrend.data.cache.datasource.CacheLocalSource
import co.anitrend.data.cache.model.CacheIdentity
import co.anitrend.data.cache.model.CacheRequest
import co.anitrend.data.cache.repository.CacheStorePolicy
import org.threeten.bp.Instant

internal class AiringCache(
    override val localSource: CacheLocalSource,
    override val request: CacheRequest = CacheRequest.AIRING
) : CacheStorePolicy() {
    /**
     * Check if a resource with a given [identity] is permitted to refresh
     *
     * @param identity Unique identifier for the cache item
     * @param expiresAfter defaults to 2 hours
     */
    override suspend fun shouldRefresh(
        identity: CacheIdentity,
        expiresAfter: Instant
    ): Boolean = isRequestBefore(identity, expiresAfter)

    sealed class Identity : CacheIdentity {

        class Paged(
            override val id: Long = 0,
            override val key: String = "airing_schedule_paged"
        ) : Identity()
    }
}