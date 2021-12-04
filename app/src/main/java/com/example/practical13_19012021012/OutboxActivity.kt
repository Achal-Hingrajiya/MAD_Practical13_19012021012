package com.example.practical13_19012021012

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.example.practical13_19012021012.src.adapters.OutboxSmsAdapter
import com.example.practical13_19012021012.src.sms_info.InboxMessages
import com.example.practical13_19012021012.src.sms_info.OutboxMessages
import com.google.android.material.bottomnavigation.BottomNavigationView

class OutboxActivity : AppCompatActivity() {

    lateinit var lvAdapter : OutboxSmsAdapter

    @RequiresApi(Build.VERSION_CODES.N)
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
        val btnNewSms = findViewById<Button>(R.id.btn_new_sms)
        btnNewSms.setOnClickListener {
            showNewSmsDialog()
        }
    }



    @RequiresApi(Build.VERSION_CODES.N)
    private fun showNewSmsDialog() {
        val dialogTitle = "Send SMS"
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(dialogTitle)

        val customLayout: View = LayoutInflater.from(this).inflate(R.layout.sms_card, null)


        builder.setView(customLayout)
        builder.setPositiveButton(
            "Send",
            DialogInterface.OnClickListener { dialog, which ->


                val tvContactNo = customLayout.findViewById<EditText>(R.id.tv_contact_no)
                val tvBody = customLayout.findViewById<EditText>(R.id.tv_sms_body)
                val contactNo = tvContactNo.text.toString()
                val body = tvBody.text.toString()


                val sms = SmsManager.getDefault()

                sms.sendTextMessage(contactNo, "Achal Hingrajiya", body, null, null)

                Toast.makeText(this, "SMS Sent",Toast.LENGTH_SHORT).show()

                val newSms = OutboxMessages(contactNo, body)
                OutboxMessages.addSMS(newSms)

                lvAdapter.notifyDataSetChanged()

            })

        val dialog: AlertDialog = builder.create()

        dialog.show()

    }
}