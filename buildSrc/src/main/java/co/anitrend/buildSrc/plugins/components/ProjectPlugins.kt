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

package co.anitrend.buildSrc.plugins.components

import org.gradle.api.Project
import org.gradle.api.plugins.PluginContainer
import co.anitrend.buildSrc.extensions.isAppModule
import co.anitrend.buildSrc.extensions.hasKaptSupport
import co.anitrend.buildSrc.extensions.baseExtension
import co.anitrend.buildSrc.extensions.hasKotlinAndroidExtensionSupport

private fun addAndroidPlugin(project: Project, pluginContainer: PluginContainer) {
    if (project.isAppModule()) pluginContainer.apply("com.android.application")
    else pluginContainer.apply("com.android.library")
}

private fun addKotlinAndroidPlugin(pluginContainer: PluginContainer) {
    pluginContainer.apply("kotlin-android")
    pluginContainer.apply("com.diffplug.spotless")
}

private fun addAnnotationProcessor(project: Project, pluginContainer: PluginContainer) {
    if (project.hasKaptSupport())
        pluginContainer.apply("kotlin-kapt")
}

private fun addKotlinAndroidExtensions(project: Project, pluginContainer: PluginContainer) {
    if (project.hasKotlinAndroidExtensionSupport())
        pluginContainer.apply("kotlin-parcelize")
}

internal fun Project.configurePlugins() {
    addAndroidPlugin(project, plugins)
    addKotlinAndroidPlugin(plugins)
    addKotlinAndroidExtensions(project, plugins)
    addAnnotationProcessor(project, plugins)
}

internal fun Project.configureAdditionalPlugins() {
    /*if (isAppModule()) {
        println("Applying additional google plugins")
        if (file("google-services.json").exists()) {
            plugins.apply("com.google.gms.google-services")
            plugins.apply("com.google.firebase.crashlytics")
        } else println("google-services.json cannot be found and will not be using any of the google plugins")
    }*/
    if (isAppModule()) {
        baseExtension().variantFilter {
            println("VariantFilter { defaultConfig: ${defaultConfig.name}, buildType: ${buildType.name}, flavors: [${flavors.joinToString { it.name }}], name: $name }")
            if (flavors.isNotEmpty() && flavors.first().name == "google") {
                println("Applying additional google plugins on -> variant: $name | type: ${buildType.name}")
                if (file("google-services.json").exists()) {
                    plugins.apply("com.google.gms.google-services")
                    plugins.apply("com.google.firebase.crashlytics")
                } else println("google-services.json cannot be found and will not be using any of the google plugins")
            }
        }
    }
}