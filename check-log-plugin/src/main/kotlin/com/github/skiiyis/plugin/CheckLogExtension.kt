package com.github.skiiyis.plugin

import org.gradle.api.Action

open class CheckLogExtension {
    internal var debug = VariantExtension()
    internal var release = VariantExtension()

    fun debug(action: Action<VariantExtension>) {
        action.execute(debug)
    }

    fun release(action: Action<VariantExtension>) {
        action.execute(release)
    }

    override fun toString(): String {
        return "CheckLogExtension(debug=$debug, release=$release)"
    }
}

open class VariantExtension {
    internal var replace: String? = null
    internal var methods: List<String>? = null
    internal var include: List<String>? = null
    internal var exclude: List<String>? = null

    fun replace(fullClassName: String) {
        replace = fullClassName.replace(".", "/")
    }

    fun methods(vararg methodName: String) {
        methods = methodName.toList()
    }

    fun include(vararg packageName: String) {
        include = packageName.toList()
    }

    fun exclude(vararg packageName: String) {
        exclude = packageName.toList()
    }

    override fun toString(): String {
        return "VariantExtension(replace=$replace, methods=$methods, include=$include, exclude=$exclude)"
    }
}