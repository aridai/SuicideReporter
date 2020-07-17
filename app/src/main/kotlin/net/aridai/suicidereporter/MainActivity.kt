package net.aridai.suicidereporter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

internal class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.setContentView(R.layout.main_activity)
        this.button1.setOnClickListener { this.suicide1() }
        this.button2.setOnClickListener { this.suicide2() }
        this.button3.setOnClickListener { this.suicide3() }
    }

    private fun suicide1() {
        Timber.d("自殺1")

        throw Exception("自殺1")
    }

    private fun suicide2() {
        Timber.d("自殺2")
        this.suicide2a()
    }

    private fun suicide3() {
        Timber.d("自殺3")
        this.lifecycleScope.launch {
            withContext<Unit>(Dispatchers.IO) {
                class Suicide3Exception : Exception("自殺3")

                throw Suicide3Exception()
            }
        }
    }

    private fun suicide2a() {
        this.suicide2b()
    }

    private fun suicide2b() {
        this.suicide2c()
    }

    private fun suicide2c() {
        class Suicide2Exception : Exception("自殺2")

        throw Suicide2Exception()
    }
}
