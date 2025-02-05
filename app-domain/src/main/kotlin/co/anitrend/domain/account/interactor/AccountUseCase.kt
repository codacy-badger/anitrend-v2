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

package co.anitrend.domain.account.interactor

import co.anitrend.arch.domain.common.IUseCase
import co.anitrend.arch.domain.state.UiState
import co.anitrend.domain.account.model.AccountParam
import co.anitrend.domain.account.repository.AccountRepository

abstract class AccountUseCase<State: UiState<*>>(
    protected val repository: AccountRepository<State>
) : IUseCase {

    /**
     * @return Authenticated users or null
     */
    fun getAuthorizedAccounts() =
        repository.getAccountUsers()

    fun signOut(param: AccountParam.SignOut) =
        repository.signOut(param)
}