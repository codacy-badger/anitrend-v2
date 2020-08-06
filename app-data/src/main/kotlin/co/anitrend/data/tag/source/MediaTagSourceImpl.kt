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

package co.anitrend.data.tag.source

import co.anitrend.arch.data.request.callback.RequestCallback
import co.anitrend.arch.extension.dispatchers.SupportDispatchers
import co.anitrend.data.arch.controller.strategy.policy.OnlineStrategy
import co.anitrend.data.arch.extension.controller
import co.anitrend.data.arch.helper.data.ClearDataHelper
import co.anitrend.data.tag.converter.TagEntityConverter
import co.anitrend.data.tag.datasource.local.MediaTagLocalSource
import co.anitrend.data.tag.datasource.remote.MediaTagRemoteSource
import co.anitrend.data.tag.entity.TagEntity
import co.anitrend.data.tag.mapper.MediaTagResponseMapper
import co.anitrend.data.tag.source.contract.MediaTagSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

internal class MediaTagSourceImpl(
    private val remoteSource: MediaTagRemoteSource,
    private val localSource: MediaTagLocalSource,
    private val mapper: MediaTagResponseMapper,
    private val clearDataHelper: ClearDataHelper,
    converter: TagEntityConverter = TagEntityConverter(),
    dispatchers: SupportDispatchers
) : MediaTagSource(dispatchers) {

    private val strategy = OnlineStrategy.create<List<TagEntity>>()

    override val observable =
        localSource.findAllFlow().map {
            converter.convertFrom(it)
        }.flowOn(dispatchers.computation)


    override suspend fun getTags(callback: RequestCallback) {
        val deferred = async {
            remoteSource.getMediaTags()
        }

        val controller = mapper.controller(dispatchers, strategy)

        controller(deferred, callback)
    }

    /**
     * Clears data sources (databases, preferences, e.t.c)
     */
    override suspend fun clearDataSource(context: CoroutineDispatcher) {
        clearDataHelper(context) {
            localSource.clear()
        }
    }
}