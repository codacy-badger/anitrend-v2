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

package co.anitrend.navigation.drawer.controller.model.navigation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.anitrend.arch.recycler.action.contract.ISupportSelectionMode
import co.anitrend.arch.recycler.common.ClickableItem
import co.anitrend.arch.recycler.holder.SupportViewHolder
import co.anitrend.core.android.recycler.model.RecyclerItemBinding
import co.anitrend.navigation.drawer.databinding.NavigationDividerItemBinding
import co.anitrend.navigation.drawer.model.navigation.Navigation
import kotlinx.coroutines.flow.MutableStateFlow

class DividerNavigationItem(
    entity: Navigation.Divider
) : RecyclerItemBinding<NavigationDividerItemBinding>(entity.hashCode().toLong()) {

    /**
     * Called when the [view] needs to be setup, this could be to set click listeners,
     * assign text, load images, e.t.c
     *
     * @param view view that was inflated
     * @param position current position
     * @param payloads optional payloads which maybe empty
     * @param stateFlow observable to broadcast click events
     * @param selectionMode action mode helper or null if none was provided
     */
    override fun bind(
        view: View,
        position: Int,
        payloads: List<Any>,
        stateFlow: MutableStateFlow<ClickableItem>,
        selectionMode: ISupportSelectionMode<Long>?
    ) {
        binding = NavigationDividerItemBinding.bind(view)
    }

    companion object {
        internal fun LayoutInflater.createNavDividerViewHolder(
            viewGroup: ViewGroup
        ) = NavigationDividerItemBinding.inflate(
            this, viewGroup, false
        ).let { SupportViewHolder(it) }
    }
}