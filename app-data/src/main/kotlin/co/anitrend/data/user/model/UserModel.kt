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

package co.anitrend.data.user.model

import co.anitrend.data.core.common.Identity
import co.anitrend.data.shared.model.SharedImageModel
import co.anitrend.data.user.model.contract.IUserModel
import co.anitrend.data.user.model.option.UserOptionsModel
import co.anitrend.data.user.model.statistics.UserStatisticModel
import co.anitrend.domain.medialist.enums.ScoreFormat
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal sealed class UserModel : IUserModel {

    /** [MediaListOptions](https://anilist.github.io/ApiV2-GraphQL-Docs/medialistoptions.doc.html)
     * A user's list options
     *
     * @param scoreFormat The score format the user is using for media lists
     * @param rowOrder The default order list rows should be displayed in
     * @param animeList The user's anime list options
     * @param mangaList The user's manga list options
     */
    @Serializable
    internal data class MediaListOptions(
        @SerialName("scoreFormat") val scoreFormat: ScoreFormat,
        @SerialName("rowOrder") val rowOrder: String,
        @SerialName("animeList") val animeList: MediaListTypeOptions?,
        @SerialName("mangaList") val mangaList: MediaListTypeOptions?
    )

    /** [MediaListTypeOptions](https://anilist.github.io/ApiV2-GraphQL-Docs/medialisttypeoptions.doc.html)
     * A user's list options for anime or manga lists
     *
     * @param sectionOrder The order each list should be displayed in
     * @param splitCompletedSectionByFormat If the completed sections of the list should be separated by format
     * @param customLists The names of the user's custom lists
     * @param advancedScoring The names of the user's advanced scoring sections
     * @param advancedScoringEnabled If advanced scoring is enabled
     */
    @Serializable
    internal data class MediaListTypeOptions(
        @SerialName("sectionOrder") val sectionOrder: List<String>?,
        @SerialName("splitCompletedSectionByFormat") val splitCompletedSectionByFormat: Boolean,
        @SerialName("customLists") val customLists: List<String>?,
        @SerialName("advancedScoring") val advancedScoring: List<String>?,
        @SerialName("advancedScoringEnabled") val advancedScoringEnabled: Boolean
    )

    /**
     * A user's previous name
     *
     * @param createdAt When the user first changed from this name
     * @param name A previous name of the user
     * @param updatedAt When the user most recently changed from this name
     */
    @Serializable
    internal data class UserPreviousName(
        @SerialName("createdAt") val createdAt: Long,
        @SerialName("name") val name: String,
        @SerialName("updatedAt") val updatedAt: Long,
    )

    @Serializable
    internal data class Id(
        @SerialName("id") override val id: Long
    ) : Identity

    /**
     * @param isFollowing If the authenticated user if following this user
     * @param isFollower If this user if following the authenticated user
     */
    @Serializable
    internal data class Core(
        @SerialName("isFollowing") val isFollowing: Boolean?,
        @SerialName("isFollower") val isFollower: Boolean?,
        @SerialName("name") override val name: String,
        @SerialName("avatar") override val avatar: SharedImageModel?,
        @SerialName("bannerImage") override val bannerImage: String?,
        @SerialName("isBlocked") override val isBlocked: Boolean?,
        @SerialName("about") override val about: String?,
        @SerialName("donatorTier") override val donatorTier: Int?,
        @SerialName("donatorBadge") override val donatorBadge: String?,
        @SerialName("siteUrl") override val siteUrl: String,
        @SerialName("updatedAt") override val updatedAt: Long?,
        @SerialName("createdAt") override val createdAt: Long?,
        @SerialName("id") override val id: Long
    ) : UserModel()

    /**
     * @property mediaListOptions The user's media list options
     * @property options The user's general options
     */
    @Serializable
    internal abstract class WithOptions : UserModel() {
        abstract val previousNames: List<UserPreviousName>
        abstract val options: UserOptionsModel?
        abstract val mediaListOptions: MediaListOptions?
    }

    /**
     * @param isFollowing If the authenticated user if following this user
     * @param isFollower If this user if following the authenticated user
     */
    @Serializable
    internal data class Extended(
        @SerialName("isFollowing") val isFollowing: Boolean?,
        @SerialName("isFollower") val isFollower: Boolean?,
        @SerialName("previousNames") override val previousNames: List<UserPreviousName> = emptyList(),
        @SerialName("options") override val options: UserOptionsModel.Core?,
        @SerialName("mediaListOptions") override val mediaListOptions: MediaListOptions?,
        @SerialName("name") override val name: String,
        @SerialName("avatar") override val avatar: SharedImageModel?,
        @SerialName("bannerImage") override val bannerImage: String?,
        @SerialName("isBlocked") override val isBlocked: Boolean?,
        @SerialName("about") override val about: String?,
        @SerialName("donatorTier") override val donatorTier: Int?,
        @SerialName("donatorBadge") override val donatorBadge: String?,
        @SerialName("siteUrl") override val siteUrl: String,
        @SerialName("updatedAt") override val updatedAt: Long?,
        @SerialName("createdAt") override val createdAt: Long?,
        @SerialName("id") override val id: Long
    ) : WithOptions()

    /**
     * @param unreadNotificationCount The number of unread notifications the user has
     * @param mediaListOptions The user's media list options
     * @param options The user's general options
     */
    @Serializable
    internal data class Viewer(
        @SerialName("isFollower") val isFollower: Boolean?,
        @SerialName("isFollowing") val isFollowing: Boolean?,
        @SerialName("previousNames") override val previousNames: List<UserPreviousName> = emptyList(),
        @SerialName("unreadNotificationCount") val unreadNotificationCount: Int?,
        @SerialName("options") override val options: UserOptionsModel.Viewer?,
        @SerialName("mediaListOptions") override val mediaListOptions: MediaListOptions?,
        @SerialName("name") override val name: String,
        @SerialName("avatar") override val avatar: SharedImageModel?,
        @SerialName("bannerImage") override val bannerImage: String?,
        @SerialName("isBlocked") override val isBlocked: Boolean?,
        @SerialName("about") override val about: String?,
        @SerialName("donatorTier") override val donatorTier: Int?,
        @SerialName("donatorBadge") override val donatorBadge: String?,
        @SerialName("siteUrl") override val siteUrl: String,
        @SerialName("updatedAt") override val updatedAt: Long?,
        @SerialName("createdAt") override val createdAt: Long?,
        @SerialName("id") override val id: Long
    ) : WithOptions()

    @Serializable
    internal data class WithStatistic(
        @SerialName("isFollowing") val isFollowing: Boolean?,
        @SerialName("isFollower") val isFollower: Boolean?,
        @SerialName("statistics") val statistics: Statistic?,
        @SerialName("name") override val name: String,
        @SerialName("avatar") override val avatar: SharedImageModel?,
        @SerialName("bannerImage") override val bannerImage: String?,
        @SerialName("isBlocked") override val isBlocked: Boolean?,
        @SerialName("about") override val about: String?,
        @SerialName("donatorTier") override val donatorTier: Int?,
        @SerialName("donatorBadge") override val donatorBadge: String?,
        @SerialName("siteUrl") override val siteUrl: String,
        @SerialName("updatedAt") override val updatedAt: Long?,
        @SerialName("createdAt") override val createdAt: Long?,
        @SerialName("id") override val id: Long
    ) : UserModel() {

        /** [UserStatisticTypes](https://anilist.github.io/ApiV2-GraphQL-Docs/UserStatisticTypes.doc.html)
         * A user's statistics
         *
         * @param anime TBA
         * @param manga TBA
         */
        @Serializable
        data class Statistic(
            @SerialName("anime") val anime: UserStatisticModel.Anime,
            @SerialName("manga") val manga: UserStatisticModel.Manga
        )
    }
}