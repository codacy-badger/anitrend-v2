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

package co.anitrend.navigation

import android.text.SpannedString
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.fragment.app.Fragment
import androidx.work.ListenableWorker
import co.anitrend.domain.airing.enums.AiringSort
import co.anitrend.domain.common.DateInt
import co.anitrend.domain.common.DateLike
import co.anitrend.domain.common.entity.shared.FuzzyDate
import co.anitrend.domain.common.sort.contract.ISortWithOrder
import co.anitrend.domain.media.enums.*
import co.anitrend.domain.medialist.enums.MediaListSort
import co.anitrend.domain.medialist.enums.MediaListStatus
import co.anitrend.domain.medialist.enums.ScoreFormat
import co.anitrend.navigation.model.common.IParam
import co.anitrend.navigation.model.sorting.Sorting
import co.anitrend.navigation.provider.INavigationProvider
import co.anitrend.navigation.router.NavigationRouter
import co.anitrend.navigation.work.WorkSchedulerController
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import org.koin.core.component.inject

object MainRouter : NavigationRouter() {
    override val provider by inject<Provider>()

    interface Provider : INavigationProvider
}

object NavigationDrawerRouter : NavigationRouter() {
    override val provider by inject<Provider>()

    interface Provider : INavigationProvider {
        fun fragment(): Class<out Fragment>
    }

    fun forFragment() = provider.fragment()
}

object SplashRouter : NavigationRouter() {
    override val provider by inject<Provider>()

    interface Provider : INavigationProvider
}

object OnBoardingRouter : NavigationRouter() {
    override val provider by inject<Provider>()

    interface Provider : INavigationProvider {
        fun fragment(): Class<out Fragment>
    }

    fun forFragment() = provider.fragment()

    @Parcelize
    data class Param(
        @RawRes val resource: Int,
        @DrawableRes val background: Int,
        val title: @RawValue SpannedString,
        val subTitle: @RawValue SpannedString,
        val description: @RawValue SpannedString,
        @ColorRes val textColor: Int
    ) : IParam {
        @IgnoredOnParcel
        override val idKey = KEY

        companion object : IParam.IKey {
            override val KEY: String = "OnBoardingRouter#Param"
        }
    }
}

object SearchRouter : NavigationRouter() {
    override val provider by inject<Provider>()

    interface Provider : INavigationProvider
}

object SettingsRouter : NavigationRouter() {
    override val provider by inject<Provider>()

    interface Provider : INavigationProvider
}

object AboutRouter : NavigationRouter() {
    override val provider by inject<Provider>()

    interface Provider : INavigationProvider
}

object AiringRouter : NavigationRouter() {
    override val provider by inject<Provider>()

    interface Provider : INavigationProvider {
        fun fragment(): Class<out Fragment>
    }

    fun forFragment() = provider.fragment()

    /** [AiringSchedule query][https://anilist.github.io/ApiV2-GraphQL-Docs/query.doc.html]
     *
     * @param id Filter by the id of the airing schedule item
     * @param mediaId Filter by the id of associated media
     * @param episode Filter by the airing episode number
     * @param airingAt Filter by the time of airing
     * @param notYetAired Filter to episodes that haven't yet aired
     * @param id_not Filter by the id of the airing schedule item
     * @param id_in Filter by the id of the airing schedule item
     * @param id_not_in Filter by the id of the airing schedule item
     * @param mediaId_not Filter by the id of associated media
     * @param mediaId_in Filter by the id of associated media
     * @param mediaId_not_in Filter by the id of associated media
     * @param episode_not Filter by the airing episode number
     * @param episode_in Filter by the airing episode number
     * @param episode_not_in Filter by the airing episode number
     * @param episode_greater Filter by the airing episode number
     * @param episode_lesser Filter by the airing episode number
     * @param airingAt_greater Filter by the time of airing
     * @param airingAt_lesser Filter by the time of airing
     * @param sort The order the results will be returned in
     */
    @Parcelize
    data class Param(
        var id: Long? = null,
        var mediaId: Long? = null,
        var episode: Int? = null,
        var airingAt: Int? = null,
        var notYetAired: Boolean? = null,
        var id_not: Long? = null,
        var id_in: List<Long>? = null,
        var id_not_in: List<Long>? = null,
        var mediaId_not: Long? = null,
        var mediaId_in: List<Long>? = null,
        var mediaId_not_in: List<Long>? = null,
        var episode_not: Int? = null,
        var episode_in: List<Int>? = null,
        var episode_not_in: List<Int>? = null,
        var episode_greater: Int? = null,
        var episode_lesser: Int? = null,
        var airingAt_greater: Int? = null,
        var airingAt_lesser: Int? = null,
        var sort: List<Sorting<AiringSort>>? = null
    ) : IParam {
        @IgnoredOnParcel
        override val idKey = KEY

        infix fun builder(param: Param.() -> Unit): Param {
            this.param()
            return this
        }

        companion object : IParam.IKey {
            override val KEY: String = "AiringRouter#Param"
        }
    }
}

object AuthRouter : NavigationRouter() {
    override val provider by inject<Provider>()

    interface Provider : INavigationProvider {
        fun fragment(): Class<out Fragment>
    }

    fun forFragment() = provider.fragment()
}

object MediaRouter : NavigationRouter() {
    override val provider by inject<Provider>()

    interface Provider : INavigationProvider {
        fun fragment(): Class<out Fragment>
    }

    fun forFragment() = provider.fragment()

    @Parcelize
    data class Param(
        val id: Long,
        val type: MediaType,
    ) : IParam {
        @IgnoredOnParcel
        override val idKey = KEY

        companion object : IParam.IKey {
            override val KEY = "MediaRouter#Param"
        }
    }
}

object MediaDiscoverRouter : NavigationRouter() {
    override val provider by inject<Provider>()

    interface Provider : INavigationProvider {
        fun fragment(): Class<out Fragment>
        fun sheet(): Class<out Fragment>
    }

    fun forFragment() = provider.fragment()
    fun forSheet() = provider.sheet()

    @Parcelize
    data class Param(
        var averageScore: Int? = null,
        var averageScore_greater: Int? = null,
        var averageScore_lesser: Int? = null,
        var averageScore_not: Int? = null,
        var chapters: Int? = null,
        var chapters_greater: Int? = null,
        var chapters_lesser: Int? = null,
        var countryOfOrigin: CharSequence? = null,
        var duration: Int? = null,
        var duration_greater: Int? = null,
        var duration_lesser: Int? = null,
        var endDate: DateInt? = null,
        var endDate_greater: DateInt? = null,
        var endDate_lesser: DateInt? = null,
        var endDate_like: String? = null,
        var episodes: Int? = null,
        var episodes_greater: Int? = null,
        var episodes_lesser: Int? = null,
        var format: MediaFormat? = null,
        var format_in: List<MediaFormat>? = null,
        var format_not: MediaFormat? = null,
        var format_not_in: List<MediaFormat>? = null,
        var genre: String? = null,
        var genre_in: List<String>? = null,
        var genre_not_in: List<String>? = null,
        var id: Long? = null,
        var idMal: Long? = null,
        var idMal_in: List<Long>? = null,
        var idMal_not: Long? = null,
        var idMal_not_in: List<Long>? = null,
        var id_in: List<Long>? = null,
        var id_not: Long? = null,
        var id_not_in: List<Long>? = null,
        var isAdult: Boolean? = null,
        var licensedBy: MediaLicensor? = null,
        var licensedBy_in: List<MediaLicensor>? = null,
        var minimumTagRank: Int? = null,
        var onList: Boolean? = null,
        var popularity: Int? = null,
        var popularity_greater: Int? = null,
        var popularity_lesser: Int? = null,
        var popularity_not: Int? = null,
        var search: String? = null,
        var season: MediaSeason? = null,
        var seasonYear: Int? = null,
        var sort: List<Sorting<MediaSort>>? = null,
        var source: MediaSource? = null,
        var source_in: List<MediaSource>? = null,
        var startDate: DateInt? = null,
        var startDate_greater: DateInt? = null,
        var startDate_lesser: DateInt? = null,
        var startDate_like: String? = null,
        var status: MediaStatus? = null,
        var status_in: List<MediaStatus>? = null,
        var status_not: MediaStatus? = null,
        var status_not_in: List<MediaStatus>? = null,
        var tag: String? = null,
        var tagCategory: String? = null,
        var tagCategory_in: List<String>? = null,
        var tagCategory_not_in: List<String>? = null,
        var tag_in: List<String>? = null,
        var tag_not_in: List<String>? = null,
        var type: MediaType? = null,
        var volumes: Int? = null,
        var volumes_greater: Int? = null,
        var volumes_lesser: Int? = null
    ) : IParam {
        @IgnoredOnParcel
        override val idKey = KEY

        companion object : IParam.IKey {
            override val KEY = "MediaDiscoverRouter#Param"
        }

        infix fun builder(param: Param.() -> Unit): Param {
            this.param()
            return this
        }
    }
}

object MediaDiscoverFilterRouter : NavigationRouter() {
    override val provider by inject<Provider>()

    interface Provider : INavigationProvider {
        fun sorting(): Class<out Fragment>
        fun general(): Class<out Fragment>
        fun genre(): Class<out Fragment>
        fun tag(): Class<out Fragment>
    }

    fun forSorting() = provider.sorting()
    fun forGeneral() = provider.general()
    fun forGenre() = provider.genre()
    fun forTag() = provider.tag()

    /**
     * Filter action models
     */
    sealed class Action : IParam {

        companion object {
            const val KEY = "MediaDiscoverFilterRouter#Action"
        }

        @Parcelize
        data class Sort(
            var sort: List<Sorting<MediaSort>>
        ) : Action() {

            @IgnoredOnParcel
            override val idKey = KEY

            fun isDefault(): Boolean {
                return sort.isNullOrEmpty()
            }

            companion object : IParam.IKey {
                override val KEY = "Sort"
            }
        }

        @Parcelize
        data class General(
            var id: Long?
        ) : Action() {

            @IgnoredOnParcel
            override val idKey = KEY

            fun isDefault(): Boolean {
                return id == null
            }

            companion object : IParam.IKey {
                override val KEY = "General"
            }
        }

        @Parcelize
        data class Genre(
            var genre_in: List<String>? = null,
            var genre_not_in: List<String>? = null,
        ) : Action() {

            @IgnoredOnParcel
            override val idKey = KEY

            fun isDefault() = genre_in.isNullOrEmpty() && genre_not_in.isNullOrEmpty()

            companion object : IParam.IKey {
                override val KEY = "Genre"
            }
        }

        @Parcelize
        data class Tag(
            var tagCategory_in: List<String>? = null,
            var tagCategory_not_in: List<String>? = null,
            var tag_in: List<String>? = null,
            var tag_not_in: List<String>? = null
        ) : Action() {

            @IgnoredOnParcel
            override val idKey = KEY

            fun isDefault(): Boolean {
                return tagCategory_in.isNullOrEmpty() &&
                        tagCategory_not_in.isNullOrEmpty() &&
                        tag_in.isNullOrEmpty() &&
                        tag_not_in.isNullOrEmpty()
            }

            companion object : IParam.IKey {
                override val KEY = "Tag"
            }
        }
    }
}

object MediaCarouselRouter : NavigationRouter() {
    override val provider by inject<Provider>()

    interface Provider : INavigationProvider {
        fun fragment(): Class<out Fragment>
    }

    fun forFragment() = provider.fragment()
}

object CharacterRouter : NavigationRouter() {
    override val provider by inject<Provider>()

    interface Provider : INavigationProvider

    @Parcelize
    data class Param(
        val id: Long
    ) : IParam {
        @IgnoredOnParcel
        override val idKey = KEY

        companion object : IParam.IKey {
            override val KEY = "CharacterRouter#Param"
        }
    }
}

object CharacterDiscoverRouter : NavigationRouter() {
    override val provider by inject<Provider>()

    interface Provider : INavigationProvider

    @Parcelize
    data class Param(
        val id: Long
    ) : IParam {
        @IgnoredOnParcel
        override val idKey = KEY

        companion object : IParam.IKey {
            override val KEY = "CharacterDiscoverRouter#Param"
        }
    }
}

object StaffRouter : NavigationRouter() {
    override val provider by inject<Provider>()

    interface Provider : INavigationProvider

    @Parcelize
    data class Param(
        val id: Long
    ) : IParam {
        @IgnoredOnParcel
        override val idKey = KEY

        companion object : IParam.IKey {
            override val KEY = "StaffRouter#Param"
        }
    }
}

object StaffDiscoverRouter : NavigationRouter() {
    override val provider by inject<Provider>()

    interface Provider : INavigationProvider

    @Parcelize
    data class Param(
        val id: Long
    ) : IParam {
        @IgnoredOnParcel
        override val idKey = KEY

        companion object : IParam.IKey {
            override val KEY = "StaffDiscoverRouter#Param"
        }
    }
}

object ProfileRouter : NavigationRouter() {
    override val provider by inject<Provider>()

    interface Provider : INavigationProvider {
        fun fragment(): Class<out Fragment>
    }

    fun forFragment() = provider.fragment()

    @Parcelize
    data class Param(
        val userId: Long? = null,
        val userName: String? = null
    ) : IParam {
        @IgnoredOnParcel
        override val idKey = KEY

        companion object : IParam.IKey {
            override val KEY = "ProfileRouter#Param"
        }
    }
}

object ForumRouter : NavigationRouter() {
    override val provider by inject<Provider>()

    interface Provider : INavigationProvider {
        fun fragment(): Class<out Fragment>
    }

    fun forFragment() = provider.fragment()
}

object FeedRouter : NavigationRouter() {
    override val provider by inject<Provider>()

    interface Provider : INavigationProvider {
        fun fragment(): Class<out Fragment>
    }

    fun forFragment() = provider.fragment()
}

object ReviewRouter : NavigationRouter() {
    override val provider by inject<Provider>()

    interface Provider : INavigationProvider {
        fun fragment(): Class<out Fragment>
    }

    fun forFragment() = provider.fragment()
}

object NotificationRouter : NavigationRouter() {
    override val provider by inject<Provider>()

    interface Provider : INavigationProvider {
        fun fragment(): Class<out Fragment>
    }

    fun forFragment() = provider.fragment()
}

object NewsRouter : NavigationRouter() {
    override val provider by inject<Provider>()

    interface Provider : INavigationProvider {
        fun fragment(): Class<out Fragment>
    }

    fun forFragment() = provider.fragment()

    @Parcelize
    data class Param(
        val link: String,
        val title: String,
        val subTitle: String,
        val description: String?,
        val content: String
    ) : IParam {
        @IgnoredOnParcel
        override val idKey = KEY

        companion object : IParam.IKey {
            override val KEY = "NewsRouter#Param"
        }
    }
}

object EpisodeRouter : NavigationRouter() {
    override val provider by inject<Provider>()

    interface Provider : INavigationProvider {
        fun fragment(): Class<out Fragment>
        fun sheet(): Class<out Fragment>
    }

    fun forFragment() = provider.fragment()
    fun forSheet() = provider.sheet()

    @Parcelize
    data class Param(
        val id: Long
    ) : IParam {
        @IgnoredOnParcel
        override val idKey = KEY

        companion object : IParam.IKey {
            override val KEY = "EpisodeRouter#Param"
        }
    }
}

object SuggestionRouter : NavigationRouter() {
    override val provider by inject<Provider>()

    interface Provider : INavigationProvider {
        fun fragment(): Class<out Fragment>
    }

    fun forFragment() = provider.fragment()
}

object UpdaterRouter : NavigationRouter() {
    override val provider by inject<Provider>()

    interface Provider : INavigationProvider {
        fun fragment(): Class<out Fragment>
    }

    fun forFragment() = ProfileRouter.provider.fragment()
}

object MediaListRouter : NavigationRouter() {
    override val provider by inject<Provider>()

    interface Provider : INavigationProvider {
        fun fragment(): Class<out Fragment>
    }

    fun forFragment() = provider.fragment()

    @Parcelize
    data class Param(
        val customListName: String? = null,
        val mediaId_in: List<Int>? = null,
        val mediaId_not_in: List<Int>? = null,
        val isFollowing: Boolean? = null,
        val userId_in: List<Long>? = null,
        val compareWithAuthList: Boolean? = null,
        val scoreFormat: ScoreFormat = ScoreFormat.POINT_100,
        val type: MediaType,
        val userId: Long,
        val completedAt: DateInt? = null,
        val completedAt_greater: DateInt? = null,
        val completedAt_lesser: DateInt? = null,
        val completedAt_like: DateLike? = null,
        val notes: String? = null,
        val notes_like: String? = null,
        val sort: List<Sorting<MediaListSort>>? = null,
        val startedAt: DateInt? = null,
        val startedAt_greater: DateInt? = null,
        val startedAt_lesser: DateInt? = null,
        val startedAt_like: DateLike? = null,
        val status: MediaListStatus? = null,
        val status_in: List<MediaListStatus>? = null,
        val status_not: MediaListStatus? = null,
        val status_not_in: List<MediaListStatus>? = null,
    ) : IParam {
        @IgnoredOnParcel
        override val idKey = KEY

        companion object : IParam.IKey {
            override val KEY = "MediaListRouter#Param"
        }
    }
}

object ImageViewerRouter : NavigationRouter() {
    override val provider by inject<Provider>()

    interface Provider : INavigationProvider

    @Parcelize
    data class Param(
        val imageSrc: String
    ) : IParam {
        @IgnoredOnParcel
        override val idKey = KEY

        companion object : IParam.IKey {
            override val KEY = "ImageViewerRouter#Param"
        }
    }
}

object GenreTaskRouter : NavigationRouter() {
    override val provider by inject<Provider>()

    interface Provider : INavigationProvider {
        fun worker(): Class<out ListenableWorker>
        fun scheduler(): WorkSchedulerController
    }

    fun forWorker() = provider.worker()
    fun forScheduler() = provider.scheduler()
}

object TagTaskRouter : NavigationRouter() {
    override val provider by inject<Provider>()

    interface Provider : INavigationProvider {
        fun worker(): Class<out ListenableWorker>
        fun scheduler(): WorkSchedulerController
    }

    fun forWorker() = provider.worker()
    fun forScheduler() = provider.scheduler()
}

object UserTaskRouter : NavigationRouter() {
    override val provider by inject<Provider>()

    interface Provider : INavigationProvider {
        fun accountSyncWorker(): Class<out ListenableWorker>
        fun followToggleWorker(): Class<out ListenableWorker>
        fun statisticSyncWorker(): Class<out ListenableWorker>

        fun accountSyncScheduler(): WorkSchedulerController
        fun statisticSyncScheduler(): WorkSchedulerController
    }

    fun forAccountSyncWorker() = provider.accountSyncWorker()
    fun forFollowToggleWorker() = provider.followToggleWorker()
    fun forStatisticSyncWorker() = provider.statisticSyncWorker()

    fun forAccountSyncScheduler() = provider.accountSyncScheduler()
    fun forStatisticSyncScheduler() = provider.accountSyncScheduler()
}

object MediaListTaskRouter : NavigationRouter() {
    override val provider by inject<Provider>()

    interface Provider : INavigationProvider {
        fun mediaListSaveEntryWorker(): Class<out ListenableWorker>
        fun mediaListSaveEntriesWorker(): Class<out ListenableWorker>
        fun mediaListDeleteEntryWorker(): Class<out ListenableWorker>
        fun mediaListDeleteCustomListWorker(): Class<out ListenableWorker>
        fun animeSyncWorker(): Class<out ListenableWorker>
        fun mangaSyncWorker(): Class<out ListenableWorker>

        fun animeSyncScheduler(): WorkSchedulerController
        fun mangaSyncScheduler(): WorkSchedulerController
    }

    fun forMediaListSaveEntryWorker() = provider.mediaListSaveEntryWorker()
    fun forMediaListSaveEntriesWorker() = provider.mediaListSaveEntriesWorker()
    fun forMediaListDeleteEntryWorker() = provider.mediaListDeleteEntryWorker()
    fun forMediaListDeleteCustomListWorker() = provider.mediaListDeleteCustomListWorker()

    fun forMediaListAnimeSyncWorker() = provider.animeSyncWorker()
    fun forMediaListMangaSyncWorker() = provider.mangaSyncWorker()

    fun forAnimeScheduler() = provider.animeSyncScheduler()
    fun forMangaScheduler() = provider.mangaSyncScheduler()

    @Suppress("UNCHECKED_CAST")
    sealed class Param : IParam {

        abstract fun toMap(): Map<String, Any?>

        @Parcelize
        data class SaveEntry(
            val id: Long? = null,
            val mediaId: Long,
            val status: MediaListStatus,
            val score: Float? = null,
            val scoreRaw: Int? = null,
            val progress: Int? = null,
            val progressVolumes: Int? = null,
            val repeat: Int? = null,
            val priority: Int? = null,
            val private: Boolean = false,
            val notes: String? = null,
            val hiddenFromStatusLists: Boolean = false,
            val customLists: List<String>? = null,
            val advancedScores: List<Float>? = null,
            val startedAt: @RawValue FuzzyDate? = null,
            val completedAt: @RawValue FuzzyDate? = null
        ) : Param() {

            @IgnoredOnParcel
            override val idKey = KEY

            override fun toMap() = mapOf(
                "id" to id,
                "mediaId" to mediaId,
                "status" to status,
                "score" to score,
                "scoreRaw" to scoreRaw,
                "progress" to progress,
                "progressVolumes" to progressVolumes,
                "repeat" to repeat,
                "priority" to priority,
                "private" to private,
                "notes" to notes,
                "hiddenFromStatusLists" to hiddenFromStatusLists,
                "advancedScores" to advancedScores,
                "startedAt" to startedAt,
                "completedAt" to completedAt
            )

            companion object : IParam.IKey {
                override val KEY = "MediaListTaskRouter#SaveEntry"

                fun fromMap(map: Map<String, Any?>) = SaveEntry(
                    id = map["id"] as Long?,
                    mediaId = map["mediaId"] as Long,
                    status = map["status"] as MediaListStatus,
                    score = map["score"] as Float?,
                    scoreRaw = map["scoreRaw"] as Int?,
                    progress = map["progress"] as Int?,
                    progressVolumes = map["progressVolumes"] as Int?,
                    repeat = map["repeat"] as Int?,
                    priority = map["priority"] as Int?,
                    private = map["private"] as Boolean ,
                    notes = map["notes"] as String?,
                    hiddenFromStatusLists = map["hiddenFromStatusLists"] as Boolean,
                    advancedScores = map["advancedScores"] as List<Float>?,
                    startedAt = map["startedAt"] as FuzzyDate?,
                    completedAt = map["completedAt"] as FuzzyDate?
                )
            }
        }

        @Parcelize
        data class SaveEntries(
            val status: MediaListStatus,
            val score: Float? = null,
            val scoreRaw: Int? = null,
            val progress: Int? = null,
            val progressVolumes: Int? = null,
            val repeat: Int? = null,
            val priority: Int? = null,
            val private: Boolean = false,
            val notes: String? = null,
            val hiddenFromStatusLists: Boolean = false,
            val advancedScores: List<Float>? = null,
            val startedAt: @RawValue FuzzyDate? = null,
            val completedAt: @RawValue FuzzyDate? = null,
            val ids: List<Long>
        ) : Param() {

            @IgnoredOnParcel
            override val idKey = KEY

            override fun toMap() = mapOf(
                "status" to status,
                "score" to score,
                "scoreRaw" to scoreRaw,
                "progress" to progress,
                "progressVolumes" to progressVolumes,
                "repeat" to repeat,
                "priority" to priority,
                "private" to private,
                "notes" to notes,
                "hiddenFromStatusLists" to hiddenFromStatusLists,
                "advancedScores" to advancedScores,
                "startedAt" to startedAt,
                "completedAt" to completedAt,
                "ids" to ids
            )

            companion object : IParam.IKey {
                override val KEY = "MediaListTaskRouter#SaveEntries"

                fun fromMap(map: Map<String, Any?>) = SaveEntries(
                    status = map["status"] as MediaListStatus,
                    score = map["score"] as Float?,
                    scoreRaw = map["scoreRaw"] as Int?,
                    progress = map["progress"] as Int?,
                    progressVolumes = map["progressVolumes"] as Int?,
                    repeat = map["repeat"] as Int?,
                    priority = map["priority"] as Int?,
                    private = map["private"] as Boolean ,
                    notes = map["notes"] as String?,
                    hiddenFromStatusLists = map["hiddenFromStatusLists"] as Boolean,
                    advancedScores = map["advancedScores"] as List<Float>?,
                    startedAt = map["startedAt"] as FuzzyDate?,
                    completedAt = map["completedAt"] as FuzzyDate?,
                    ids = map["ids"] as List<Long>
                )
            }
        }

        @Parcelize
        data class DeleteEntry(
            val id: Long
        ) : Param() {

            @IgnoredOnParcel
            override val idKey = KEY

            override fun toMap() = mapOf(
                "id" to id
            )

            companion object : IParam.IKey {
                override val KEY = "MediaListTaskRouter#DeleteEntry"

                fun fromMap(map: Map<String, Any?>) = DeleteEntry(
                    id = map["id"] as Long
                )
            }
        }

        @Parcelize
        data class DeleteCustomList(
            val customList: String,
            val type: MediaType
        ) : Param() {

            @IgnoredOnParcel
            override val idKey = KEY

            override fun toMap() = mapOf(
                "customList" to customList,
                "type" to type
            )

            companion object : IParam.IKey {
                override val KEY = "MediaListTaskRouter#DeleteCustomList"

                fun fromMap(map: Map<String, Any?>) = DeleteCustomList(
                    customList = map["customList"] as String,
                    type = map["type"] as MediaType
                )
            }
        }
    }
}

object NewsTaskRouter : NavigationRouter() {
    override val provider by inject<Provider>()

    interface Provider : INavigationProvider {
        fun worker(): Class<out ListenableWorker>
        fun scheduler(): WorkSchedulerController
    }

    fun forWorker() = provider.worker()
    fun forScheduler() = provider.scheduler()
}

object EpisodeTaskRouter : NavigationRouter() {
    override val provider by inject<Provider>()

    interface Provider : INavigationProvider {
        fun worker(): Class<out ListenableWorker>
        fun scheduler(): WorkSchedulerController
    }

    fun forWorker() = provider.worker()
    fun forScheduler() = provider.scheduler()
}