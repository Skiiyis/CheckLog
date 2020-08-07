package com.github.skiiyis.plugin

object Log {

    private const val TAG = "CheckLogPlugin"

    fun info(tag: String, any: Any) {
        println("[$TAG]$tag :$any")
    }

    fun error(tag: String, vararg any: Any) {
        println("[$TAG]$tag :$any")
    }
}