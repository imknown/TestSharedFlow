package net.imknown.android.testsharedflow

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        findViewById<View>(R.id.btnGo).setOnClickListener {
            startActivity(Intent(this, SecondaryActivity::class.java))
        }
    }
}