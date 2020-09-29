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

package co.anitrend.domain.media.interactor

import co.anitrend.arch.domain.common.IUseCase
import co.anitrend.arch.domain.state.UiState
import co.anitrend.domain.common.graph.IGraphPayload
import co.anitrend.domain.media.repository.MediaCarouselRepository
import co.anitrend.domain.media.repository.MediaRepository

abstract class MediaUseCase<State: UiState<*>>(
    protected val repository: MediaRepository<State>
) : IUseCase {
    fun getPagedMedia(query: IGraphPayload) =
        repository.getMediaPaged(query)

    fun getPagedMediaByNetwork(query: IGraphPayload) =
        repository.getMediaPagedByNetwork(query)
}

abstract class MediaCarouselUseCase<State: UiState<*>>(
    protected val repository: MediaCarouselRepository<State>
) : IUseCase {
    fun getMediaCarousel(query: IGraphPayload) =
        repository.getMediaCarousel(query)
}