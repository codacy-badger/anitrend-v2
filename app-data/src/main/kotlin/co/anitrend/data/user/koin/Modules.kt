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

package co.anitrend.data.user.koin

import co.anitrend.data.android.extensions.graphQLController
import co.anitrend.data.auth.mapper.AuthMapper
import co.anitrend.data.core.extensions.graphApi
import co.anitrend.data.core.extensions.store
import co.anitrend.data.user.*
import co.anitrend.data.user.cache.UserCache
import co.anitrend.data.user.converter.*
import co.anitrend.data.user.mapper.UserMapper
import co.anitrend.data.user.repository.UserRepository
import co.anitrend.data.user.source.UserSourceImpl
import co.anitrend.data.user.source.contract.UserSource
import co.anitrend.data.user.usecase.UserInteractor
import org.koin.dsl.module

private val sourceModule = module {
    factory<UserSource.Identifier> {
        UserSourceImpl.Identifier(
            remoteSource = graphApi(),
            localSource = store().userDao(),
            clearDataHelper = get(),
            controller = graphQLController(
                mapper = get<UserMapper.User>()
            ),
            converter = get(),
            cachePolicy = get<UserCache.Identifier>(),
            dispatcher = get()
        )
    }
    factory<UserSource.Authenticated> {
        UserSourceImpl.Authenticated(
            remoteSource = graphApi(),
            localSource = store().userDao(),
            clearDataHelper = get(),
            controller = graphQLController(
                mapper = get<AuthMapper>()
            ),
            converter = get(),
            settings = get(),
            cachePolicy = get<UserCache.Profile>(),
            dispatcher = get()
        )
    }
    factory<UserSource.Search> {
        UserSourceImpl.Search(
            remoteSource = graphApi(),
            localSource = store().userDao(),
            clearDataHelper = get(),
            controller = graphQLController(
                mapper = get<UserMapper.Paged>()
            ),
            converter = get(),
            dispatcher = get()
        )
    }
    factory<UserSource.Profile> {
        UserSourceImpl.Profile(
            remoteSource = graphApi(),
            localSource = store().userDao(),
            clearDataHelper = get(),
            controller = graphQLController(
                mapper = get<UserMapper.Profile>()
            ),
            converter = get(),
            cachePolicy = get<UserCache.Profile>(),
            dispatcher = get()
        )
    }
    factory<UserSource.Statistic> {
        UserSourceImpl.Statistic(
            remoteSource = graphApi(),
            localSource = store().userDao(),
            clearDataHelper = get(),
            controller = graphQLController(
                mapper = get<UserMapper.Statistic>()
            ),
            converter = get(),
            cachePolicy = get<UserCache.Statistic>(),
            dispatcher = get()
        )
    }
    factory<UserSource.ToggleFollow> {
        UserSourceImpl.ToggleFollow(
            remoteSource = graphApi(),
            localSource = store().userDao(),
            controller = graphQLController(
                mapper = get<UserMapper.User>()
            ),
            converter = get(),
            dispatcher = get()
        )
    }
    factory<UserSource.Update> {
        UserSourceImpl.Update(
            remoteSource = graphApi(),
            localSource = store().userDao(),
            controller = graphQLController(
                mapper = get<UserMapper.Profile>()
            ),
            converter = get(),
            settings = get(),
            dispatcher = get()
        )
    }
}

private val cacheModule = module {
    factory {
        UserCache.Identifier(
            localSource = store().cacheDao()
        )
    }
    factory {
        UserCache.Profile(
            localSource = store().cacheDao()
        )
    }
    factory {
        UserCache.Statistic(
            localSource = store().cacheDao()
        )
    }
}

private val converterModule = module {
    factory {
        UserEntityConverter()
    }
    factory {
        UserModelConverter()
    }
    factory {
        UserGeneralOptionModelConverter()
    }
    factory {
        UserMediaOptionModelConverter()
    }
    factory {
        UserViewEntityConverter()
    }
}

private val mapperModule = module {
    factory {
        UserMapper.Paged(
            localSource = store().userDao(),
            converter = get(),
        )
    }
    factory {
        UserMapper.Profile(
            generalOptionMapper = get(),
            mediaOptionMapper = get(),
            previousNameMapper = get(),
            localSource = store().userDao(),
            converter = get(),
        )
    }
    factory {
        UserMapper.User(
            localSource = store().userDao(),
            converter = get(),
        )
    }
    factory {
        UserMapper.Embed(
            localSource = store().userDao(),
            converter = get(),
        )
    }
    factory {
        UserMapper.MediaOptionEmbed(
            localSource = store().userMediaOptionDao(),
            converter = get(),
        )
    }
    factory {
        UserMapper.GeneralOptionEmbed(
            localSource = store().userGeneralOptionDao(),
            converter = get(),
        )
    }
    factory {
        UserMapper.PreviousNameEmbed(
            localSource = store().userPreviousNameDao()
        )
    }
}

private val useCaseModule = module {
    factory<GetUserInteractor> {
        UserInteractor.Identifier(
            repository = get()
        )
    }
    factory<GetUserPagedInteractor> {
        UserInteractor.Paged(
            repository = get()
        )
    }
    factory<GetProfileInteractor> {
        UserInteractor.Profile(
            repository = get()
        )
    }
    factory<GetAuthenticatedInteractor> {
        UserInteractor.Authenticated(
            repository = get()
        )
    }
    factory<ToggleFollowInteractor> {
        UserInteractor.ToggleFollow(
            repository = get()
        )
    }
    factory<UpdateProfileInteractor> {
        UserInteractor.Update(
            repository = get()
        )
    }
}

private val repositoryModule = module {
    factory<UserIdentifierRepository> {
        UserRepository.Identifier(
            source = get()
        )
    }
    factory<UserAuthenticatedRepository> {
        UserRepository.Authenticated(
            source = get()
        )
    }
    factory<UserSearchRepository> {
        UserRepository.Search(
            source = get()
        )
    }
    factory<UserProfileRepository> {
        UserRepository.Profile(
            source = get()
        )
    }
    factory<UserFollowRepository> {
        UserRepository.ToggleFollow(
            source = get()
        )
    }
    factory<UserUpdateRepository> {
        UserRepository.Update(
            source = get()
        )
    }
}

internal val userModules = listOf(
    converterModule,
    sourceModule,
    cacheModule,
    mapperModule,
    useCaseModule,
    repositoryModule
)