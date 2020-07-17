package net.aridai.suicidereporter.reporter

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.aridai.suicidereporter.R
import timber.log.Timber
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

internal class CrashReporterService : LifecycleService() {
    companion object {
        private val EXTRA_STACK_TRACE = "${CrashReporterService::class.java.name}.extra.STACK_TRACE"
        private val NOTIFICATION_CHANNEL_ID = "${CrashReporterService::class.java.name}.NOTIFICATION_CHANNEL_ID"
        private const val NOTIFICATION_CHANNEL_NAME = "CrashReporterChannel"
        private const val NOTIFICATION_NAME = "CrashReporter"
        private const val FOREGROUND_SERVICE_ID = 89
        private const val DATE_TIME_PATTERN = "yyyy-MM-dd-HH-mm-ss"
        private const val TOAST_MESSAGE = "クラッシュレポート保存完了"
        private const val NOTIFICATION_MESSAGE = "クラッシュレポート保存中..."

        fun start(context: Context, error: Throwable) {
            val intent = Intent(context, CrashReporterService::class.java).also {
                it.putExtra(EXTRA_STACK_TRACE, Log.getStackTraceString(error))
            }
            ContextCompat.startForegroundService(context, intent)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        this.startForeground(FOREGROUND_SERVICE_ID, this.createNotification())

        this.lifecycleScope.launch { execute(intent!!) }

        return START_NOT_STICKY
    }

    private suspend fun execute(intent: Intent) {
        this.saveToFile(stackTrace = intent.getStringExtra(EXTRA_STACK_TRACE)!!)

        //  分かりやすいようにスリープを入れる。
        delay(1000L)
        Toast.makeText(this.applicationContext, TOAST_MESSAGE, Toast.LENGTH_SHORT).show()
        this.stopSelf()
    }

    private suspend fun saveToFile(stackTrace: String) {
        val fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)).let { "$it.log" }
        val file = File(this.applicationContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), fileName)

        Timber.i("出力ファイル: ${file.absolutePath}")

        withContext(Dispatchers.IO) { file.writeText(stackTrace) }
    }

    private fun createNotification(): Notification {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_LOW
            )
            NotificationManagerCompat.from(this.applicationContext).createNotificationChannel(channel)
        }

        return NotificationCompat.Builder(
            this.applicationContext,
            NOTIFICATION_CHANNEL_ID
        )
            .setContentTitle(NOTIFICATION_NAME)
            .setContentText(NOTIFICATION_MESSAGE)
            .setSmallIcon(R.drawable.ic_baseline_report_24)
            .setProgress(0, 0, true)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setNotificationSilent()
            .setAutoCancel(false)
            .build()
    }
}
