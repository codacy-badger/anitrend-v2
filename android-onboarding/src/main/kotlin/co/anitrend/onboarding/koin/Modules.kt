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

package co.anitrend.onboarding.koin

import co.anitrend.core.koin.helper.DynamicFeatureModuleHelper
import co.anitrend.navigation.OnBoardingRouter
import co.anitrend.onboarding.component.presenter.OnBoardingPresenter
import co.anitrend.onboarding.provider.FeatureProvider
import co.anitrend.onboarding.component.screen.OnBoardingScreen
import co.anitrend.onboarding.component.content.OnBoardingContent
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.fragment.dsl.fragment
import org.koin.dsl.module

private val presenterModule = module {
    scope<OnBoardingScreen> {
        scoped {
            OnBoardingPresenter(
                context = androidContext(),
                settings = get(),
                controller = get()
            )
        }
    }
}

private val fragmentModule = module {
    scope<OnBoardingScreen> {
        fragment {
            OnBoardingContent()
        }
    }
}

private val featureModule = module {
    factory<OnBoardingRouter.Provider> {
        FeatureProvider()
    }
}

internal val moduleHelper = DynamicFeatureModuleHelper(
    listOf(presenterModule, fragmentModule, featureModule)
)