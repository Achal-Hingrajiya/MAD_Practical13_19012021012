package com.example.practical13_19012021012

import android.Manifest
import android.app.AlertDialog
import android.content.*
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Telephony
import android.view.LayoutInflater
import android.view.View
import android.widget.ListView
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.example.practical13_19012021012.src.adapters.InboxSmsAdapter
import com.example.practical13_19012021012.src.sms_info.InboxMessages
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText

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


//    @RequiresApi(Build.VERSION_CODES.N)
//    private fun showEditNoteDialog(sms: InboxMessages) {
//        val dialogTitle = "SMS Received"
//        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
//        builder.setTitle(dialogTitle)
//
//        val customLayout: View = LayoutInflater.from(this).inflate(R.layout.new_sms_dialog, null)
//
//
////
//        title.setText(note.title)
//        subtitle.setText(note.subTitle)
//        desc.setText(note.Description)
//        reminderSwitch.isChecked = note.isReminder
//
//        timePicker.hour = getHour(note.remindertime)
//        timePicker.minute = getMinute(note.remindertime)
//
//        builder.setView(customLayout)
//        builder.setPositiveButton(
//            "Save Changes",
//            DialogInterface.OnClickListener { dialog, which ->
//
//                val hour = timePicker.hour
//                val minute = timePicker.minute
//                val remTimeInMillis = getMillis(hour, minute)
//                val modifiedTime = getCurrentDateTime(remTimeInMillis)
//
//                note.title = title.text.toString()
//                note.subTitle = subtitle.text.toString()
//                note.Description = desc.text.toString()
//                note.isReminder = reminderSwitch.isChecked
//                note.modifiedTime = modifiedTime
//
//
//                notifyDataSetChanged()
//
//            })
//
//        val dialog: AlertDialog = builder.create()
//        dialog.show()
//
//    }

}