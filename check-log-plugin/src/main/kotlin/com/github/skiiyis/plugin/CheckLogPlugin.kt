package com.github.skiiyis.plugin

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.util.*

class CheckLogPlugin : Plugin<Project> {

    companion object {
        const val EXTENSION_NAME = "checkLog"
    }

    override fun apply(project: Project) {
        val checkLogExtension =
            project.extensions.create(EXTENSION_NAME, CheckLogExtension::class.java)
        val appExtension = project.properties["android"] as AppExtension
        appExtension.registerTransform(
            CheckLogTransform(checkLogExtension, project),
            Collections.EMPTY_LIST
        )
        project.afterEvaluate {
            Log.info("extension", checkLogExtension)
        }
    }
}