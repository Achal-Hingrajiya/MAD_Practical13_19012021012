package com.example.practical13_19012021012.src.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
import androidx.annotation.RequiresApi
import com.example.practical13_19012021012.R
import com.example.practical13_19012021012.src.sms_info.OutboxMessages

class OutboxSmsAdapter(private val context : Context, private val dataSource : ArrayList<OutboxMessages>) : BaseAdapter() {
    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return dataSource[position].id
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ViewHolder")
    override fun getView(position: Int, contentView: View?, parent: ViewGroup?): View {

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val rowView = inflater.inflate(R.layout.sms_card, parent, false)
        val tvContactNo = rowView.findViewById<EditText>(R.id.tv_contact_no)
        val tvSmsBody = rowView.findViewById<EditText>(R.id.tv_sms_body)
        tvContactNo.focusable = View.NOT_FOCUSABLE
        tvContactNo.isFocusableInTouchMode = false
        tvContactNo.setBackgroundResource(android.R.color.transparent)
        tvSmsBody.focusable = View.NOT_FOCUSABLE
        tvSmsBody.isFocusableInTouchMode = false
        tvSmsBody.setBackgroundResource(android.R.color.transparent)


        val sms = getItem(position) as OutboxMessages

        tvContactNo.setText(sms.contactNo)
        tvSmsBody.setText(sms.body)

        return rowView
    }
}