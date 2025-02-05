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

package co.anitrend.medialist.editor.provider

import android.content.Context
import android.content.Intent
import co.anitrend.medialist.editor.component.sheet.MediaListEditorContent
import co.anitrend.medialist.editor.component.screen.MediaListEditorScreen
import co.anitrend.navigation.MediaListEditorRouter

class FeatureProvider : MediaListEditorRouter.Provider {
    override fun activity(context: Context?) =
        Intent(context, MediaListEditorScreen::class.java)
        
    override fun sheet() = MediaListEditorContent::class.java
}