/*
 * Copyright (C) 2019  AniTrend
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

package co.anitrend.data.core.api.interceptor

import co.anitrend.data.auth.helper.contract.IAuthenticationHelper
import kotlinx.coroutines.runBlocking
import okhttp3.*

/**
 * Authentication interceptor add headers dynamically when the application is authenticated.
 * The context in which an [Interceptor] may be  parallel or asynchronous depending
 * on the dispatching caller, as such take care to assure thread safety
 */
internal class GraphAuthenticator(
    private val authenticationHelper: IAuthenticationHelper
) : Authenticator {

    /**
     * Returns a request that includes a credential to satisfy an authentication challenge in `response`.
     * Returns null if the challenge cannot be satisfied.
     *
     *
     * The route is best effort, it currently may not always be provided even when logically
     * available. It may also not be provided when an authenticator is re-used manually in an
     * application interceptor, such as when implementing client-specific retries.
     */
    override fun authenticate(route: Route?, response: Response): Request {
        val requestBuilder = response.request.newBuilder()
        if (response.code == UNAUTHORIZED) {
            if (authenticationHelper.isAuthenticated) {
                /**
                 * TODO: Track the number of failed requests, given that the auth header exists and logout the user
                 */
                runBlocking {
                    authenticationHelper.invalidateAuthenticationState()
                }
            }
        }
        return requestBuilder.build()
    }

    companion object {
        private const val UNAUTHORIZED = 401
    }
}
