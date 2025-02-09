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

import co.anitrend.buildSrc.Libraries

plugins {
    id("co.anitrend.plugin")
}

dependencies {
    implementation(project(Libraries.AniTrend.Data.feed))
    implementation(project(Libraries.AniTrend.CommonUi.episode))

    implementation(Libraries.AndroidX.Browser.browser)

    implementation(Libraries.Saket.BetterLinkMovement.betterLinkMovement)
    implementation(Libraries.jsoup)

    /** Markwon */
    implementation(Libraries.Markwon.core)
    implementation(Libraries.Markwon.html)
    implementation(Libraries.Markwon.linkify)
    implementation(Libraries.Markwon.coil)
}
