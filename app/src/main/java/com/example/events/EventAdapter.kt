package com.example.events

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_event.view.*

class EventAdapter(private val items: ArrayList<Event>) : RecyclerView.Adapter<EventAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val eventName = view.event_name

        fun bind(event: Event) {
            eventName.text = event.title
        }
    }

    fun update(events: List<Event>) {
        items.clear()
        items.addAll(events)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.fragment_event, parent, false)
    )

}