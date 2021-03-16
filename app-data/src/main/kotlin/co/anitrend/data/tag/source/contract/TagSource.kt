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

package co.anitrend.data.tag.source.contract

import co.anitrend.arch.data.request.callback.RequestCallback
import co.anitrend.arch.data.source.core.SupportCoreDataSource
import co.anitrend.data.cache.extensions.invoke
import co.anitrend.data.cache.model.CacheIdentity
import co.anitrend.data.cache.repository.contract.ICacheStorePolicy
import co.anitrend.data.tag.cache.TagCache
import co.anitrend.domain.tag.entity.Tag
import kotlinx.coroutines.flow.Flow

internal abstract class TagSource : SupportCoreDataSource() {

    protected val cacheIdentity: CacheIdentity = TagCache.Identity.TAG

    protected abstract val cachePolicy: ICacheStorePolicy

    protected abstract fun observable(): Flow<List<Tag>>

    protected abstract suspend fun getTags(callback: RequestCallback): Boolean

    internal operator fun invoke(): Flow<List<Tag>> {
        cachePolicy(
            scope = scope,
            requestHelper = requestHelper,
            cacheIdentity = cacheIdentity,
            block = ::getTags
        )

        return observable()
    }
}