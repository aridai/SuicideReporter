package net.aridai.suicidereporter

import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.detail_activity.*
import java.io.File

internal class DetailActivity : AppCompatActivity() {
    companion object {
        val EXTRA_FILE_NAME = "${DetailActivity::class.java.name}.extra.FILE_NAME"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.detail_activity)

        val fileName = this.intent.getStringExtra(EXTRA_FILE_NAME)!!
        val dir = this.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)!!
        val content = File(dir, fileName).readText()

        this.content.text = content
    }
}
