package net.aridai.suicidereporter

import android.app.Application
import net.aridai.suicidereporter.reporter.CrashReporter
import timber.log.Timber

internal class App : Application() {

    override fun onCreate() {
        super.onCreate()

        CrashReporter.setup(this)

        Timber.plant(Timber.DebugTree())
        Timber.tag("SuicideReporter")
    }
}
