package com.example.practical13_19012021012

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView

class InboxActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inbox)

        val bnav = findViewById<BottomNavigationView>(R.id.bnav)
        bnav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.outbox -> {
                    Intent(this, OutboxActivity::class.java).apply {
                        startActivity(this)
                    }
                    return@setOnItemSelectedListener true
                }
                else ->
                    return@setOnItemSelectedListener true
            }
        }

    }
}