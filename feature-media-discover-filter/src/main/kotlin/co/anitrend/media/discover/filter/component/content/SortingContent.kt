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

package co.anitrend.media.discover.filter.component.content

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import co.anitrend.arch.extension.ext.argument
import co.anitrend.core.android.extensions.createChipChoice
import co.anitrend.core.component.content.AniTrendContent
import co.anitrend.domain.common.sort.order.SortOrder
import co.anitrend.domain.media.enums.MediaSort
import co.anitrend.media.discover.filter.R
import co.anitrend.media.discover.filter.databinding.MediaDiscoverFilterSortingBinding
import co.anitrend.navigation.MediaDiscoverRouter
import kotlinx.coroutines.launch

internal class SortingContent(
    override val inflateLayout: Int = R.layout.media_discover_filter_sorting
) : AniTrendContent<MediaDiscoverFilterSortingBinding>() {

    private val param by argument(
        MediaDiscoverRouter.Param.KEY,
        MediaDiscoverRouter.Param()
    )

    private fun bindModelToViews() {
        requireBinding().sortByAscending.isChecked = param.sort?.any {
            it.order == SortOrder.ASC
        } == true
    }


    private fun initializeViewsWithOptions() {
        MediaSort.values().forEach {
            requireBinding().sortChipGroup.addView(
                requireContext().createChipChoice {
                    text = it.alias
                    isChecked = param.sort?.map { sorting ->
                        sorting.sortable
                    }?.contains(it) == true
                }
            )
        }
    }

    /**
     * Additional initialization to be done in this method, this method will be called in
     * [androidx.fragment.app.FragmentActivity.onCreate].
     *
     * **N.B.** Calling super of this will register a connectivity change listener, so only
     * call `super.initializeComponents` if you require this behavior
     *
     * @param savedInstanceState
     */
    override fun initializeComponents(savedInstanceState: Bundle?) {

    }

    /**
     * Invoke view model observer to watch for changes, this will be called
     * called in [onViewCreated]
     */
    override fun setUpViewModelObserver() {

    }

    /**
     * Called immediately after [onCreateView] has returned, but before any saved state has been
     * restored in to the view. This gives subclasses a chance to initialize themselves once
     * they know their view hierarchy has been completely created.
     *
     * The fragment's view hierarchy is not however attached to its parent at this point.
     *
     * @param view The View returned by [onCreateView].
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous
     * saved state as given here.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = MediaDiscoverFilterSortingBinding.bind(view)
        lifecycleScope.launch {
            initializeViewsWithOptions()
            bindModelToViews()
        }
    }
}
