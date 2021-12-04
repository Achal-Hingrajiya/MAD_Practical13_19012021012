package com.example.practical13_19012021012.src.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.practical13_19012021012.R
import com.example.practical13_19012021012.src.sms_info.InboxMessages

class InboxSmsAdapter(private val context : Context, private val dataSource : ArrayList<InboxMessages>) : BaseAdapter() {
    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return dataSource[position].id
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, contentView: View?, parent: ViewGroup?): View {

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val rowView = inflater.inflate(R.layout.sms_card, parent, false)
        val tvContactNo = rowView.findViewById<TextView>(R.id.tv_contact_no)
        val tvSmsBody = rowView.findViewById<TextView>(R.id.tv_sms_body)

        val sms = getItem(position) as InboxMessages

        tvContactNo.text = sms.contactNo
        tvSmsBody.text = sms.body

        return rowView
    }
}