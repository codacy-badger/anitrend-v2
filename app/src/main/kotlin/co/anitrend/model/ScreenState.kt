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

package co.anitrend.model

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.lifecycle.SavedStateHandle
import co.anitrend.R
import co.anitrend.arch.extension.ext.extra
import co.anitrend.core.android.state.IScreenState
import co.anitrend.navigation.NavigationDrawerRouter

internal class ScreenState(
    private val savedStateHandle: SavedStateHandle
) : IScreenState {

    private val navigationDestination by savedStateHandle.extra(
        NavigationDrawerRouter.Param.KEY,
        NavigationDrawerRouter.Destination.HOME
    )

    var shouldExit: Boolean = false

    @IdRes var selectedItem: Int = navigationDestination.toNavId()

    @StringRes var selectedTitle: Int = R.string.navigation_home

    override fun onSaveInstanceState(outState: Bundle) {
        savedStateHandle.set(ARG_KEY_SELECTED_ITEM, selectedItem)
        savedStateHandle.set(ARG_KEY_SELECTED_TITLE, selectedTitle)
        savedStateHandle.set(ARG_KEY_SHOULD_EXIT, shouldExit)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        if (savedStateHandle.contains(ARG_KEY_SELECTED_ITEM))
            selectedItem = requireNotNull(savedStateHandle.get<Int>(ARG_KEY_SELECTED_ITEM))
        if (savedStateHandle.contains(ARG_KEY_SELECTED_TITLE))
            selectedTitle = requireNotNull(savedStateHandle.get<Int>(ARG_KEY_SELECTED_TITLE))
        if (savedStateHandle.contains(ARG_KEY_SHOULD_EXIT))
            shouldExit = requireNotNull(savedStateHandle.get<Boolean>(ARG_KEY_SHOULD_EXIT))
    }

    companion object {
        internal const val ARG_KEY_SHOULD_EXIT = "ARG_KEY_SHOULD_EXIT"

        internal const val ARG_KEY_SELECTED_ITEM = "ARG_KEY_NAVIGATION_STATE"
        internal const val ARG_KEY_SELECTED_TITLE = "ARG_KEY_NAVIGATION_STATE"

        /**
         * Converts [NavigationDrawerRouter.Destination] to a navigation [IdRes]
         */
        @IdRes
        internal fun NavigationDrawerRouter.Destination.toNavId() = when (this) {
            NavigationDrawerRouter.Destination.HOME -> R.id.navigation_home
            NavigationDrawerRouter.Destination.DISCOVER -> R.id.navigation_discover
            NavigationDrawerRouter.Destination.SOCIAL -> R.id.navigation_social
            NavigationDrawerRouter.Destination.REVIEWS -> R.id.navigation_reviews
            NavigationDrawerRouter.Destination.SUGGESTIONS -> R.id.navigation_suggestions
            NavigationDrawerRouter.Destination.ANIME_LIST -> R.id.navigation_anime_list
            NavigationDrawerRouter.Destination.MANGA_LIST -> R.id.navigation_manga_list
            NavigationDrawerRouter.Destination.NEWS -> R.id.navigation_news
            NavigationDrawerRouter.Destination.FORUMS -> R.id.navigation_forum
            NavigationDrawerRouter.Destination.EPISODES -> R.id.navigation_episodes
        }
    }
}