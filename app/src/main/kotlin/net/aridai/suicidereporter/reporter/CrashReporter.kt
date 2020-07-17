package net.aridai.suicidereporter.reporter

import android.content.Context

internal object CrashReporter {

    fun setup(context: Context) {
        val newHandler = when (val existingHandler = Thread.getDefaultUncaughtExceptionHandler()) {
            null -> Handler(context)
            else -> Wrapper(existingHandler, Handler(context))
        }

        Thread.setDefaultUncaughtExceptionHandler(newHandler)
    }

    private class Handler(private val context: Context) : Thread.UncaughtExceptionHandler {

        override fun uncaughtException(t: Thread, e: Throwable) {
            CrashReporterService.start(this.context, e)
        }
    }

    private class Wrapper(
        private val existing: Thread.UncaughtExceptionHandler,
        private val handler: Handler
    ) : Thread.UncaughtExceptionHandler {

        override fun uncaughtException(t: Thread, e: Throwable) {
            this.handler.uncaughtException(t, e)
            this.existing.uncaughtException(t, e)
        }
    }
}
