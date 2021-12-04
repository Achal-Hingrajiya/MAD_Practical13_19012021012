package com.example.practical13_19012021012

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import androidx.core.app.ActivityCompat
import com.example.practical13_19012021012.src.adapters.InboxSmsAdapter
import com.example.practical13_19012021012.src.adapters.OutboxSmsAdapter
import com.example.practical13_19012021012.src.sms_info.InboxMessages
import com.example.practical13_19012021012.src.sms_info.OutboxMessages
import com.google.android.material.bottomnavigation.BottomNavigationView

class OutboxActivity : AppCompatActivity() {

    lateinit var lvAdapter : OutboxSmsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_outbox)


        val bnav = findViewById<BottomNavigationView>(R.id.bnav)
        bnav.selectedItemId = R.id.outbox

        bnav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.inbox -> {
                    Intent(this, InboxActivity::class.java).apply {
                        startActivity(this)
                    }
                    return@setOnItemSelectedListener true
                }
                else ->
                    return@setOnItemSelectedListener true
            }
        }


        val lvInboxSms = findViewById<ListView>(R.id.lv_outbox_sms)

        OutboxMessages.addSMS(OutboxMessages("6355689874", "This is a testing sms."))
        OutboxMessages.addSMS(OutboxMessages("6355689874", "This is a testing sms."))
        OutboxMessages.addSMS(OutboxMessages("6355689874", "This is a testing sms."))
        OutboxMessages.addSMS(OutboxMessages("6355689874", "This is a testing sms."))
        OutboxMessages.addSMS(OutboxMessages("6355689874", "This is a testing sms."))
        OutboxMessages.addSMS(OutboxMessages("6355689874", "This is a testing sms."))

        lvAdapter = OutboxSmsAdapter(this, OutboxMessages.outboxSmsArr)
        lvInboxSms.adapter = lvAdapter

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.RECEIVE_SMS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.RECEIVE_SMS,
                    Manifest.permission.SEND_SMS,
                    Manifest.permission.READ_SMS
                ),
                111
            )
        }

    }
}