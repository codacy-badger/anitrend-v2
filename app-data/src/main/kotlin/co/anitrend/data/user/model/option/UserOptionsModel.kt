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

package co.anitrend.data.user.model.option

import co.anitrend.domain.notification.enums.NotificationType
import co.anitrend.domain.user.enums.UserTitleLanguage
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** [UserOptions](https://anilist.github.io/ApiV2-GraphQL-Docs/useroptions.doc.html)
 * A user's general options
 *
 * @property displayAdultContent Whether the user has enabled viewing of 18+ content
 * @property profileColor Profile highlight color (blue, purple, pink, orange, red, green, grey)
 * @property titleLanguage The language the user wants to see media titles in
 */
@Serializable
internal sealed class UserOptionsModel {
    abstract val displayAdultContent: Boolean?
    abstract val profileColor: String?
    abstract val titleLanguage: UserTitleLanguage?

    @Serializable
    data class Core(
        @SerialName("displayAdultContent") override val displayAdultContent: Boolean?,
        @SerialName("profileColor") override val profileColor: String?,
        @SerialName("titleLanguage") override val titleLanguage: UserTitleLanguage?,
    ) : UserOptionsModel()

    /**
     * @param airingNotifications Whether the user receives notifications when a show they are watching airs
     * @param notificationOptions Notification options
     * @param timeZone The user's timezone offset (Auth user only)
     */
    @Serializable
    data class Viewer(
        @SerialName("airingNotifications") val airingNotifications: Boolean?,
        @SerialName("notificationOptions") val notificationOptions: List<NotificationOptions>?,
        @SerialName("timezone") val timeZone: String?,
        @SerialName("displayAdultContent") override val displayAdultContent: Boolean?,
        @SerialName("profileColor") override val profileColor: String?,
        @SerialName("titleLanguage") override val titleLanguage: UserTitleLanguage?,
    ) : UserOptionsModel()

    /**
     * @param enabled Whether this type of notification is enabled
     * @param notificationType The type of notification
     */
    @Serializable
    internal data class NotificationOptions(
        @SerialName("enabled") val enabled: Boolean,
        @SerialName("type") val notificationType: NotificationType
    )
}