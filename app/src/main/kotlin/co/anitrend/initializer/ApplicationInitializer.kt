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

package co.anitrend.initializer

import android.content.Context
import androidx.startup.Initializer
import co.anitrend.core.initializer.injector.InjectorInitializer
import co.anitrend.core.initializer.contract.AbstractCoreInitializer
import co.anitrend.core.koin.helper.DynamicFeatureModuleHelper.Companion.loadModules
import co.anitrend.koin.appModules

class ApplicationInitializer : AbstractCoreInitializer<Unit>() {

    /**
     * Initializes and a component given the application [Context]
     *
     * @param context The application context.
     */
    override fun create(context: Context) {
        appModules.loadModules()
    }

    /**
     * @return A list of dependencies that this [Initializer] depends on. This is
     * used to determine initialization order of [Initializer]s.
     *
     * For e.g. if a [Initializer] `B` defines another
     * [Initializer] `A` as its dependency, then `A` gets initialized before `B`.
     */
    override fun dependencies(): List<Class<out Initializer<*>>> =
        listOf(InjectorInitializer::class.java)
}