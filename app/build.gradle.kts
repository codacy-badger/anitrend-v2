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

    /** Material Design */
    implementation(Libraries.Google.Material.material)

    implementation(Libraries.AniTrend.Emojify.emojify)

    /** debugImplementation because LeakCanary should only run in debug builds. */
    debugImplementation(Libraries.Square.LeakCanary.leakCanary)

    /** debugImplementation because debug-db should only run in debug builds */
    debugImplementation(Libraries.debugDb)
}

