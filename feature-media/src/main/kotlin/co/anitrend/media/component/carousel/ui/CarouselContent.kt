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

package co.anitrend.media.component.carousel.ui

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import co.anitrend.arch.extension.util.attribute.SeasonType
import co.anitrend.arch.extension.util.date.SupportDateHelper
import co.anitrend.arch.recycler.SupportRecyclerView
import co.anitrend.arch.recycler.adapter.contract.ISupportAdapter
import co.anitrend.arch.ui.view.widget.model.StateLayoutConfig
import co.anitrend.core.ui.fragment.list.AniTrendListFragment
import co.anitrend.core.ui.get
import co.anitrend.data.media.model.query.MediaCarouselQuery
import co.anitrend.domain.common.entity.contract.IEntity
import co.anitrend.domain.media.enums.MediaSeason
import co.anitrend.media.BuildConfig
import co.anitrend.media.R
import co.anitrend.media.component.carousel.viewmodel.CarouselViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.threeten.bp.Instant

class CarouselContent(
    private val dateHelper: SupportDateHelper,
    override val stateConfig: StateLayoutConfig,
    override val supportViewAdapter: ISupportAdapter<IEntity>,
    override val defaultSpanSize: Int = R.integer.single_list_size
) : AniTrendListFragment<IEntity>() {

    private val viewModel by viewModel<CarouselViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val animator = object : DefaultItemAnimator() {
            override fun canReuseUpdatedViewHolder(
                viewHolder: RecyclerView.ViewHolder
            ) = false
        }
        animator.supportsChangeAnimations = false
        supportRecyclerView?.itemAnimator = animator
    }

    /**
     * Stub to trigger the loading of data, by default this is only called
     * when [supportViewAdapter] has no data in its underlying source.
     *
     * This is called when the fragment reaches it's [onStart] state
     *
     * @see initializeComponents
     */
    override fun onFetchDataInitialize() {
        val currentYear = dateHelper.getCurrentYear()
        val currentSeason = when (dateHelper.currentSeason) {
            SeasonType.WINTER -> MediaSeason.WINTER
            SeasonType.SPRING -> MediaSeason.SPRING
            SeasonType.SUMMER -> MediaSeason.SUMMER
            SeasonType.FALL -> MediaSeason.FALL
        }
        val mediaCarouselQuery = MediaCarouselQuery(
            season = currentSeason,
            seasonYear = currentYear,
            nextYear = when (currentSeason) {
                MediaSeason.FALL -> currentYear + 1
                MediaSeason.SPRING -> currentYear
                MediaSeason.SUMMER -> currentYear
                MediaSeason.WINTER -> currentYear
            },
            nextSeason = when (dateHelper.currentSeason) {
                SeasonType.WINTER -> MediaSeason.SPRING
                SeasonType.SPRING -> MediaSeason.SUMMER
                SeasonType.SUMMER -> MediaSeason.FALL
                SeasonType.FALL -> MediaSeason.WINTER
            },
            currentTime = Instant.now().epochSecond
        )
        viewModel.state(
            mediaCarouselQuery
        )
    }

    /**
     * Invoke view model observer to watch for changes, this will be called
     * called in [onViewCreated]
     */
    override fun setUpViewModelObserver() {
        viewModelState().model.observe(viewLifecycleOwner) {
            onPostModelChange(it)
        }
    }

    /**
     * Proxy for a view model state if one exists
     */
    override fun viewModelState() = viewModel.state
}