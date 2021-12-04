package com.example.practical13_19012021012

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Telephony
import android.widget.ListView
import androidx.core.app.ActivityCompat
import com.example.practical13_19012021012.src.adapters.InboxSmsAdapter
import com.example.practical13_19012021012.src.sms_info.InboxMessages
import com.google.android.material.bottomnavigation.BottomNavigationView

class InboxActivity : AppCompatActivity() {

    lateinit var lvAdapter : InboxSmsAdapter

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

        val lvInboxSms = findViewById<ListView>(R.id.lv_inbox_sms)

        lvAdapter = InboxSmsAdapter(this, InboxMessages.inboxSmsArr)
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
        } else {
            receiveMsg()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 111
            && grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            receiveMsg()
        }
    }

    private fun receiveMsg() {
        val br = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    for (sms in Telephony.Sms.Intents.getMessagesFromIntent(intent)) {

                        val contactNo = sms.originatingAddress.toString()
                        val body = sms.displayMessageBody.toString()
                        val newSms = InboxMessages(contactNo, body)
                        InboxMessages.addSMS(newSms)
                        lvAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
        registerReceiver(br, IntentFilter("android.provider.Telephony.SMS_RECEIVED"))
    }
}