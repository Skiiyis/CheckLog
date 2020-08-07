package skiiyis.github.com.checklog

import android.util.Log

object MyLog {

    @JvmStatic
    fun e(tag: String?, msg: String?): Int {
        return Log.e(tag, "[${getCaller()}] $msg")
    }

    @JvmStatic
    fun e(tag: String?, msg: String?, tr: Throwable?): Int {
        return Log.e(tag, "[${getCaller()}] $msg", tr)
    }

    @JvmStatic
    fun i(tag: String?, msg: String?): Int {
        return Log.i(tag, "[${getCaller()}] $msg")
    }

    @JvmStatic
    fun i(tag: String?, msg: String?, tr: Throwable?): Int {
        return Log.i(tag, "[${getCaller()}] $msg", tr)
    }

    private fun getCaller(): String {
        val stackTrace = Thread.currentThread().stackTrace[4]
        return "${stackTrace.fileName}:${stackTrace.lineNumber}"
    }
}