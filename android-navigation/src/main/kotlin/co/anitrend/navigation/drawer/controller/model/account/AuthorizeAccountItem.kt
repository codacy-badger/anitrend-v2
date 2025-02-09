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

package co.anitrend.navigation.drawer.controller.model.account

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.anitrend.arch.extension.ext.getCompatDrawable
import co.anitrend.arch.recycler.action.contract.ISupportSelectionMode
import co.anitrend.arch.recycler.common.ClickableItem
import co.anitrend.arch.recycler.holder.SupportViewHolder
import co.anitrend.core.android.recycler.model.RecyclerItemBinding
import co.anitrend.navigation.AuthRouter
import co.anitrend.navigation.drawer.R
import co.anitrend.navigation.drawer.databinding.AccountAnonymousItemBinding
import co.anitrend.navigation.drawer.model.account.Account
import co.anitrend.navigation.extensions.startActivity
import coil.load
import coil.request.Disposable
import kotlinx.coroutines.flow.MutableStateFlow

class AuthorizeAccountItem(
    private val entity: Account.Authorize
) : RecyclerItemBinding<AccountAnonymousItemBinding>(entity.id) {

    private var disposable: Disposable? = null

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
        binding = AccountAnonymousItemBinding.bind(view)
        requireBinding().accountUserName.setText(entity.titleRes)
        disposable = requireBinding().accountProfileImage.load(
            view.context.getCompatDrawable(R.drawable.ic_account_add_24dp, R.color.primaryTextColor)
        )
        requireBinding().accountContainer.setOnClickListener {
            AuthRouter.startActivity(it.context)
        }
    }

    /**
     * Called when the view needs to be recycled for reuse, clear any held references
     * to objects, stop any asynchronous work, e.t.c
     */
    override fun unbind(view: View) {
        binding?.accountContainer?.setOnClickListener(null)
        disposable?.dispose()
        disposable = null
        super.unbind(view)
    }

    companion object {
        internal fun LayoutInflater.createAuthorizeAccountViewHolder(
            viewGroup: ViewGroup
        ) = AccountAnonymousItemBinding.inflate(
            this, viewGroup, false
        ).let { SupportViewHolder(it) }
    }
}