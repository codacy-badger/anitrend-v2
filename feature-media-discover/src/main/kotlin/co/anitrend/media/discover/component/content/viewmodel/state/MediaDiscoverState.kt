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

package co.anitrend.media.discover.component.content.viewmodel.state

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.asLiveData
import androidx.paging.PagedList
import co.anitrend.arch.core.model.ISupportViewModelState
import co.anitrend.arch.data.state.DataState
import co.anitrend.data.media.GetNetworkMediaInteractor
import co.anitrend.data.media.GetPagedMediaInteractor
import co.anitrend.domain.media.entity.Media
import co.anitrend.domain.media.model.MediaParam
import co.anitrend.navigation.MediaDiscoverRouter
import kotlin.coroutines.CoroutineContext
import kotlin.properties.Delegates

data class MediaDiscoverState(
    private val pagedInteractor: GetPagedMediaInteractor,
    private val networkInteractor: GetNetworkMediaInteractor
) : ISupportViewModelState<PagedList<Media>> {

    var context by Delegates.notNull<CoroutineContext>()

    private val useCaseResult = MutableLiveData<DataState<PagedList<Media>>>()

    override val model = Transformations.switchMap(useCaseResult) {
        it.model.asLiveData(context)
    }

    override val loadState = Transformations.switchMap(useCaseResult) {
        it.loadState.asLiveData(context)
    }

    override val refreshState = Transformations.switchMap(useCaseResult) {
        it.refreshState.asLiveData(context)
    }

    operator fun invoke(param: MediaDiscoverRouter.Param, onNetwork: Boolean = false) {
        val query = MediaParam.Find() builder {
            averageScore = param.averageScore
            averageScore_greater = param.averageScore_greater
            averageScore_lesser = param.averageScore_lesser
            averageScore_not = param.averageScore_not
            chapters = param.chapters
            chapters_greater = param.chapters_greater
            chapters_lesser = param.chapters_lesser
            countryOfOrigin = param.countryOfOrigin
            duration = param.duration
            duration_greater = param.duration_greater
            duration_lesser = param.duration_lesser
            endDate = param.endDate
            endDate_greater = param.endDate_greater
            endDate_lesser = param.endDate_lesser
            endDate_like = param.endDate_like
            episodes = param.episodes
            episodes_greater = param.episodes_greater
            episodes_lesser = param.episodes_lesser
            format = param.format
            format_in = param.format_in
            format_not = param.format_not
            format_not_in = param.format_not_in
            genre = param.genre
            genre_in = param.genre_in
            genre_not_in = param.genre_not_in
            id = param.id
            idMal = param.idMal
            idMal_in = param.idMal_in
            idMal_not = param.idMal_not
            idMal_not_in = param.idMal_not_in
            id_in = param.id_in
            id_not = param.id_not
            id_not_in = param.id_not_in
            isAdult = param.isAdult
            licensedBy = param.licensedBy
            licensedBy_in = param.licensedBy_in
            minimumTagRank = param.minimumTagRank
            onList = param.onList
            popularity = param.popularity
            popularity_greater = param.popularity_greater
            popularity_lesser = param.popularity_lesser
            popularity_not = param.popularity_not
            search = param.search
            season = param.season
            seasonYear = param.seasonYear
            sort = param.sort
            source = param.source
            source_in = param.source_in
            startDate = param.startDate
            startDate_greater = param.startDate_greater
            startDate_lesser = param.startDate_lesser
            startDate_like = param.startDate_like
            status = param.status
            status_in = param.status_in
            status_not = param.status_not
            status_not_in = param.status_not_in
            tag = param.tag
            tagCategory = param.tagCategory
            tagCategory_in = param.tagCategory_in
            tagCategory_not_in = param.tagCategory_not_in
            tag_in = param.tag_in
            tag_not_in = param.tag_not_in
            type = param.type
            volumes = param.volumes
            volumes_greater = param.volumes_greater
            volumes_lesser = param.volumes_lesser
        }

        val result = if (!onNetwork)
            pagedInteractor(query)
        else
            networkInteractor(query)
        useCaseResult.postValue(result)
    }

    /**
     * Called upon [androidx.lifecycle.ViewModel.onCleared] and should optionally
     * call cancellation of any ongoing jobs.
     *
     * If your use case source is of type [co.anitrend.arch.domain.common.IUseCase]
     * then you could optionally call [co.anitrend.arch.domain.common.IUseCase.onCleared] here
     */
    override fun onCleared() {
        pagedInteractor.onCleared()
        networkInteractor.onCleared()
    }

    /**
     * Triggers use case to perform refresh operation
     */
    override suspend fun refresh() {
        val uiModel = useCaseResult.value
        uiModel?.refresh?.invoke()
    }

    /**
     * Triggers use case to perform a retry operation
     */
    override suspend fun retry() {
        val uiModel = useCaseResult.value
        uiModel?.retry?.invoke()
    }
}
