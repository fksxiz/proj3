package com.example.proj3

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.bumptech.glide.Glide

class EventAdapter(private val context: Context):BaseAdapter() {
    var events: List<Event> = emptyList()

    override fun getCount(): Int {
        return events.size
    }

    override fun getItem(position: Int): Any {
        return events[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val event = getItem(position) as Event
        val view=convertView ?:LayoutInflater.from(context).inflate(R.layout.events_listview_item,parent,false)

        view.apply {
            findViewById<TextView>(R.id.titleTextView).text = event.title
            findViewById<TextView>(R.id.AnnotationTextView).text = event.annotation
            findViewById<TextView>(R.id.DateTextView).text = event.createDate
            if(!event.imageUrl.isNullOrEmpty()) Glide.with(context).load(event.imageUrl).into(findViewById(R.id.ImageView))
        }
        return view
    }

}