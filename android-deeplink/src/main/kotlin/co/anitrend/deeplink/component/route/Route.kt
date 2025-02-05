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

package co.anitrend.deeplink.component.route

import android.content.Intent
import com.hellofresh.deeplink.BaseRoute
import com.hellofresh.deeplink.DeepLinkUri
import com.hellofresh.deeplink.Environment
import timber.log.Timber

/**
 * Application deep link router for handling anitrend and web URIs
 */
abstract class Route(vararg routes: String) : BaseRoute<Intent?>(*routes) {

    /**
     * Called when the application receives a matching deep link from [routes]
     *
     * @param uri Full URI including paths and variables that match [routes]
     * @param params Variables available in the uri path e.g `segment/:param`
     * @param env Application environment
     */
    override fun run(
        uri: DeepLinkUri,
        params: Map<String, String>,
        env: Environment
    ): Intent? {
        Timber.d("Deep link matcher found!")
        val entries = params.entries.joinToString()
        Timber.d("Attempting to resolve -> uri: $uri | params: $entries")
        return null
    }
}
