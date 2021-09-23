package net.imknown.android.testsharedflow

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val btn = Button(this).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setText(android.R.string.ok)
            textSize = 100F
            setOnClickListener {
                val intent = Intent(this@MainActivity, SecondaryActivity::class.java)
                startActivity(intent)
            }
        }
        setContentView(btn)
    }
}