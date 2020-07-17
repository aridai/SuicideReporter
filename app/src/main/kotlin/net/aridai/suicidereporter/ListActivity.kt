package net.aridai.suicidereporter

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.list_activity.*

internal class ListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.list_activity)

        val dir = this.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)!!
        val files = dir.listFiles()?.mapNotNull { if (it.extension == "log") it.name else null } ?: emptyList()

        this.listView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, files)
        this.listView.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, DetailActivity::class.java).also {
                it.putExtra(DetailActivity.EXTRA_FILE_NAME, files[position])
            }
            this.startActivity(intent)
        }
    }
}
