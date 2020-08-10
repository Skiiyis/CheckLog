package com.github.skiiyis.plugin

import com.android.build.api.transform.Context
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.TransformInput
import com.android.build.api.transform.TransformOutputProvider
import com.android.build.gradle.internal.pipeline.TransformManager
import com.quinn.hunter.transform.HunterTransform
import com.quinn.hunter.transform.RunVariant
import org.gradle.api.Project

class CheckLogTransform(
    private val extension: CheckLogExtension,
    project: Project
) : HunterTransform(project) {

    override fun transform(
        context: Context?,
        inputs: MutableCollection<TransformInput>?,
        referencedInputs: MutableCollection<TransformInput>?,
        outputProvider: TransformOutputProvider?,
        isIncremental: Boolean
    ) {
        val variant = context?.variantName
        when {
            variant?.toLowerCase()?.contains("release") == true -> {
                this.bytecodeWeaver = CheckLogByteCodeWeaver(extension.release)
            }
            variant?.toLowerCase()?.contains("debug") == true -> {
                this.bytecodeWeaver = CheckLogByteCodeWeaver(extension.debug)
            }
            else -> {
                throw IllegalArgumentException("unsupport variant, current variant is $variant")
            }
        }
        super.transform(context, inputs, referencedInputs, outputProvider, isIncremental)
    }

    override fun getRunVariant(): RunVariant? {
        val debug = extension.debug.replace != null
        val release = extension.release.replace != null
        return when {
            debug && release -> {
                RunVariant.ALWAYS
            }
            release -> {
                RunVariant.RELEASE
            }
            debug -> {
                RunVariant.DEBUG
            }
            else -> {
                RunVariant.NEVER
            }
        }
    }

    override fun inDuplcatedClassSafeMode(): Boolean {
        return true
    }

    override fun getName(): String {
        return "checkLog"
    }

    override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> {
        return TransformManager.CONTENT_CLASS
    }

    override fun isIncremental(): Boolean {
        return true
    }

    override fun getScopes(): MutableSet<QualifiedContent.Scope>? {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    override fun getReferencedScopes(): MutableSet<in QualifiedContent.Scope> {
        return TransformManager.EMPTY_SCOPES
    }
}