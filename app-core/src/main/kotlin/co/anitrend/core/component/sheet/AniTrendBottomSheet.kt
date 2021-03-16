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

package co.anitrend.core.component.sheet

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import androidx.annotation.LayoutRes
import androidx.annotation.MenuRes
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.viewbinding.ViewBinding
import co.anitrend.arch.core.model.ISupportViewModelState
import co.anitrend.arch.extension.ext.UNSAFE
import co.anitrend.arch.ui.fragment.contract.ISupportFragment
import co.anitrend.core.R
import co.anitrend.core.android.binding.IBindingView
import co.anitrend.core.android.components.sheet.SheetBehaviourCallback
import co.anitrend.core.extensions.stackTrace
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import org.koin.androidx.scope.fragmentScope
import org.koin.core.scope.KoinScopeComponent

abstract class AniTrendBottomSheet<B : ViewBinding>(
    @MenuRes protected open val inflateMenu: Int = ISupportFragment.NO_MENU_ITEM,
    @LayoutRes protected open val inflateLayout: Int = ISupportFragment.NO_LAYOUT_ITEM
) : BottomSheetDialogFragment(),
    KoinScopeComponent, IBindingView<B>, CoroutineScope by MainScope(), ISupportFragment {

    override val moduleTag: String = javaClass.simpleName

    override var binding: B? = null

    override val scope by lazy(UNSAFE) { fragmentScope() }

    protected val bottomSheetCallback = SheetBehaviourCallback()

    private fun applyMargins(viewParent: ViewParent) {
        runCatching {
            val parent = viewParent as View
            val params = parent.layoutParams as CoordinatorLayout.LayoutParams
            val width = resources.getDimensionPixelSize(R.dimen.bottom_sheet_margin)
            params.setMargins(width, 0, width, 0)
            parent.layoutParams = params
        }.stackTrace(moduleTag)
    }

    /**
     * Invoke view model observer to watch for changes, this will be called
     * called in [onViewCreated]
     */
    protected abstract fun setUpViewModelObserver()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        initializeComponents(savedInstanceState)
        return dialog
    }

    /**
     * Called to have the fragment instantiate its user interface view.
     * This is optional, and non-graphical fragments can return null. This will be called between
     * [onCreate] and [onViewCreated].
     *
     * A default View can be returned by calling [Fragment] in your
     * constructor. Otherwise, this method returns null.
     *
     * It is recommended to **only** inflate the layout in this method and move
     * logic that operates on the returned View to [onViewCreated].
     *
     * If you return a View from here, you will later be called in
     * [onDestroyView] when the view is being released.
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return Return the View for the fragment's UI, or null.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if (inflateLayout != ISupportFragment.NO_LAYOUT_ITEM) {
            inflater.inflate(inflateLayout, container, false)
        } else super.onCreateView(inflater, container, savedInstanceState)
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
        applyMargins(view.parent)
        setUpViewModelObserver()
    }

    /**
     * Proxy for a view model state if one exists
     */
    override fun viewModelState(): ISupportViewModelState<*>? = null

    /**
     * Called when the fragment is no longer in use. This is called
     * after [onStop] and before [onDetach].
     */
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}