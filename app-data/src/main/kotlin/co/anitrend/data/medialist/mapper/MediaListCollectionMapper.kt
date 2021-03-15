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

package co.anitrend.data.medialist.mapper

import co.anitrend.data.arch.mapper.DefaultMapper
import co.anitrend.data.arch.railway.OutCome
import co.anitrend.data.arch.railway.extension.evaluate
import co.anitrend.data.arch.railway.extension.otherwise
import co.anitrend.data.arch.railway.extension.then
import co.anitrend.data.medialist.converter.MediaListModelConverter
import co.anitrend.data.medialist.datasource.local.MediaListLocalSource
import co.anitrend.data.medialist.entity.MediaListEntity
import co.anitrend.data.medialist.model.container.MediaListContainerModel
import co.anitrend.data.user.converter.UserModelConverter
import co.anitrend.data.user.datasource.local.UserLocalSource
import co.anitrend.data.user.model.UserModel
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

internal class MediaListCollectionMapper(
    private val localSource: MediaListLocalSource,
    private val converter: MediaListModelConverter,
    private val userConverter: UserModelConverter,
    private val userLocalSource: UserLocalSource,
    private val context: CoroutineContext
) : DefaultMapper<MediaListContainerModel.Collection, List<MediaListEntity>>() {

    private suspend fun saveUser(userModel: UserModel) {
        val entity = userConverter.convertFrom(userModel)
        withContext(context) {
            userLocalSource.upsert(entity)
        }
    }

    /**
     * Handles the persistence of [data] into a local source
     *
     * @return [OutCome.Pass] or [OutCome.Fail] of the operation
     */
    override suspend fun persistChanges(data: List<MediaListEntity>): OutCome<Nothing?> {
        return runCatching {
            localSource.upsert(data)
            OutCome.Pass(null)
        }.getOrElse { OutCome.Fail(listOf(it)) }
    }

    /**
     * Inserts the given object into the implemented room database,
     *
     * @param mappedData mapped object from [onResponseMapFrom] to insert into the database
     */
    override suspend fun onResponseDatabaseInsert(mappedData: List<MediaListEntity>) {
        mappedData evaluate
                ::checkValidity then
                ::persistChanges otherwise
                ::handleException
    }

    /**
     * Creates mapped objects and handles the database operations which may be required to map various objects,
     *
     * @param source the incoming data source type
     * @return mapped object that will be consumed by [onResponseDatabaseInsert]
     */
    override suspend fun onResponseMapFrom(source: MediaListContainerModel.Collection): List<MediaListEntity> {
        source.user?.also { user ->
            saveUser(user)
        }
        val mediaList = source.lists.flatMap { it.entries.orEmpty() }
        return converter.convertFrom(mediaList)
    }
}